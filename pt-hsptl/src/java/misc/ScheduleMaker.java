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

/**
 *
 * @author scottvanderlind
 */
public class ScheduleMaker {

    public static void run() {
        ArrayList<Shift> unassigned = Shift.getUnassignedShifts();

        // Iterate over every shift that doesn't have an employee tied to
        // it.
        for (Shift s: unassigned) {

        }
    }
}
