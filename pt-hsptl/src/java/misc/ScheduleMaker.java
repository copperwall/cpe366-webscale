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
        for (Shift test_shift : unassigned) {
            System.out.println("DEBUG: Shift " + test_shift.getPk() + " is unassigned");
        }

        // Iterate over every shift that doesn't have an employee tied to
        // it.
        for (Shift s: unassigned) {
            System.out.println("before eligible");
            ArrayList<Employee> eligible = Employee.getEligibleEmployees(s);
            System.out.println("after eligible");
            // TODO: Foreach employee, given their employeeid
            // Determine if they have any shifts within 10 hours of this one.
            // This can be determined by adding 11 hours onto the starttime
            // of the shift s.
            for (Employee e : eligible) {
                if (e.otherShiftTooClose(s)) {
                    System.out.println("too close");
                    continue;
                }
                System.out.println("not too close");
                System.out.println(s.get("shift_type"));
                if (s.get("shift_type").equals("surgery")) {
                    if (e.tooManySurgeries(s)) {
                        System.out.println("too many surgeries");
                        continue;
                    }
                    
                    System.out.println("not too many surgeries");
                }
                
                if (s.get("shift_type").equals("appointment")) {
                    if (e.tooManyOvernights(s)) {
                        System.out.println("too many overnights");
                        continue;
                    }
                    System.out.println("not too many overnights");
                }
                
                // Add to schedule
                EmployeeShift es = new EmployeeShift();
                es.set("shiftid", s.get("shiftid"));
                es.set("employeeid", e.get("employeeid"));
                es.set("requested", "0");

                try {
                    System.out.println("Trying to save");
                   es.save();
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }
                
                break;
            }
        }
    }
}
