/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

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
        return "****";
    }

}
