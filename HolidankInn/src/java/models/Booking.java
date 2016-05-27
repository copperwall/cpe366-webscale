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
        // GET THE PRICE! This is funky. TODO Rethink how this works
        float price = 0;
        RoomBooking roomBooking =
         new RoomBooking(this.getPk(), roomid, startDate, endDate, price);

        // TODO: Don't swallow this exception
        // There will be an exception here if the room is not available
        // during those dates
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
        RoomBooking rb = new RoomBooking(0);
        String query = "SELECT * FROM room_bookings WHERE bookingid = "
         + this.getPk();

        return rb.getCustom(query);
    }

    // Get all the charges for this stay
    public ArrayList<Charge> getCharges() {
        Charge c = new Charge(0);
        String query = "SELECT * FROM charges WHERE bookingid = "
         + this.getPk();

        return c.getCustom(query);
    }

    // Get all the payments made toward this stay
    public ArrayList<Payment> getPayments() {
        Payment p = new Payment(0);
        String query = "SELECT * FROM payments WHERE bookingid = "
         + this.getPk();

        return p.getCustom(query);
    }

    // Get the total amount owed on this booking
    public double getTotalAmount() {
        double total = 0;
        // get all the rooms
        ArrayList<RoomBooking> rooms = this.getRooms();
        // get all the charges
        ArrayList<Charge> charges = this.getCharges();
        // Add them up!

        for (int i = 0; i < rooms.size(); i++) {
            total += rooms.get(i).getPrice();
        }

        for (int n = 0; n < charges.size(); n++) {
            total += charges.get(n).getAmount();
        }

        return total;
    }

    // Get the total amount paid on this booking
    public double getPaidAmount() {
        double paid = 0;
        // get al the payments
        ArrayList<Payment> payments = this.getPayments();
        // Add them up!
        for (int i = 0; i < payments.size(); i++) {
            paid += payments.get(i).getAmount();
        }

        return paid;
    }

    // Get the unpaid balance on this doublebooking
    public double getBalance() {
        double total = this.getTotalAmount();
        double paid = this.getPaidAmount();
        
        // The balance is the total - the amount paid
        return total - paid;
    }

}
