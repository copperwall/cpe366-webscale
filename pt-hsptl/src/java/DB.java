/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Chris Opperwall
 */
public class DB {
    public Connection getConnection() {
        Connection connection;
        
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://devopps.me/project1", "postgres",
                    "databasesrcool");
        } catch (SQLException e) {
            System.out.println("Could not connect to postgresql");
            System.out.println(e.getMessage());
            return null;
        }
        
        return connection;
    }
    
    public DB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Postgresql Driver not found");
        }
    }
}