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
    
    private String startDate;
    private String endDate;
    private String view;
    private String bed;
    
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
        return this.startDate;
    }
    
    public void setStartDate(String date) {
        this.startDate = date;
    }
    
    public String getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(String date) {
        this.endDate = date;
    }
    
    public String getRequestedView() {
        return this.view;
    }
    
    public void setRequestedView(String type) {
        this.view = type;
    }
    
    public String getRequestedBed() {
        return this.bed;
    }
    
    public void setRequestedBed(String type) {
        this.bed = type;
    }
    
    public ArrayList<SelectItem> getViewTypes() {
        ArrayList<SelectItem> views = new ArrayList<>();
        
        views.add(new SelectItem("ocean", "Ocean"));
        views.add(new SelectItem("pool", "Pool"));
        
        return views;
    }
    
    public ArrayList<SelectItem> getBedTypes() {
        ArrayList<SelectItem> beds = new ArrayList<>();
        
        beds.add(new SelectItem("single", "Single King"));
        beds.add(new SelectItem("double", "Two Queens"));
        
        return beds;
    }
    
    public String createReservation() throws Exception {
        // TODO: Find rooms that meet the input criteria.
        //       If there are no rooms available, throw an error
        ArrayList<Room> availableRooms =
         Room.getAvailableRooms(this.startDate, this.endDate,
         this.view, this.bed);
        
        if (availableRooms.isEmpty()) {
            // We have large problems, mi amigo.
            throw new Exception("There are no rooms available meeting your criteria.");
        }
        // Select a room to book, get that room's price (a function of the Room object)
        Room chosenRoom = availableRooms.get(0);
        System.out.println("Chosen roomid: " + chosenRoom.getPk());

        RoomBooking rb = RoomBooking.factory(this.bookingId, chosenRoom.getPk(),
                this.startDate, this.endDate);
        rb.save();
        
        return "reserved";
    }
    
}
