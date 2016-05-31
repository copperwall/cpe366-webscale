/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author scott
 */
public class RoomBooking extends DBO {
    
    public RoomBooking(int id) {
        super();
        
        // Our users table has the PK of userid
        this.setTable("room_bookings", "room_bookingid");
        
        this.bind("roomid", "roomid:integer");
        this.bind("bookingid", "bookingid:integer");
        this.bind("start_date", "start_date:timestamp without time zone");
        this.bind("end_date", "end_date:timestamp without time zone");
        this.bind("price", "price:real");

        if (id != 0) {
            this.id = id;
            this.fromDb = true;
            this.load();
        }
    }
    
    public static RoomBooking factory(int bookingid, int roomid, String startDate, String endDate) {
        RoomBooking rb = new RoomBooking(0);
        
        rb.set("bookingid", bookingid);
        rb.set("roomid", roomid);
        rb.set("start_date", startDate);
        rb.set("end_date", endDate);
        //this.set("price", price);
        
        return rb;
    }

    public double getPrice() {
        return Double.parseDouble(this.get("price"));
    }
    
    public String getDescription() {
        Room r = new Room(Integer.parseInt(this.get("roomid")));
        return r.getDescription();
    }
    
    public String getCheckinDate() {
        return this.get("start_date");
    }
    
    public String getCheckoutDate() {
        return this.get("end_date");
    }
    
    public String getRoomNumber() {
        Room r = new Room(Integer.parseInt(this.get("roomid")));
        return r.get("number");
    }

    public boolean save() throws Exception{
        // If this is not an existing booking, make sure the room is
        // available before allowing the booking to be created
        // If the room is not available during those dates, throw a fit.
        if (!this.fromDb) {
            Room r = new Room(Integer.parseInt(this.get("roomid")));
            if (!r.isAvailable(this.get("start_date"), this.get("end_date"))) {
                throw new
                 Exception("The room is not available during those dates");
            }
            
            // We also need to calculate the total price for this booking.
            double total = 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            // Iterate over every day in the booking, get the price for that day
            LocalDate start = LocalDate.parse(this.get("start_date"), formatter),
                      end   = LocalDate.parse(this.get("end_date"), formatter);
            
            LocalDate next = start.minusDays(1);

            while((next = next.plusDays(1)).isBefore(end.plusDays(1))) {
                double price = r.getPrice(next.toString());
                total += price;
            }
            
            this.set("price", total);
        }

        return super.save();
    }
}
