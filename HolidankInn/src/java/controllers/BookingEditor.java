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
    
    public ArrayList<RoomBooking> getRooms() {
        return this.booking.getRooms();
    }
    
    public String confirmBooking() {
        System.out.println("HOME");
        return "home";
    }
    
    public String removeRoomBooking(int roombookingid) {
        System.out.println("Removing RoomBooking " + roombookingid);
        this.booking.removeRoomBooking(roombookingid);
        return "removed";
    }
    
}
