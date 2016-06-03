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
public class Payment extends DBO {
    
    public Payment(int id) {
        super();
        
        // Our users table has the PK of userid
        this.setTable("payments", "paymentid");
        
        this.bind("bookingid", "bookingid:integer");
        this.bind("amount", "amount:real");
        this.bind("date", "date:timestamp without time zone");
        
        if (id != 0) {
            this.id = id;
            this.fromDb = true;
            this.load();
        }
    }
    
    public double getAmount() {
        return Double.parseDouble(this.get("amount"));
    }
    
    public String getCardDescription() {
        CreditCard cc = new CreditCard(0);
        String query = "SELECT * FROM credit_cards "
                + "JOIN bookings USING (userid) "
                + "WHERE bookingid = " + this.get("bookingid");
        ArrayList<CreditCard> cards = cc.getCustom(query);
        String last4;
        if (cards.isEmpty()) {
           last4 = "6969";
        } else {
            cc = cards.get(0);
            String full = cc.get("number");
            last4 = full.substring(Math.max(0, full.length() - 4));
        }

        return "**** **** **** " + last4;
    }

}
