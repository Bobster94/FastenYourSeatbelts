package controllers;

import javafx.beans.property.SimpleStringProperty;

/**
 * This class is used to set and get the customer TableView content
 * @author Bas
 * @version 1.0
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
    
    /*
    * @return the customer id
    */
    public String getID() {
        return id.get();
    }

    /*
    * @return the firstname from the customer
    */    
    public String getFirstName() {
        return firstName.get();
    }
    
    /*
    * @return the lastname from the customer
    */
    public String getLastName() {
        return lastName.get();
    }

    /*
    * @return the birthdate from the customer
    */
    public String getBirthDate() {
        return birthDate.get();
    }

    /*
    * @return the city where the customer lives
    */
    public String getCity() {
        return city.get();
    }

    /*
    * @return the street where the customer lives
    */    
    public String getStreet() {
        return street.get();
    }

    /*
    * @return the housenumber from the customer
    */
    public String getHouseNumber() {
        return houseNumber.get();
    }

    /*
    * @return the email from the customer
    */    
    public String getEmail() {
        return email.get();
    }

    /*
    * @return the phonenumber from the customer
    */    
    public String getPhoneNumber() {
        return phoneNumber.get();
    }
}
