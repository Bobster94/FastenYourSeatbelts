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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Bas
 */
public class AddCustomerController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    @FXML private Button btnAddCustomer;
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
        String telefoon = phone.getText();
        System.out.println(telefoon);
        String code = zip.getText();
        System.out.println(code);
        
        try(Connection conn = Database.initDatabase()) {
            String SQL = "SELECT firstName,lastName,birthDate,city,street,houseNumber,email,phoneNumber "
                + "FROM customer "
                + "WHERE lastName LIKE '%"+lastname+"%' "
                + "AND birthDate LIKE '%"+birthdate+"%'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            
            //Add data to the tableview
            while(rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            tvCustomers.setItems(data);
        } catch(Exception e){
            System.out.println("Error on filling the tableview");             
        } 
    }
        
        
    }
    
    public BorderPane getAddCustomerScreen() {
        BorderPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/views/AddCustomer.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }
    
}   
