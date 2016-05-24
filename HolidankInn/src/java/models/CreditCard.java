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
public class CreditCard extends DBO {
    
    public CreditCard(int id) {
        super();
        
        // Our users table has the PK of userid
        this.setTable("credit_cards", "credit_cardid");
        
        // Bind our user attributes
        this.bind("number", "number");
        this.bind("cvv", "cvv");
        this.bind("expiration", "expiration:timestamp without time zone");
        this.bind("userid", "userid:integer");
        
        // Address info for the card
        this.bind("street_address", "street_address");
        this.bind("state", "state");
        this.bind("zip_code", "zip_code");
        
        if (id != 0) {
            this.id = id;
            this.fromDb = true;
            this.load();
        }
    }
    
}
