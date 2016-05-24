/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

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
    
    public RoomBooking(int bookingid, String startDate, String endDate, float price) {
        super(0);
        
        this.set("start_date", startDate);
        this.set("end_date", endDate);
        this.set("price", "" + price);
    }
}
