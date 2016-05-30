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

@Named(value = "reserveRoom")
@SessionScoped
@ManagedBean
/**
 *
 * @author scott
 */
public class ReserveRoom implements Serializable {
    private Booking booking;
    private int bookingId;
    
    public ReserveRoom() {
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
    
    public String getStartDate() {
        return "";
    }
    
    public void setStartDate(String date) {
        
    }
    
    public String getEndDate() {
        return "";
    }
    
    public void setEndDate(String date) {
        
    }
    
    public String getRequestedView() {
        return "";
    }
    
    public void setRequestedView(String type) {
        
    }
    
    public String getRequestedBed() {
        return "";
    }
    
    public void setRequestedBed(String type) {
        
    }
    
    public ArrayList<SelectItem> getViewTypes() {
        ArrayList<SelectItem> views = new ArrayList<>();
        
        views.add(new SelectItem("Ocean"));
        views.add(new SelectItem("Pool"));
        
        return views;
    }
    
    public ArrayList<SelectItem> getBedTypes() {
        ArrayList<SelectItem> beds = new ArrayList<>();
        
        beds.add(new SelectItem("Single King"));
        beds.add(new SelectItem("Two Queens"));
        
        return beds;
    }
    
    public String createReservation() {
        
        // TODO: Find rooms that meet the input criteria.
        //       If there are no rooms available, throw an error
        //       Select a room to book, get that room's price (a function of the Room object)
        //       call `new RoomBooking(this.bookingId, room.getPk(), startDate, endDate, price)`
        
        return "reserved";
    }
    
}
