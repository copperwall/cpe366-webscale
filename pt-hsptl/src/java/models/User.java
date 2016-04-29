package models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/*
 * Copyright (C) 2016 scottvanderlind
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 * @author scottvanderlind
 */
public class User implements Serializable
{
    private int id;
    private String login;
    private String password;
    private String role;
    private boolean dirty;
    
    public static User validate(String login, String password) {
        User u = User.find(login);
        
        return null;
    }
    
    // Find an existing user by their login string
    public static User find(String login) {
        User u = new User();
        u.setLogin(login);
        
        // If the load fails, the user doesn't exist.
        if (!u.load()) {
            return null;
        }
        
        return u;
    }
    
    public User() {
    }
    
    // Find an existing user by their id
    public User(int id) {
        this.id = id;
        //this.loadData();
    }
    
    // Create a new user object with the given login string
    public User(String login) {
        this.login = login;
    }
    
    public void save() {
        // Save the data in the object to the DB IFF the object is dirty
        if (!this.dirty) {
            return;
        }
    }
    
    private boolean load() {
        return false;
    }
    
    public void setId(int id) {
        this.id = id;
        this.dirty = true;
    }
    
    public void setLogin(String login) {
        this.login = login;
        this.dirty = true;
    }
    
    public void setPassword(String password) {
        this.password = password;
        this.dirty = true;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getLogin() {
        return this.login;
    }
    
    public String getRole() {
        return this.role;
    }
    
    public boolean isAdmin() {
        return this.role.equals("admin");
    }
    
    public boolean isDoctor() {
        return this.role.equals("doctor");
    }
    
    public boolean isTechnician() {
        return this.role.equals("technician");
    }
    
    /**
     * hasPrivilege.
     * 
     * This function might not be needed. The thought was that it would
     * help support privilege hierarchies, but since doctors and technicians
     * are next to one another in the hierarchy it might be pointless.
     * 
     * We really only care if a user is an admin or not.
     * 
     * @param privilege
     * @return 
     */
    public boolean hasPrivilege(String privilege) {
        // Admins have all privileges, so use the admin status
        // as the base status.
        boolean result = this.isAdmin();
        switch (privilege) {
            case "doctor":
                result = this.isDoctor();
                break;
            case "technician":
                result = this.isTechnician();
                break;
            default:
                break;
        }
        return result;
    }
}
