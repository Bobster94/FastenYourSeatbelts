package controllers;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Bas
 */
public class detailedLuggage {
    private final SimpleStringProperty id;
    private final SimpleStringProperty brand;
    private final SimpleStringProperty color;    
    private final SimpleStringProperty type;
    private final SimpleStringProperty weight;
    private final SimpleStringProperty size;
    private final SimpleStringProperty extra;
    private final SimpleStringProperty material;
    private final SimpleStringProperty date;
    private final SimpleStringProperty barcode;
    private final SimpleStringProperty flightNumber;
    private final SimpleStringProperty lostAirport;
    
    public detailedLuggage(String[] params) {
        id = new SimpleStringProperty(params[0]);
        brand = new SimpleStringProperty(params[1]);
        color = new SimpleStringProperty(params[2]);
        type = new SimpleStringProperty(params[3]);
        weight = new SimpleStringProperty(params[4]);
        size = new SimpleStringProperty(params[5]);
        extra = new SimpleStringProperty(params[6]);
        material = new SimpleStringProperty(params[7]);
        date = new SimpleStringProperty(params[8]);
        barcode = new SimpleStringProperty(params[9]);
        flightNumber = new SimpleStringProperty(params[10]);
        lostAirport = new SimpleStringProperty(params[11]);
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

    public String getBrand() {
        return brand.get();
    }

    public String getColor() {
        return color.get();
    }

    public String getType() {
        return type.get();
    }

    public String getWeight() {
        return weight.get();
    }

    public String getSize() {
        return size.get();
    }

    public String getExtra() {
        return extra.get();
    }

    public String getMaterial() {
        return material.get();
    }
}
