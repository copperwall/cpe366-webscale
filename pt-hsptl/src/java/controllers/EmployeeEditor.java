package controllers;

import java.io.Serializable;
import models.Employee;
import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
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
@ViewScoped
@ManagedBean
public class EmployeeEditor implements Serializable
{

    private Employee editing;
    private int id;

    /**
     * Creates a new instance of EmployeeEditor
     */
    public EmployeeEditor()
    {
    }
    
    public void onload() {
        System.out.println("onload for id " + id);
        this.editing = new Employee(id);
    }

    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return this.editing.get("login");
    }

    public void setLogin(String login) {
        this.editing.set("login", login);
    }

    public String getPassword() {
        return this.editing.get("password");
    }

    public void setPassword(String password) {
        this.editing.set("password", password);
    }

    public String getRole() {
        return this.editing.get("role");
    }

    public void setRole(String role) {
        this.editing.set("role", role);
    }

    public Employee getRequestedEmployee() {
        return this.editing;
    }

    public String updateEmployee() {
        System.out.println("login: " + this.editing.get("login"));
        System.out.println("Password: " + this.editing.get("password"));

        try {
            this.editing.save();
        } catch (Exception e) {
            System.err.println("Error in updating employee " + this.editing.get("employeeid"));
        }
        //System.out.println("new login: " + this.login);
        return "updated";
    }
    
    public String delete() {
        this.editing.delete();
        return "deleted";
    }

}
