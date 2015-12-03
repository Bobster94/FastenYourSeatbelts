/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Bas
 */
public class AddFoundLuggageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public BorderPane getAddFoundLuggageScreen() {
        BorderPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/views/AddFoundLuggage.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }
    @FXML private TextField txtBarcode;
    @FXML private TextField txtFoundAtAirport;
    
    @FXML
    public void AddFoundLuggage (ActionEvent event) {
        
        String textveld1 = txtBarcode.getText();
        System.out.println(textveld1);

        String textveld2 = txtFoundAtAirport.getText();
        System.out.println(textveld2);
        
        try(Connection conn = Database.initDatabase()){
            String SQL = "INSERT INTO luggage (brand,color,type,weight,size,barcode,lostAirport,extra,lostFound,material,date,flightNummer,idEmployee) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            
            PreparedStatement preparedStatement = conn.prepareStatement(SQL);
            preparedStatement.setString(1, "test");
	    preparedStatement.setString(2, "test");
            preparedStatement.setString(3, "test");
            preparedStatement.setInt(4, 2);
            preparedStatement.setString(5, "test");
            preparedStatement.setString(6, textveld1);
            preparedStatement.setString(7, textveld2);
            preparedStatement.setString(8, "test");
            preparedStatement.setInt(9, 1);
            preparedStatement.setString(10,"test");
            preparedStatement.setDate(11, java.sql.Date.valueOf("1996-10-24"));
            preparedStatement.setString(12,"test");
            preparedStatement.setInt(13, 1);
            preparedStatement.executeUpdate();
        
            //Close connection
            conn.close();
        }catch(SQLException ex){
              Logger.getLogger(AddFoundLuggageController.class.getName()).log(Level.SEVERE,null,ex);             
          }
       
    }
}
