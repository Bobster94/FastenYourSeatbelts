package controllers;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Bas
 */
public class detailedCustomer {
    private final SimpleStringProperty id;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty insertion;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty birthDate;
    private final SimpleStringProperty country;
    private final SimpleStringProperty city;
    private final SimpleStringProperty zipCode;
    private final SimpleStringProperty street;
    private final SimpleStringProperty houseNumber;
    private final SimpleStringProperty email;
    private final SimpleStringProperty phoneNumber;
    
    detailedCustomer(String[] params) {
        id = new SimpleStringProperty(params[0]);
        firstName = new SimpleStringProperty(params[1]);
        insertion = new SimpleStringProperty(params[2]);
        lastName = new SimpleStringProperty(params[3]);
        birthDate = new SimpleStringProperty(params[4]);
        country = new SimpleStringProperty(params[5]);
        city = new SimpleStringProperty(params[6]);
        zipCode = new SimpleStringProperty(params[7]);
        street = new SimpleStringProperty(params[8]);
        houseNumber = new SimpleStringProperty(params[9]);
        email = new SimpleStringProperty(params[10]);
        phoneNumber = new SimpleStringProperty(params[11]);
    } 

    public String getId() {
        return id.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getInsertion() {
        return insertion.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getBirthDate() {
        return birthDate.get();
    }

    public String getCountry() {
        return country.get();
    }

    public String getCity() {
        return city.get();
    }

    public String getZipCode() {
        return zipCode.get();
    }

    public String getStreet() {
        return street.get();
    }

    public String getHouseNumber() {
        return houseNumber.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }
}
