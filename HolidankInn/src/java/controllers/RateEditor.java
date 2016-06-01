/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import models.Room;
import models.RoomPrice;
/**
 *
 * @author Kyle
 */
public class RateEditor {
    private String room;
    private String start;
    private String end;
    private String rate;
    
    public ArrayList<SelectItem> getPossibleDays()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar start = Calendar.getInstance();
        start.add(Calendar.DAY_OF_MONTH, 21);
        int daysPossible = 100;
        ArrayList<Date> dates = getDatesFromStart(start.getTime(), daysPossible);
        ArrayList<SelectItem> days = new ArrayList<SelectItem>();
        
        for (Date d : dates)
        {
            days.add(new SelectItem(dateFormat.format(d)));
        }
        return days;
    }
    
    public ArrayList<SelectItem> getRooms()
    {
        Room room = new Room(0);
        ArrayList<Room> rooms = room.getAll();
        ArrayList<SelectItem> allRooms = new ArrayList<SelectItem>();
        
        for (Room r : rooms)
        {
            allRooms.add(new SelectItem(room.getPk(), room.get("number") + "-" + room.getDescription()));
        }
        return allRooms;
    }
    
    public void applyRate(String rate)
    {
        //check if rate is double, else throw error
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        
        double price;
        try {
            price = Double.parseDouble(rate);
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

    
    public ArrayList<Date> getDatesFromStart(Date startdate, int range)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        Calendar temp = new GregorianCalendar();
        temp.setTime(startdate);
        temp.add(Calendar.DATE, range);
        Date enddate = temp.getTime();
        
        while (calendar.getTime().before(enddate))
        {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
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
