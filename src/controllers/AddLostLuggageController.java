/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
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
    }
    
    
   
    
    
        
        
    }

