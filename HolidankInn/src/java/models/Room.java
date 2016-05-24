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
    
    // Is the room available given a date?
    public boolean isAvailable(String date) {
        // To determine whether the room is available:
        // Query for all RoomBookings referring to this room during the date
        // passed in. If count(bookings) > 0, this room is not available.
        // NOTE: Make sure to join the bookings table and make sure the booking
        // is still active and not cancelled.
        return true;
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
        return true;
    }
    
    // Get the type of beds the room has
    public String getBedType() {
        // if room number %2 == 0, double queen
        // if room number %2 != 0, single king
        return "double queen"; // OR SINGLE KING
    }
    
    // Get the type of view the room has
    public String getView() {
        // if room rumber [x01-x06], ocean view
        // if room number [x07-x12], pool view
        return "ocean"; // OR POOL
    }
    
    public static ArrayList<Room> getAvailableRooms(String start, String end) {
        Room r = new Room(0);
        
        // TODO write a query to return all the available rooms for the date
        // range
        String q = "";
        
        return r.getCustom(q);
    }
}
