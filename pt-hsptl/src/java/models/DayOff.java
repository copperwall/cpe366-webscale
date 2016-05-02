package models;

import java.util.ArrayList;
import java.util.Calendar;
import misc.ScheduleMaker;

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
public class DayOff extends DBO<DayOff> {

    public DayOff() {
        this(0);
    }

    public DayOff(int id) {
        // Call the super constructor first
        super();
        // Set our table and pk
        this.setTable("day_off_requests", "day_off_requestid");
        // Bind our object attributes to the column names
        // bind(attribute, column)
        this.bind("employeeid", "employeeid:integer");
        this.bind("date", "date:date");
        this.bind("type", "type:dayoff_type");

        // Set our DB status. If we don't pass an ID, we're a fresh
        // object
        if (id != 0) {
            this.id = id;
            this.fromDb = true;
            this.load();
        }
    }

    @Override
    public boolean save() throws Exception {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        
        if (this.hasTooManyDays()) {
            throw new Exception("You have already taken too many " + this.get("type") + " days");
        }
        
        super.save();
        
        try {
            ScheduleMaker.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            super.delete();
            throw new Exception("There will not be enough people for shifts with that day off");
        }
        
        // If the type is Vacation Day then the date must be three weeks in advance
        // There cannot be eight other sick days 
        return true;
    }
    
    private boolean hasTooManyDays() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        StringBuilder query = new StringBuilder();
        
        query.append("SELECT * FROM day_off_requests");
        query.append(" WHERE EXTRACT(YEAR FROM date) = ");
        query.append(String.valueOf(year));
        query.append(" AND employeeid = ");
        query.append(this.get("employeeid"));
        
        // TODO: The query to get the number of queries for a certain year. 
        ArrayList<DayOff> daysOffThisYear = this.getCustom(query.toString());
        if (this.get("type").equals("vacation")) {
            if (daysOffThisYear.size() < 8) {
                return false;
            }
        } else {
            if (daysOffThisYear.size() < 4) {
                return false;
            }
        }
        
        return true;
    }
}
