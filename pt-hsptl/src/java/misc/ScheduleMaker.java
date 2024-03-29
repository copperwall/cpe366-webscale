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
package misc;

import java.util.ArrayList;
import java.util.Collections;
import models.Shift;
import models.Employee;
import models.EmployeeShift;
/**
 *
 * @author scottvanderlind
 */
public class ScheduleMaker {

    public static void run() {
        // Clean all dynamically allocated employee_shifts
        ArrayList<EmployeeShift> cleanList = EmployeeShift.getAll();

        for (EmployeeShift es : cleanList) {
            if (es.get("requested").equals("0")) {
                es.delete();
            }
        }

        ArrayList<Shift> unassigned = Shift.getUnassignedShifts();

        // Iterate over every shift that doesn't have an employee tied to
        // it.
        for (Shift s: unassigned) {
            ArrayList<Employee> eligible = Employee.getEligibleEmployees(s);
            
            Collections.shuffle(eligible);
            for (Employee e : eligible) {
                if (e.otherShiftTooClose(s)) {
                    System.out.println("too close");
                    continue;
                }

                if (s.get("shift_type").equals("surgery")) {
                    if (e.tooManySurgeries(s)) {
                        System.out.println("too many surgeries");
                        continue;
                    }
                }
                
                if (s.get("shift_type").equals("appointment")) {
                    if (e.tooManyOvernights(s)) {
                        System.out.println("too many overnights");
                        continue;
                    }
                }
                
                // Add to schedule
                EmployeeShift es = new EmployeeShift();
                es.set("shiftid", String.valueOf(s.getPk()));
                es.set("employeeid", String.valueOf(e.getPk()));
                es.set("requested", "0");

                try {
                   es.save();
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
                
                break;
            }
        }
    }
}
