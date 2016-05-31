/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;

/**
 *
 * @author scott
 */
public class User extends DBO {
    
    public User(int id) {
        super();
        
        // Our users table has the PK of userid
        this.setTable("users", "userid");
        
        // Bind our user attributes
        this.bind("login", "login");
        this.bind("password", "password");
        this.bind("type", "type:user_type");
        
        this.bind("firstname", "firstname");
        this.bind("lastname", "lastname");
        this.bind("email", "email");
        
        if (id != 0) {
            this.id = id;
            this.fromDb = true;
            this.load();
        }
    }
    
    public boolean isAdmin() {
        return this.get("type").equals("admin");
    }
    
    public boolean isEmployee() {
        return this.isAdmin() || this.get("type").equals("employee");
    }
    
    public ArrayList<CreditCard> getCreditCards() {
        CreditCard cc = new CreditCard(0);
        
        String query = "SELECT * FROM credit_cards"
                + "WHERE userid = " + this.getPk();
        
        return cc.getCustom(query);
    }
    
    public ArrayList<Booking> getAllBookings() {
        Booking b = new Booking(0);
        String query = "SELECT * FROM bookings"
                + " WHERE userid = " + this.getPk();
        
        return b.getCustom(query);
    }
    
    public ArrayList<Booking> getConfirmedBookings() {
        Booking b = new Booking(0);
        String query = "SELECT * FROM bookings"
                + " WHERE userid = " + this.getPk()
                + " AND confirmed = 1 AND cancelled = 0";
        
        return b.getCustom(query);
    }
    
    public ArrayList<Booking> getUnconfirmedBookings() {
        Booking b = new Booking(0);
        String query = "SELECT * FROM bookings"
                + " WHERE userid = " + this.getPk()
                + " AND confirmed = 0 AND cancelled = 0";
        
        return b.getCustom(query);
    }
    
    public ArrayList<Booking> getCancelledBookings() {
        Booking b = new Booking(0);
        String query = "SELECT * FROM bookings"
                + " WHERE userid = " + this.getPk()
                + " AND cancelled = 1";
        
        return b.getCustom(query);
    }
    
}
