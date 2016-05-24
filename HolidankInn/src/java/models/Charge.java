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
public class Charge extends DBO {
    
    public Charge(int id) {
        super();
        
        // Our users table has the PK of userid
        this.setTable("charges", "chargeid");
        
        this.bind("bookingid", "bookingid:integer");
        this.bind("description", "description");
        this.bind("amount", "amount:real");

        if (id != 0) {
            this.id = id;
            this.fromDb = true;
            this.load();
        }
    }
    
}
