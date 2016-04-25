
import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

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
@Named(value = "employeeEditor")
@SessionScoped
@ManagedBean
public class EmployeeEditor
{

    /**
     * Creates a new instance of EmployeeEditor
     */
    public EmployeeEditor()
    {
    }
    
    public Employee getRequestedEmployee() {
        String idstring = FacesContext.getCurrentInstance().getExternalContext()
         .getRequestParameterMap().get("id");
        int id = Integer.parseInt(idstring);
        
        return new Employee(id);
    }
    
}
