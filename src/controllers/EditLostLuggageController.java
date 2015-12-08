/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Bas
 */
public class EditLostLuggageController implements Initializable {
    @FXML private ComboBox cbBrand;
    @FXML private ComboBox cbType;
    @FXML private ComboBox cbColor;
    @FXML private ComboBox cbWeight;
    @FXML private ComboBox cbSize;
    @FXML private TextArea txtExtra;
    //@FXML private ComboBox cbMaterial;
    @FXML private TextField txtFlightnumber;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }  
    
    public void buildScreen(String id) {
        try {
            Connection conn = Database.initDatabase();
            String selectLuggage = "SELECT brand,color,type,weight,size,extra,material,flightNumber FROM luggage WHERE id = "+id;
            ResultSet rs = conn.createStatement().executeQuery(selectLuggage);
            if(rs.next()) {
                txtExtra.setText(new String(rs.getBytes("extra"), "UTF-8"));
                txtFlightnumber.setText(new String(rs.getBytes("flightNummer"), "UTF-8"));
                cbBrand.setValue(new String(rs.getBytes("brand"), "UTF-8"));
                cbColor.setValue(new String(rs.getBytes("color"), "UTF-8"));
                cbSize.setValue(new String(rs.getBytes("size"), "UTF-8"));
                cbType.setValue(new String(rs.getBytes("type"), "UTF-8"));
                cbWeight.setValue(new String(rs.getBytes("weight"), "UTF-8"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditLostLuggageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EditLostLuggageController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    public BorderPane getEditLostLuggageScreen() {
        BorderPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/views/EditLostLuggage.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }
}
