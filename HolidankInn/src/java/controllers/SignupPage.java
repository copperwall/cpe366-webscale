/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import misc.SessionBean;
import misc.DB;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import models.CreditCard;
import models.User;

@Named(value = "signupPage")
@SessionScoped
@ManagedBean
/**
 *
 * @author scott
 */
public class SignupPage implements Serializable {
    
    // User fields
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    
    // CC fields
    private String streetAddress;
    private String state;
    private String zipcode;
    private String ccnum;
    private String exp;
    private String cvv;
    
    public String createUser() {
        // Construct new User model.
        // Add all the current fields to it.
        // Save it.
        // Make sure to make it a customer type.
        User customer = new User(0);
        CreditCard cc = new CreditCard(0);
        customer.set("login", this.login);
        customer.set("password", this.password);
        customer.set("email", this.email);
        customer.set("firstname", this.firstName);
        customer.set("lastname", this.lastName);
        customer.set("type", "customer");
        
        try {
            customer.save();
        } catch (Exception e) {
            FacesContext currentInstance = FacesContext.getCurrentInstance();
            currentInstance.addMessage(null,
             new FacesMessage(FacesMessage.SEVERITY_ERROR,
              "Incorrect fields", "Please enter valid fields"));
            return "signupPage";
        }
        
        ArrayList<User> results = customer.getCustom("SELECT * FROM users WHERE login = '" + customer.get("login") + "'");
        int userid = results.get(0).getPk();
        String[] month_year = this.exp.split("/");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Integer.parseInt(month_year[0]));
        // I know, this is gross. I'm sorry.
        cal.set(Calendar.YEAR, Integer.parseInt(month_year[1]) + 2000);
        Date expiration = cal.getTime();
        
        cc.set("street_address", this.streetAddress);
        cc.set("state", this.state);
        cc.set("zip_code", this.zipcode);
        cc.set("number", this.ccnum);
        cc.set("expiration", expiration.toString());
        cc.set("cvv", this.cvv);
        cc.set("userid", userid);
        
        try {
            cc.save();
        } catch (Exception e) {
            FacesContext currentInstance = FacesContext.getCurrentInstance();
            currentInstance.addMessage(null,
             new FacesMessage(FacesMessage.SEVERITY_ERROR,
              "Incorrect fields", "Please enter valid fields"));
            return "signupPage";
        }
        
        SessionBean.setUserLogin(this.login, userid);
        return "home";
    }
        
    // User details
    public void setLogin(String login) {
        this.login = login;
    }
    
    public void setPassword(String password) {
        this.password = password; 
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * @return the address
     */
    public String getStreetAddress() {
        return streetAddress;
    }
 
    // Address details
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getZipcode() {
        return zipcode;
    }
    
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    
    public String getLogin() {
        return login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the ccnum
     */
    public String getCcnum() {
        return ccnum;
    }

    /**
     * @param ccnum the ccnum to set
     */
    public void setCcnum(String ccnum) {
        this.ccnum = ccnum;
    }

    /**
     * @return the exp
     */
    public String getExp() {
        return exp;
    }

    /**
     * @param exp the exp to set
     */
    public void setExp(String exp) {
        this.exp = exp;
    }

    /**
     * @return the cvv
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * @param cvv the cvv to set
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
