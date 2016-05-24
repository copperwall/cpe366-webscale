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
public class User extends DBO {
    
    public User(int id) {
        super();
        
        // Our users table has the PK of userid
        this.setTable("users", "userid");
        
        // Bind our user attributes
        this.bind("login", "login");
        this.bind("password", "password");
        this.bind("role", "role:role_enum");
        
        if (id != 0) {
            this.id = id;
            this.fromDb = true;
            this.load();
        }
    }
    
}
