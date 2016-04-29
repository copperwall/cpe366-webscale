package misc;

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

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.inject.Named;
//import javax.enterprise.context.Dependent;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.Employee;
/**
 *
 * @author scottvanderlind
 */
@Named(value = "sessionBean")
@SessionScoped
@ManagedBean
public class SessionBean implements Serializable
{            
    private static Employee currentEmployee;
    
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }
 
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }
 
    public static void setUserLogin(String login, int id) {
        HttpSession session = getSession();
        session.setAttribute("userlogin", login);
        
        currentEmployee = new Employee(id);
    }
    
    public static String getUserLogin() {
        HttpSession session = getSession();
        if (session.getAttribute("userlogin") != null) {
            return session.getAttribute("userlogin").toString();
        }
        return "";
    }
    
    public static boolean isLoggedIn() {
        HttpSession session = getSession();
        return session.getAttribute("userlogin") != null;
    }
    
    public String getLogin() {
        return getUserLogin();
    }
    
    public Employee getCurrentEmployee()
    {
        return currentEmployee;
    }

}
