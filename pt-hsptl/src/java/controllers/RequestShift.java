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
package controllers;

import java.util.ArrayList;
import javax.annotation.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import models.DBO;
import models.Shift;

/**
 *
 * @author scottvanderlind
 */
@Named(value = "requestShift")
@RequestScoped
@ManagedBean
public class RequestShift
{

    /**
     * Creates a new instance of RequestShift
     */
    public RequestShift()
    {
    }
    
    public ArrayList<Shift> getAvailableShifts() {
        String query = "SELECT * FROM shifts LEFT JOIN employees_to_shifts USING (shiftid) WHERE employeeid IS NULL ORDER BY shiftid;";
        
        // Because of how reflection works in Java, we have to instantiate
        // a dummy object in order for the getCustom function to know
        // what the return type will be. :(
        Shift s = new Shift(0);
        
        return s.getCustom(query);
    }
    
    public void test() {
        System.out.println("test");
    }
}
