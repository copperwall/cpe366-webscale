package misc;

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

    private static Connection conn = null;

    public Connection getConnection() {
        if (this.conn != null) {
            return conn;
        }
        try {
            this.conn = DriverManager.getConnection(
                    "jdbc:postgresql://devopps.me/acme_hotel", "postgres",
                    "databasesrcool");
        } catch (SQLException e) {
            System.out.println("Could not connect to postgresql");
            System.out.println(e.getMessage());
            return null;
        }
        
        return this.conn;
    }
    
    public DB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Postgresql Driver not found");
        }
    }
}