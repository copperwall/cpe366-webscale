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

@Named(value = "guestSearch")
@SessionScoped
@ManagedBean
/**
 *
 * @author scott
 */
public class GuestSearch implements Serializable {
    private String searchTerm;
    
    public GuestSearch() {
    }
    
    public void setSearchTerm(String term) {
        this.searchTerm = term;
    }
    public String getSearchTerm() {
        return this.searchTerm;
    }
    
    // Called on page load
    public void onLoad() {
    }
    
    public ArrayList<Booking> getResults() {
        Booking b = new Booking(0);
        return b.bookingSearch(this.searchTerm);
    }
    
    
}
