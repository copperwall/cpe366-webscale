
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class DBO implements Serializable {
    private String table;
    private String pk;
    private HashMap<String, String> bindings;
    private HashMap<String, String> attributes;
    boolean fromDb;
    private boolean isDirty;
    int id;
    
    public DBO () {
        this.bindings = new HashMap<>();
        this.attributes = new HashMap<>();
        this.fromDb = false;
        this.isDirty = false;
    }
    
    protected void bind(String attribute, String column) {
        this.bindings.put(attribute, column);
    }
    
    public void setTable(String table, String pk) {
        this.table = table;
        this.pk = pk;
    }
    
    public boolean save() {
        System.out.println("Save called");
        DB db = new DB();
        Connection conn = db.getConnection();
        PreparedStatement saveStatement;
        StringBuilder query = new StringBuilder();
        int i = 1;
        int affectedRows = 0;
        
        if (!this.fromDb) {
            query.append("INSERT INTO " + this.table + " (");
            
            for (Map.Entry<String, String> entry : this.bindings.entrySet()) {
                String columnName = entry.getValue();
                String enumName = "";
            
                boolean isEnum;
                if (isEnum = columnName.contains(":")) {
                    String[] parts = columnName.split(":");
                    columnName = parts[0];
                    enumName = parts[1];
                }
            
                query.append(columnName + ",");
            }
            query.deleteCharAt(query.length()-1);
            query.append(") VALUES (");
        } else {
            query.append("UPDATE " + this.table + " SET ");
        }
        
        // Build the query
        for (Map.Entry<String, String> entry : this.bindings.entrySet()) {
            String columnName = entry.getValue();
            String enumName = "";
            
            boolean isEnum;
            if (isEnum = columnName.contains(":")) {
                String[] parts = columnName.split(":");
                columnName = parts[0];
                enumName = parts[1];
            }
            
            
            if (!this.fromDb) {
                if (isEnum) {
                    query.append("?::" + enumName + ",");
                } else {
                    query.append("?,");
                }
            } else {
                if (isEnum) {
                    query.append(columnName + " = ?::" + enumName + ",");
                } else {
                    query.append(columnName + " = ?,");
                }
            }
        }
        // Remove the trailing comma
        query.deleteCharAt(query.length()-1);
        if (!this.fromDb) {
            query.append(") ");
        }
        
        if (this.fromDb) {
            query.append(" WHERE " + this.pk + " = " + this.id);
        }
        
        try {
            // Prepare the statement
            saveStatement = conn.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            
            // Bind the values
            int idx = 1;
            for (Map.Entry<String, String> entry : this.bindings.entrySet()) {
                String objectAttribute = entry.getKey();
                String attribute = this.attributes.get(objectAttribute);
                saveStatement.setString(idx++, attribute);
            }
            
            System.out.println(saveStatement.toString());
            
            affectedRows = saveStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        if (affectedRows == 0) {
            return false;
        }
        
        // If this isn't from the DB, and there are affected rows
        // we need to update the ID of this object
        if (!this.fromDb) {
            try {
                ResultSet generatedKeys = saveStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getInt(1);
                    this.fromDb = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DBO.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        
        // If we're here, we're assuming everything went well. The object
        // is no longer dirty.
        this.isDirty = false;
        
        return true;
    }
    
    // Set a dynamic attribute of the object.
    public void set(String attribute, String value) {
        this.attributes.put(attribute, value);
        this.isDirty = true;
    }
    
    public String get(String attribute) {
        return this.attributes.get(attribute);
    }
    
    // Load the object from the db
    public boolean load() {
        if (!this.fromDb) {
            return false;
        }
        
        DB db = new DB();
        Connection conn = db.getConnection();

        PreparedStatement loadObject;
        String check = "SELECT * FROM " + this.table
         + " WHERE " + this.pk + " = ? LIMIT 1";
        ResultSet rs;
        boolean isValid;
        
        try {
            loadObject = conn.prepareStatement(check);
            loadObject.setInt(1, this.id);
            
            System.out.println(loadObject.toString());

            rs = loadObject.executeQuery();
            if (rs.next()) {
                // Iterate over binding map.
                for (Map.Entry<String, String> entry : this.bindings.entrySet()) {
                    // Get the attribute and column name from our bindings
                    String objectAttribute = entry.getKey();
                    String columnName = entry.getValue();
                    
                    String enumName = "";

                    boolean isEnum;
                    if (isEnum = columnName.contains(":")) {
                        String[] parts = columnName.split(":");
                        columnName = parts[0];
                        enumName = parts[1];
                    }
                
                    // Get the column value from the resultset and assign it to the
                    // attributes map.
                    System.out.println("assigning " + rs.getString(columnName) + " to " + columnName);
                    this.attributes.put(objectAttribute, rs.getString(columnName));
                }
            } else {
                // We don't have anything. :(
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        return true;
    }
    
}
