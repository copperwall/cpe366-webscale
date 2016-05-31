package controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;

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
@Named(value = "employeeEditor")
@ViewScoped
@ManagedBean
public class EmployeeCreator implements Serializable
{

    private User editing;
    private int id;
    
    /**
     * Creates a new instance of EmployeeEditor
     */
    public EmployeeCreator()
    {
    }
    
    public void onload() {
        this.editing = new User(0);
    }
    
    public ArrayList<User> getAll() {
        User u = new User(0);
        return u.getAll();
    }
    
    public int getId() {
        return this.id;
    }

    public String getLogin() {
        return this.editing.get("login");
        //return "";
    }

    public void setLogin(String login) {
        this.editing.set("login", login);
    }

    public String getPassword() {
        return this.editing.get("password");
        //return "";
    }

    public void setPassword(String password) {
        this.editing.set("password", password);
    }
    
    public String getFirstName() {
        return this.editing.get("firstname");
    }
    
    public void setFirstName(String firstname) {
        this.editing.set("firstname", firstname);
    }
    
    public String getLastName() {
        return this.editing.get("lastname");
    }
    
    public void setLastName(String lastname) {
        this.editing.set("lastname", lastname);
    }
    
    public String getEmail() {
        return this.editing.get("email");
    }
    
    public void setEmail(String email) {
        this.editing.set("email", email);
    }

    public String getRole() {
        return this.editing.get("type");
    }

    public void setRole(String role) {
        this.editing.set("type", role);
    }

    public User getRequestedEmployee() {
        return this.editing;
    }

    public String createEmployee() {
        System.out.println("login: " + this.editing.get("login"));
        System.out.println("Password: " + this.editing.get("password"));
        FacesContext currentInstance = FacesContext.getCurrentInstance();

        try {
            this.editing.save();
        }
        catch (Exception e) {
            Logger.getLogger(EmployeeCreator.class.getName()).
                    log(Level.SEVERE, null, e);
            currentInstance.addMessage(null,
             new FacesMessage(FacesMessage.SEVERITY_ERROR,
              e.getMessage(), "You can't."));
            return "fail";
        }
        
        this.id = this.editing.getPk();
        //System.out.println("new login: " + this.login);
        return "updated";
    }
    
    public ArrayList<SelectItem> getPossibleTypes() {
       ArrayList<SelectItem> types = new ArrayList<SelectItem>(); 
       
       types.add(new SelectItem("admin", "Admin"));
       types.add(new SelectItem("employee", "Employee"));
       types.add(new SelectItem("customer", "Customer"));
       
       return types;
    }

}
