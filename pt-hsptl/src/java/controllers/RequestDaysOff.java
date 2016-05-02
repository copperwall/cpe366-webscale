/*
 * Copyright (C) 2016 Kyle
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
package controllers;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import misc.SessionBean;
import models.DayOff;
/**
 *
 * @author Kyle
 */
public class RequestDaysOff implements Serializable {
    private String vacStart;
    private String vacEnd;
    private String sickDay;
    private String sickMonth;
    private String sickYear;
    
    public RequestDaysOff()
    {
        vacStart = "";
        vacEnd = "";
        sickDay = "";
        sickMonth = "";
        sickYear = "";
    }
    
    public String applyVacDays()
    {
        //make sure first date is before second date, make new table entry for each date
        //pass current employee id into this function from xhtml page??
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date start = null;
        Date end = null;
        
        try {
            start = df.parse(vacStart);
            end = df.parse(vacEnd);
        }
        catch (Exception e) {System.out.println("Exception while parsing vacaction days: " + e.getMessage());}
        
        ArrayList<Date> vacation = getDatesBetween(start, end);
        
        for (Date d : vacation)
        {
            int employeeid = SessionBean.getCurrentEmployee().getPk();
            FacesContext currentInstance = FacesContext.getCurrentInstance();

            //MAKE 3 SEPARATE selectOneMenu IN HTML for DAY, MONTH, YEAR!!!
            DayOff day = new DayOff(0);
            day.set("employeeid", "" + employeeid);
            day.set("type", "vacation");
            day.set("date", df.format(d));

            try {
                day.save();
            } catch (Exception e) {
                currentInstance.addMessage(null,
                 new FacesMessage(FacesMessage.SEVERITY_ERROR,
                  e.getMessage(), "You can't."));
                return "fail";
            }
        }
        
        return "success";
    }
    
    public String applySickDays()
    {
        int employeeid = SessionBean.getCurrentEmployee().getPk();
        FacesContext currentInstance = FacesContext.getCurrentInstance();

        //MAKE 3 SEPARATE selectOneMenu IN HTML for DAY, MONTH, YEAR!!!
        DayOff day = new DayOff(0);
        day.set("employeeid", "" + employeeid);
        day.set("type", "sick");
        day.set("date", sickDay);
        
        try {
            day.save();
        } catch (Exception e) {
            currentInstance.addMessage(null,
             new FacesMessage(FacesMessage.SEVERITY_ERROR,
              e.getMessage(), "You can't."));
            return "fail";
        }

        return "success";
    }
    
    public ArrayList<SelectItem> getPossibleVacationDays()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar start = Calendar.getInstance();
        start.add(Calendar.DAY_OF_MONTH, 21);
        int daysPossible = 100;
        ArrayList<Date> dates = getDatesFromStart(start.getTime(), daysPossible);
        ArrayList<SelectItem> vacDays = new ArrayList<SelectItem>();
        
        for (Date d : dates)
        {
            vacDays.add(new SelectItem(dateFormat.format(d)));
        }
        return vacDays;
    }
    
    public ArrayList<SelectItem> getPossibleSickDays()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar start = Calendar.getInstance();
        start.add(Calendar.DAY_OF_MONTH, 1);
        int daysPossible = 21;
        ArrayList<Date> dates = getDatesFromStart(start.getTime(), daysPossible);
        ArrayList<SelectItem> vacDays = new ArrayList<SelectItem>();
        
        for (Date d : dates)
        {
            vacDays.add(new SelectItem(dateFormat.format(d)));
        }
        return vacDays;
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
    
    public ArrayList<Date> getDatesBetween(Date startdate, Date enddate)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);
        
        Calendar temp = new GregorianCalendar();
        temp.setTime(enddate);
        temp.add(Calendar.DATE, 1);
        Date end = temp.getTime();
        
        while (calendar.getTime().before(end))
        {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    /**
     * @return the vacStart
     */
    public String getVacStart() {
        return vacStart;
    }

    /**
     * @param vacStart the vacStart to set
     */
    public void setVacStart(String vacStart) {
        this.vacStart = vacStart;
    }

    /**
     * @return the vacEnd
     */
    public String getVacEnd() {
        return vacEnd;
    }

    /**
     * @param vacEnd the vacEnd to set
     */
    public void setVacEnd(String vacEnd) {
        this.vacEnd = vacEnd;
    }

    /**
     * @return the sickDay
     */
    public String getSickDay() {
        return sickDay;
    }

    /**
     * @param sickDay the sickDay to set
     */
    public void setSickDay(String sickDay) {
        this.sickDay = sickDay;
    }

    /**
     * @return the sickMonth
     */
    public String getSickMonth() {
        return sickMonth;
    }

    /**
     * @param sickMonth the sickMonth to set
     */
    public void setSickMonth(String sickMonth) {
        this.sickMonth = sickMonth;
    }

    /**
     * @return the sickYear
     */
    public String getSickYear() {
        return sickYear;
    }

    /**
     * @param sickYear the sickYear to set
     */
    public void setSickYear(String sickYear) {
        this.sickYear = sickYear;
    }
}
