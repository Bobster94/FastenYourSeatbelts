package controllers;

import javafx.beans.property.SimpleStringProperty;

/**
 * This class will store short information about luggage
 * @author Bas
 */
public class Luggage {
    private final SimpleStringProperty id;
    private final SimpleStringProperty barcode;
    private final SimpleStringProperty date;
    private final SimpleStringProperty flightNumber;
    private final SimpleStringProperty lostAirport;
    
    public Luggage(String[] params) {
        id = new SimpleStringProperty(params[0]);
        barcode = new SimpleStringProperty(params[1]);
        date = new SimpleStringProperty(params[2]);
        flightNumber = new SimpleStringProperty(params[3]);
        lostAirport = new SimpleStringProperty(params[4]);
    }
    
    public String getID() {
        return id.get();
    }
    public String getBarcode() {
        return barcode.get();
    }
    public String getDate() {
        return date.get();
    }
    
    public String getFlightNumber() {
        return flightNumber.get();
    }
    
    public String getLostAirport() {
        return lostAirport.get();
    }
}
