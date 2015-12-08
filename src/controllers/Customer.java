/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Bas
 */
public class Customer {
    private final SimpleStringProperty id;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty birthDate;
    private final SimpleStringProperty city;
    private final SimpleStringProperty street;
    private final SimpleStringProperty houseNumber;
    private final SimpleStringProperty email;
    private final SimpleStringProperty phoneNumber;
    
    Customer(String[] params) {
        id = new SimpleStringProperty(params[0]);
        firstName = new SimpleStringProperty(params[1]);
        lastName = new SimpleStringProperty(params[2]);
        birthDate = new SimpleStringProperty(params[3]);
        city = new SimpleStringProperty(params[4]);
        street = new SimpleStringProperty(params[5]);
        houseNumber = new SimpleStringProperty(params[6]);
        email = new SimpleStringProperty(params[7]);
        phoneNumber = new SimpleStringProperty(params[8]);
    }
    
    public String getID() {
        return id.get();
    }
    
    public String getFirstname() {
        return firstName.get();
    }
}
