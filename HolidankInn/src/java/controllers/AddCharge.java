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
import models.Room;
import models.RoomBooking;
import models.User;

@Named(value = "addCharge")
@SessionScoped
@ManagedBean
/**
 *
 * @author scott
 */
public class AddCharge implements Serializable {
    private Booking booking;
    private int bookingId;
    
    private String description;
    private String amount;
    
    public AddCharge() {
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
    
    public void setDescription(String desc) {
        this.description = desc;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    public String getAmount() {
        return this.amount;
    }
    
    public String addCharge() throws Exception {
        Charge c = new Charge(0);
        c.set("bookingid", this.bookingId);
        c.set("description", this.description);
        c.set("amount", this.amount);
        c.save();
        
        return "added";
    }
    
}
