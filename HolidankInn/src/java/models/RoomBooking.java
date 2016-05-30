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
    
    public RoomBooking(int bookingid, int roomid, String startDate, String endDate, float price) {
        super(0);
        
        this.set("bookingid", bookingid);
        this.set("roomid", roomid);
        this.set("start_date", startDate);
        this.set("end_date", endDate);
        this.set("price", price);
    }

    public double getPrice() {
        return Double.parseDouble(this.get("price"));
    }
    
    public String getDescription() {
        Room r = new Room(Integer.parseInt(this.get("roomid")));
        return r.getDescription();
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
        }

        return super.save();
    }
}
