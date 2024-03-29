package models;

import java.util.ArrayList;

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
public class EmployeeShift extends DBO<EmployeeShift> {
    
    public EmployeeShift() {
        this(0);
    }
    
    public EmployeeShift(int id) {
        // Call the super constructor first
        super();
        // Set our table and pk
        this.setTable("employee_shifts", "employee_shiftid");
        // Bind our object attributes to the column names
        // bind(attribute, column)
        this.bind("shiftid", "shiftid:integer");
        this.bind("employeeid", "employeeid:integer");
        this.bind("requested", "requested:integer");
        this.bind("date", "date:timestamp without time zone");
        
        // Set our DB status. If we don't pass an ID, we're a fresh
        // object
        if (id != 0) {
            this.id = id;
            this.fromDb = true;
            this.load();
        }
    }

    public boolean save() throws Exception {
        this.calculateDate();
        return super.save();
    }

    public static ArrayList<EmployeeShift> getAll() {
        EmployeeShift es = new EmployeeShift();
        String query = "SELECT * FROM employee_shifts";

        return es.getCustom(query);
    }

    private void calculateDate() {
        Shift s = new Shift(Integer.parseInt(this.get("shiftid")));
        this.set("date", s.getShiftTimestamp());
    }

}
