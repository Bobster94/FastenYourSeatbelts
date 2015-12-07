/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author Bobster
 */
public class Admin {
    public static final int user = 0;
    public static final int manager = 1;
    
    private int admin;
     
    public Admin() {
        admin = user;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int manager) {
        this.admin = manager;
    }
    
   
}
