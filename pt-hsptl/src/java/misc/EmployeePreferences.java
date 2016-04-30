/*
 * Copyright (C) 2016 Kyle
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

/**
 *
 * @author Kyle
 */
public class EmployeePreferences {
    private ArrayList<String> preferredTimes;
    private ArrayList<String> preferredDays;
    
    public EmployeePreferences()
    {
        preferredTimes = new ArrayList<String>();
        preferredDays = new ArrayList<String>();
    }
    
    public ArrayList<String> getPreferredTimes()
    {
        return preferredTimes;
    }
    
    public ArrayList<String> getPreferredDays()
    {
        return preferredDays;
    }
    
    public void setPreferredDays(ArrayList<String> result)
    {
        preferredDays = result;
    }
    
    public void setPreferredTimes(ArrayList<String> result)
    {
        preferredTimes = result;
    }
}
