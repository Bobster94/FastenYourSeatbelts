/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bas
 */
public class Database {
    
    public static Connection initDatabase() {
        Connection conn = null;
        try {
            //Load the JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");
            
            //Connect to a database
            conn = DriverManager.getConnection("jdbc:mysql://localhost/fys", "root", "root");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return conn;
    }
    
}
