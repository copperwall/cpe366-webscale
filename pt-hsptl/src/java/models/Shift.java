package models;

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
        
        // Set our DB status. If we don't pass an ID, we're a fresh
        // object
        if (id != 0) {
            this.id = id;
            this.fromDb = true;
            this.load();
        }
    }
    
    public static void getAvailableShifts() {
        
    }
}
