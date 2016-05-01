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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import misc.SessionBean;
import models.DBO;
import models.EmployeeShift;
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
    
    /**
     * Get shifts available to this user.
     * Returns an arraylist of shifts that haven't been claimed by
     * another employee of the same type.
     * @return
     */
    public ArrayList<Shift> getAvailableShifts() {
        String role = SessionBean.getCurrentEmployee().get("role");
        StringBuilder typeList = new StringBuilder();

        switch(role) {
            case "technician":
                typeList.append("'technician'");
                break;
            case "doctor":
                typeList.append("'appointment','surgery'");
                break;
            default:
                typeList.append("'appointment','surgery','technician'");
                break;
        }

        String query = "SELECT * FROM shifts s "
                + "LEFT JOIN employee_shifts es "
                + "USING (shiftid) "
                + "WHERE (es.requested IS NULL OR es.requested = 0) "
                + "AND s.shift_type IN (" + typeList.toString() + ") "
                + "ORDER BY s.shiftid;";
        
        // Because of how reflection works in Java, we have to instantiate
        // a dummy object in order for the getCustom function to know
        // what the return type will be. :(
        Shift s = new Shift(0);
        
        return s.getCustom(query);
    }
    
    public String request(int id) {
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        System.out.println("requested " + id);

        EmployeeShift shift = new EmployeeShift(0);

        shift.set("employeeid", "" + SessionBean.getCurrentEmployee().getPk());
        shift.set("shiftid", "" + id);
        shift.set("requested", "1");

        try
        {
            shift.save();
            return "success";
        }
        catch (Exception ex) {
            Logger.getLogger(RequestShift.class.getName()).
                    log(Level.SEVERE, null, ex);
            currentInstance.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                ex.getMessage(), "You can't."));
                return "fail";
        }
    }
}
