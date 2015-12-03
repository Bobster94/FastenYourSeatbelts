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
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Bas
 */
public class AddLostLuggageController implements Initializable {
    @FXML
    private BorderPane SaveLost;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public BorderPane getAddLostLuggageScreen() {
        BorderPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/views/AddLostLuggage.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }
    @FXML private TextArea TXTCustomerfirstname;
    @FXML private TextArea TXTCustomerlastname;
    @FXML private TextArea TXTVactationname;
    @FXML private TextArea TXTVactationperiod;
    @FXML private TextArea TXTVactationzipcode;
    @FXML private TextArea TXTVactation1;
    @FXML private TextArea TXTVactation2;
    @FXML private TextArea TXTVactation3;
    @FXML private TextArea TXTVactation4;
    @FXML
    
    public void savechanges(ActionEvent event) {
    
        String name = TXTCustomerfirstname.getText();
        System.out.println(name);

        String namelast = TXTCustomerlastname.getText();
        System.out.println(namelast);  

        String vacation = TXTVactationname.getText();
        System.out.println(vacation);  

        String period = TXTVactationperiod.getText();
        System.out.println(period); 

        String zipcode = TXTVactationzipcode.getText();
        System.out.println(zipcode); 
        
        String vacation1 = TXTVactation1.getText();
        System.out.println(vacation1);
        
        String vacation2 = TXTVactation2.getText();
        System.out.println(vacation2);
        
        String vacation3 = TXTVactation3.getText();
        System.out.println(vacation3);
        
        String vacation4 = TXTVactation4.getText();
        System.out.println(vacation4);
        
        
        try(Connection conn = Database.initDatabase()){
            String SQL = "INSERT INTO luggage (brand,color,type,weight,size,barcode,lostAirport,"
                    + "extra,lostFound,material,date,flightNummer,idEmployee) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            
            PreparedStatement preparedStatement = conn.prepareStatement(SQL);
            preparedStatement.setString(1, "test");
	    preparedStatement.setString(2, "test");
            preparedStatement.setString(3, "test");
            preparedStatement.setInt(4, 2);
            preparedStatement.setString(5, "test");
            preparedStatement.setString(6, vacation4);
            preparedStatement.setString(7, vacation3);
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

