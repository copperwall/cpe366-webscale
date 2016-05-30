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
import models.User;

@Named(value = "newBooking")
@SessionScoped
@ManagedBean
/**
 *
 * @author scott
 */
public class NewBooking implements Serializable {
    
    private String bookingName;
    
    public void setBookingName(String name) {
        this.bookingName = name;
    }
    
    public String getBookingName() {
        return this.bookingName;
    }
    
    public String createBooking() {
        Booking b = new Booking(0);
        User u = SessionBean.getCurrentUser();
        
        b.set("userid", u.getPk());
        b.set("name", this.bookingName);
        // TODO: set these defaults in the Postgresql schema
        b.set("confirmed", 0);
        b.set("cancelled", 0);
        
        try {
            b.save();
            return "home";
        } catch (Exception ex) {
            Logger.getLogger(NewBooking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "home";
    }
    
}
