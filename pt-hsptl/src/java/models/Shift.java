package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import static java.util.Locale.US;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class Shift extends DBO<Shift> {
    
    public Shift() {
        this(0);
    }
    
    public Shift(int id) {
        // Call the super constructor first
        super();
        // Set our table and pk
        this.setTable("shifts", "shiftid");
        // Bind our object attributes to the column names
        // bind(attribute, column)
        this.bind("day_of_week", "day_of_week:day_of_week");
        this.bind("time_of_day", "time_of_day:time_of_day");
        // The rold ccol is of type role_enum
        this.bind("shift_type", "shift_type:shift_type");
        // Binds the weekid to the weekid column.
        this.bind("weekid", "weekid:integer");
        
        // Set our DB status. If we don't pass an ID, we're a fresh
        // object
        if (id != 0) {
            this.id = id;
            this.fromDb = true;
            this.load();
        }
    }
    
    public Week getWeek() {
        return new Week(Integer.parseInt(this.get("weekid")));
    }
    
    public boolean isTechnician() {
        return this.get("shift_type").equals("technician");
    }

    private int mapDay(String day) {
        switch (day) {
            case "MONDAY":
                return Calendar.MONDAY;
            case "TUESDAY":
                return Calendar.TUESDAY;
            case "WEDNESDAY":
                return Calendar.WEDNESDAY;
            case "THURSDAY":
                return Calendar.THURSDAY;
            case "FRIDAY":
                return Calendar.FRIDAY;
            case "SATURDAY":
                return Calendar.SATURDAY;
            case "SUNDAY":
                return Calendar.SUNDAY;
        }
        return 0;
    }

    // Return the time spread for the date
    public String getShiftTime() {
        switch (this.get("time_of_day")) {
            case "EARLY_MORNING":
                return "7.30am-6.30pm";
            case "SURGERY":
                return "7.30am-6.30pm";
            case "DAY":
                return "8.30am-7.30pm";
            case "LATE":
                return "9.30am-8.30pm";
            case "OVERNIGHT":
                return "8pm – 8am";
            case "SUNDAY":
                return "8am – 8pm";
        }
        return "";
    }
    
    public String getShiftTimestamp() {
        return this.getDate() + " " + this.getStartTime();
    }
    
    public String getStartTime() {
        switch (this.get("time_of_day")) {
            case "EARLY_MORNING":
                return "07:30:00";
            case "SURGERY":
                return "07:30:00";
            case "DAY":
                return "08:30:00";
            case "LATE":
                return "09:30:00";
            case "OVERNIGHT":
                return "20:00:00";
            case "SUNDAY":
                return "08:00:00";
        }
        return "";
    }
    
    // Return a date string for the date of the shift
    public String getDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

        try
        {
            cal.setTime(sdf.parse(this.getWeek().toString()));
        }
        catch (ParseException ex)
        {
            Logger.getLogger(Shift.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(cal.get(Calendar.DAY_OF_WEEK) != this.mapDay(this.get("day_of_week"))) {
            cal.add(Calendar.DATE, 1);
        }
        //return cal.getTime().toString();
        return format1.format(cal.getTime());
    }

    public static ArrayList<Shift> getUnassignedShifts() {
        Shift s = new Shift(0);
        String query = "SELECT shifts.* FROM shifts LEFT JOIN employee_shifts USING (shiftid) WHERE employee_shiftid IS NULL ORDER BY shiftid";
        return s.getCustom(query);
    }

    // Get all the employees working this same shift (of all types)
    public ArrayList<Employee> getEmployeesOnShift() {
        Employee e = new Employee(0);
        String query = "SELECT * FROM employees e " +
                       "JOIN employee_shifts es USING (employeeid) " +
                       "JOIN shifts s USING (shiftid) " +
                       "WHERE s.day_of_week = '" + this.get("day_of_week") + "' " +
                       "AND s.time_of_day = '" + this.get("time_of_day") + "' " +
                       "AND s.weekid = " + this.get("weekid");
        return e.getCustom(query);
    }

}
