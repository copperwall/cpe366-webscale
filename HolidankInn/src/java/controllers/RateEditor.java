/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import models.Room;
import models.RoomPrice;
/**
 *
 * @author Kyle
 */
@Named(value = "rateEditor")
@ViewScoped
@ManagedBean
public class RateEditor implements Serializable {
    private String room;
    private String start;
    private String end;
    private String rate;
    

    public ArrayList<SelectItem> getRooms()
    {
        Room room = new Room(0);
        ArrayList<Room> rooms = room.getAll();
        ArrayList<SelectItem> allRooms = new ArrayList<SelectItem>();
        
        for (Room r : rooms)
        {
            allRooms.add(new SelectItem(r.getPk(),
             r.get("number") + " - " + r.getDescription()));
        }
        return allRooms;
    }
    
    public ArrayList<RoomPrice> getAllRateOverrides() {
        RoomPrice r = new RoomPrice(0);
        return r.getAll();
    }
    
    public void removeRate(int id) {
        RoomPrice r = new RoomPrice(id);
        r.delete();
    }
    
    public void applyRate() {
        //check if rate is double, else throw error
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        
        double price = 0.0;
        try {
            price = Double.parseDouble(this.rate);
        }
        catch (Exception e)
        {
            //present an error here
            currentInstance.addMessage(null,
                 new FacesMessage(FacesMessage.SEVERITY_ERROR,
                  e.getMessage(), "Rate is not a number"));
            return;
        }
        RoomPrice rp = new RoomPrice(0);
        rp.set("roomid", room);
        rp.set("price", price);
        rp.set("start_date", start);
        rp.set("end_date", end);
        
        try {
                rp.save();
            } catch (Exception e) {
                currentInstance.addMessage(null,
                 new FacesMessage(FacesMessage.SEVERITY_ERROR,
                  e.getMessage(), "You can't."));
            }
    }
    
    /**
     * @return the room
     */
    public String getRoom() {
        return room;
    }

    /**
     * @param room the room to set
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * @return the start
     */
    public String getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public String getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * @return the rate
     */
    public String getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(String rate) {
        this.rate = rate;
    }
    
    
}
