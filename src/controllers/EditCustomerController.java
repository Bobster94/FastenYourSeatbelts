/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.AddCustomerController;
import controllers.Database;
import controllers.LoginController;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class EditCustomerController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        }
;
    @FXML private TextField first;
    @FXML private TextField country;
    @FXML private TextField last;
    @FXML private TextField city;
    @FXML private TextField email;
    @FXML private TextField street;
    @FXML private TextField phone;
    @FXML private TextField zip;
    
    
    
   
    
    @FXML
    private void saveCustomer(ActionEvent event) {
        String name = first.getText();
        System.out.println(name);
        String counrty = country.getText();
        System.out.println(counrty);
        String lastname = last.getText();
        System.out.println(lastname);
        String stad = city.getText();
        System.out.println(stad);
        String address = email.getText();
        System.out.println(address);
        String straat = street.getText();
        System.out.println(straat);
        int telefoon = Integer.parseInt(phone.getText());
        System.out.println(telefoon);
        String code = zip.getText();
        System.out.println(code);
        
               try(Connection conn = Database.initDatabase()){
            String Customer = "UPDATE INTO customer (firstname,insertion,lastname,birthDate,city,street,houseNumber,email,date,phoneNumber,idEmployee) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            
            
            PreparedStatement preparedStatement = conn.prepareStatement(Customer);
            preparedStatement.setString(1, "test");
	    preparedStatement.setString(2, "test"); 
            preparedStatement.setString(3, lastname);
            preparedStatement.setDate(4, java.sql.Date.valueOf("1996-02-03"));
            preparedStatement.setString(5, stad);
            preparedStatement.setString(6, straat);
            preparedStatement.setString(7, "test");
            preparedStatement.setString(8, address);
            preparedStatement.setDate(9, java.sql.Date.valueOf("1996-03-03"));
            preparedStatement.setInt(10, telefoon);
            preparedStatement.setInt(11, 1);
            preparedStatement.executeUpdate();
    }   catch (SQLException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
        
    public BorderPane getEditCustomerScreen() {
        BorderPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/views/EditCustomer.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }    
    
}
