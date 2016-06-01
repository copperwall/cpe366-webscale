/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import misc.SessionBean;
import models.Booking;
import models.User;
import org.jboss.weld.util.LazyValueHolder.Serializable;

@Named(value = "frontDesk")
@SessionScoped
@ManagedBean
/**
 *
 * @author scott
 */
public class FrontDesk extends Serializable {
    
    private String searchTerm;
    
    public ArrayList<Booking> getTodaysCheckins() {
        String timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date());
        Booking b = new Booking(0);
        String query = "SELECT * FROM bookings b "
                + "JOIN room_bookings rb USING (bookingid) "
                + "WHERE rb.start_date = '" + timeStamp + "' "
                + "AND b.cancelled = 0 AND b.confirmed = 1 "
                + "AND b.checked_in = 0;";
        
        return b.getCustom(query);
    }
    
    public ArrayList<Booking> getTodaysCheckouts() {
        String timeStamp = new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date());
        Booking b = new Booking(0);
        String query = "SELECT * FROM bookings b "
                + "JOIN room_bookings rb USING (bookingid) "
                + "WHERE rb.end_date = '" + timeStamp + "' "
                + "AND b.checked_in = 1 AND b.checked_out = 0;";
        
        return b.getCustom(query);
    }
    
    public String guestSearch() {
        User u = new User(0);
        ArrayList<User> results = u.userSearch(this.searchTerm);
        
        System.out.println(results);
        
        return "guest-search";
    }
    public void setSearchTerm(String term) {
        this.searchTerm = term;
    }
    public String getSearchTerm() {
        return this.searchTerm;
    }

    @Override
    protected Object computeValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
