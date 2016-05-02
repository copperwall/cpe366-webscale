package models;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import misc.DB;
import misc.EmployeePreferences;

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
@Named(value = "employee")
@SessionScoped
@ManagedBean
public class Employee extends DBO<Employee> {
    
    private EmployeePreferences ep;
    
    public Employee() {
        this(0);
        ep = new EmployeePreferences();
    }
    
    public Employee(int id) {
        // Call the super constructor first
        super();
        ep = new EmployeePreferences();
        // Set our table and pk
        this.setTable("employees", "employeeid");
        // Bind our object attributes to the column names
        // bind(attribute, column)
        this.bind("login", "login");
        this.bind("password", "password");
        // The rold ccol is of type role_enum
        this.bind("role", "role:role_enum");
        
        // Set our DB status. If we don't pass an ID, we're a fresh
        // object
        if (id != 0) {
            this.id = id;
            this.fromDb = true;
            this.load();
        }
    }
    
    public String getLogin() {
        return this.get("login");
    }
    
    public ArrayList<String> getPossibleRoles() {
        ArrayList<String> roles = new ArrayList<>();
        roles.add("technician");
        roles.add("doctor");
        roles.add("admin");
        return roles;
    }
    
    public boolean otherShiftTooClose(Shift s) {
        // Grab timestamp from shift
        // Select employee_shifts where the date is less than 10 hours before
        // the timestamp of the shift, or less than 10 hours after the timestamp
        // of the shift + 11 hours.
        return true;
    }
    
    public boolean tooManySurgeries(Shift s) {
        // Grab weekid from shift
        // Select employee_shifts for shifts for this employeeid and shifts from
        // the weekid from the candidate shift and of type surgery.
        
        String q = "SELECT * "
                + "FROM shifts s "
                + "JOIN employee_shifts es "
                + "USING (shiftid) "
                + "WHERE s.weekid = " + s.get("weekid") 
                     + " AND s.shift_type = 'surgery' "
                     + "AND es.employeeid = " + this.get("employeeid");
        
        if (s.getCustom(q).size() == 0)
            return false;
        else
            return true;
    }
    
    public boolean tooManyOvernights(Shift s) {
        // Grab weekid from shift
        // Select employee_shifts for shifts for this employeeid and shifts from
        // the weekid from the candidate shift and of time_of_day overnight.
        return true;
    }

    public EmployeePreferences getEmployeePreferences()
    {
        return ep;
    }
    
    /**
     * Returns a list of Employees who do not have a day off on the day of the
     * given shift 's'.
     * 
     * @param s The candidate shift.
     * @return List of employees.
     */
    public static ArrayList<Employee> getEligibleEmployees(Shift s)
    {
        String type;
        if (s.get("shift_type").equals("technician"))
           type = "'technician'";
        else
           type = "'doctor'";
        
        String datestring = s.getShiftTimestamp();
        
        String q = "SELECT * "
                + "FROM employees e "
                + "LEFT JOIN day_off_requests d "
                + "ON e.employeeid = d.employeeid AND d.date = DATE '" + datestring + "' "
                + "WHERE role = " + type
                + " and d.employeeid IS NULL";
               /* + "FROM employees "
                + "WHERE role = " + type
                + " and employeeid not in (SELECT employeeid "
                                        + "FROM day_off_requests "
                                        + "WHERE '" + s.getDate() + "' " + "= date)";*/
                
        
        return s.getCustom(q);
    }
    
    public static ArrayList<Employee> getAll() {
        Employee e = new Employee(0);
        String query = "SELECT * FROM employees ORDER BY employeeid;";
        return e.getCustom(query);
    }
    
    public void applyPreferences()
    {
        for (String s : ep.getPreferredTimes())
        {
            System.out.println(s);
        }
        
        for (String s : ep.getPreferredDays())
        {
            System.out.println(s);
        }
    }

    /**
     * Get an employee's schedule.
     * Returns an ArrayList of Shift objects representing the shifts that
     * an employee is signed up for.
     * @return 
     */
    public ArrayList<Shift> getSchedule() {
        Shift s = new Shift(0);
        String q = "SELECT * FROM shifts JOIN employee_shifts USING (shiftid) WHERE employeeid = " + this.getPk();

        return s.getCustom(q);
    }
    
    /**
     * Get an employee's day-off-requests.
     * Returns an ArrayList of DayOff objects owned by an employee.
     * @return 
     */
    public ArrayList<DayOff> getDaysOff() {
        DayOff d = new DayOff(0);
        String query = "SELECT * FROM day_off_requests WHERE employeeid = " + this.getPk();
        
        return d.getCustom(query);
    }
}
