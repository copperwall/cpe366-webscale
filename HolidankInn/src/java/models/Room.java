/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;

/**
 *
 * @author scott
 */
public class Room extends DBO {
    
    public Room(int id) {
        super();
        
        // Our users table has the PK of userid
        this.setTable("rooms", "roomid");
        
        // Bind our room attributes
        this.bind("number", "number:integer");
        this.bind("view_type", "view_type:room_view");
        this.bind("bed_type", "bed_type:room_bed");
        
        if (id != 0) {
            this.id = id;
            this.fromDb = true;
            this.load();
        }
    }
    
    // Get the room's price given a date.
    public double getPrice(String date) {
        // To determine the price of a room:
        // 1. Does it have any RoomPrice overrides during the date passed in?
        // 2. If so, return the price. If not, return 100.
        return 100.0;
    }
    
    public String getView() {
        switch(this.get("view_type")) {
            case "ocean":
                return "Ocean view";
            case "pool":
                return "Pool view";
        }
        return "";
    }
    
    public String getBed() {
        switch(this.get("bed_type")) {
            case "single":
                return "Single king";
            case "double":
                return "Double queen";
        }
        return "";
    }
    
    public String getDescription() {
        return this.getView() + ", " + this.getBed();
    }
    
    // Is the room available given a date?
    public boolean isAvailable(String date) {
        // Checking for a single date is the same as for a date range
        // since it's just an OR statement in SQL.
        return this.isAvailable(date, date);
    }
    
    public boolean isAvailable(String startDate, String endDate) {
        // If we want to check for an entire range at a time, it's
        // definitely faster. Do that here. The query is a little trickier.
        // If the passed in start or end dates falls between the start/end date
        // of an existing  booking, the room is not avaialble.
        // SELECT * FROM room_bookings rb
        // WHERE rb.startDate < ?StartDate < rb.endDate
        // OR rb.startDate < ?EndDate < rb.endDate
        // ALSO join the bookings table and make sure the booking is still
        // active
        RoomBooking rb = new RoomBooking(0);
        String query = "SELECT * FROM room_bookings rb " +
            "JOIN rooms r USING (roomid) " +
            "JOIN bookings b ON (rb.room_bookingid = b.bookingid) " +
            "WHERE  (( " +
            "	rb.start_date <= '" + startDate + "' " +
            "	AND rb.end_date >= '" + startDate + "' " +
            ") OR ( " +
            "	rb.start_date <= '" + endDate + "' " +
            "	AND rb.end_date >= '" + endDate + "' " +
            ")) " +
            "AND r.roomid = " + this.getPk() + " " +
            "AND b.confirmed = 1 " +
            "AND b.cancelled = 0;";
        
        ArrayList<RoomBooking> confirmedBookings = rb.getCustom(query);
        
        // If there are _ANY_ confirmed bookings for this room during those
        // dates, the room is NOT available.
        return confirmedBookings.isEmpty();
    }
    
    public static ArrayList<Room> getAvailableRooms(String start, String end,
     String view, String bed) {
        Room r = new Room(0);
        
        String query = "SELECT * FROM rooms r " +
            "LEFT JOIN room_bookings rb USING (roomid) " +
            "LEFT JOIN bookings b ON (rb.room_bookingid = b.bookingid) " +
            "WHERE  ((( " +
            "	rb.start_date <= '" + start + "' " +
            "	AND rb.end_date >= '" + start + "' " +
            ") OR ( " +
            "	rb.start_date <= '" + end + "' " +
            "	AND rb.end_date >= '" + end + "' " +
            ")) AND (b.confirmed = 0 OR b.cancelled = 1) " +
            "OR (rb.room_bookingid IS NULL )) " +
            "AND r.view_type = '" + view + "' " +
            "AND r.bed_type = '" + bed + "';";
        System.out.println("QUERY " + query);
        return r.getCustom(query);
    }
}
