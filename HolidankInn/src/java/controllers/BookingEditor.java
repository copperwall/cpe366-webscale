/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import misc.SessionBean;
import models.Booking;
import models.Charge;
import models.Payment;
import models.Room;
import models.RoomBooking;
import models.User;

@Named(value = "bookingEditor")
@SessionScoped
@ManagedBean
/**
 *
 * @author scott
 */
public class BookingEditor implements Serializable {
    private Booking booking;
    private int bookingId;
    
    public BookingEditor() {
    }
    
    // Called on page load
    public void onLoad() {
        this.booking = new Booking(this.bookingId);
    }
    
    public void setBookingId(int id) {
        this.bookingId = id;
    }
    
    public int getBookingId() {
        return this.bookingId;
    }
    
    public String getBookingName() {
        return this.booking.get("name");
    }
    
    public Booking getBooking() {
        return this.booking;
    }
    
    public boolean canEditCharges() {
        User u = SessionBean.getCurrentUser();
        return u.isEmployee() && !this.booking.isCheckedOut();
    }
    
    public boolean canEditBooking() {
        User u = SessionBean.getCurrentUser();
        return u.isEmployee() && !this.booking.isCheckedOut();
    }
    
    public boolean canCheckIn() {
        User u = SessionBean.getCurrentUser();
        return u.isEmployee() && !this.booking.isCheckedIn();
    }
    
    public boolean canCheckOut() {
        User u = SessionBean.getCurrentUser();
        return u.isEmployee() && this.booking.isCheckedIn() && !this.booking.isCheckedOut();
    }
    
    public String checkIn() throws Exception {
        this.booking.checkIn();
        this.booking.save();
        return "checked-in";
    }
    
    public String checkOut() throws Exception {
        this.booking.checkOut();
        this.booking.save();
        return "checked-out";
    }
    
    public ArrayList<RoomBooking> getRooms() {
        return this.booking.getRooms();
    }
    
    public ArrayList<Charge> getCharges() {
        return this.booking.getCharges();
    }
    
    public ArrayList<Payment> getPayments() {
        return this.booking.getPayments();
    }
    
    public String confirmBooking() throws Exception {
        this.booking.confirm();
        this.booking.save();
        return "home";
    }
    
    public String cancelBooking() throws Exception {
        this.booking.cancel();
        this.booking.save();
        return "home";
    }
    
    public String removeRoomBooking(int roombookingid) {
        System.out.println("Removing RoomBooking " + roombookingid);
        this.booking.removeRoomBooking(roombookingid);
        return "removed";
    }
    
    public String removeCharge(int chargeid) {
        this.booking.removeCharge(chargeid);
        return "removed";
    }
    
}
