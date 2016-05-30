/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.ArrayList;
import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import misc.SessionBean;
import models.Booking;
import models.User;
import org.jboss.weld.util.LazyValueHolder.Serializable;

@Named(value = "homeController")
@SessionScoped
@ManagedBean
/**
 *
 * @author scott
 */
public class HomeController extends Serializable {
    
    public ArrayList<Booking> getBookings() {
        User u = SessionBean.getCurrentUser();
        
        return u.getAllBookings();
    }

    @Override
    protected Object computeValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
