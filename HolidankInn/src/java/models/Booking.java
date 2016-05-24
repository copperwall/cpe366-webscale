/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author scott
 */
public class Booking extends DBO {
    
    public Booking(int id) {
        super();
        
        // Our users table has the PK of userid
        this.setTable("bookings", "bookingid");
        
        this.bind("userid", "userid:integer");
        this.bind("cancelled", "cancelled:integer");
        
        if (id != 0) {
            this.id = id;
            this.fromDb = true;
            this.load();
        }
    }
    
    // Get the user that has created this booking
    public User getUser() {
        return new User(Integer.parseInt(this.get("userid")));
    }
    
    // Some helpful setters
    
    public boolean cancel() {
        // If the current date is inside of or after the booking window, 
        // you cannot cance the booking.
        
        // If the current date is before the booking has started, you can
        // cancel.
        this.set("cancelled", "1");
        return true;
    }
    
    // Add a room to the booking reservation
    public boolean addRoom(int roomid, String startDate, String endDate) {
        // Find the room that we want to reserve.
        Room room = new Room(roomid);
        
        // If the room is not available during those dates, throw a fit.
        if (!room.isAvailable(startDate, endDate)) {
            // TODO: THROW EXCEPTION TO NOTIFY USER
            return false;
        }
        
        // If it is available, create a new RoomBooking
        // GET THE PRICE! This is funky. TODO Rethink how this works
        float price = 0;
        RoomBooking roomBooking =
         new RoomBooking(this.getPk(), startDate, endDate, price);
        
        // TODO: Don't swallow this exception
        try {
            // Save!
            roomBooking.save();
        } catch (Exception ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    
    /// Some helpful getters
    
    // Get the room bookings for this stay
    public ArrayList<RoomBooking> getRooms() {
        return new ArrayList<RoomBooking>();
    }
    
    // Get all the charges for this stay
    public ArrayList<Charge> getCharges() {
        return new ArrayList<Charge>();
    }
    
    // Get all the payments made toward this stay
    public ArrayList<Payment> getPayments() {
        return new ArrayList<Payment>();
    }
    
    // Get the total amount owed on this booking
    public double getTotalAmount() {
        // get all the rooms
        // get all the charges
        // Add them up!
        return 0.00;
    }
    
    // Get the total amount paid on this booking
    public double getPaidAmount() {
        // get al the payments
        // Add them up!
        return 0.00;
    }
    
    // Get the unpaid balance on this doublebooking
    public double getBalance() {
        double total = this.getTotalAmount();
        double paid = this.getPaidAmount();
        
        // The balance is the total - the amount paid
        return total - paid;
    }
    
    
    
}
