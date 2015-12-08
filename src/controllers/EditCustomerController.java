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
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    @FXML private Button btnAddCustomer;
    @FXML private TextField txtFirstname;
//    @FXML private TextField country;
//    @FXML private TextField last;
//    @FXML private TextField city;
//    @FXML private TextField email;
//    @FXML private TextField street;
//    @FXML private TextField phone;
//    @FXML private TextField zip;
    
public void buildScreen(String id) {
        try {
            Connection conn = Database.initDatabase();
            String selectLuggage = "SELECT * FROM customer WHERE id = "+id;
            ResultSet rs = conn.createStatement().executeQuery(selectLuggage);
            if(rs.next()) {
                txtFirstname.setText(new String(rs.getBytes("firstName"), "UTF-8"));
//                txtFlightnumber.setText(new String(rs.getBytes("flightNummer"), "UTF-8"));
//                cbBrand.setValue(new String(rs.getBytes("brand"), "UTF-8"));
//                cbColor.setValue(new String(rs.getBytes("color"), "UTF-8"));
//                cbSize.setValue(new String(rs.getBytes("size"), "UTF-8"));
//                cbType.setValue(new String(rs.getBytes("type"), "UTF-8"));
//                cbWeight.setValue(new String(rs.getBytes("weight"), "UTF-8"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditLostLuggageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EditLostLuggageController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
   
    
//    @FXML
//    private void saveCustomer(ActionEvent event) {
//        String name = first.getText();
//        String counrty = country.getText();
//        String lastname = last.getText();
//        String stad = city.getText();
//        String address = email.getText();
//        String straat = street.getText();
//        int telefoon = Integer.parseInt(phone.getText());
//        String code = zip.getText();
//        
//        try(Connection conn = Database.initDatabase()){
//            String Customer = "UPDATE INTO customer "
//                    + "(firstname,insertion,lastname,birthDate,city,street,"
//                    + "houseNumber,email,date,phoneNumber,idEmployee) "
//                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
//            
//            
//            PreparedStatement preparedStatement = conn.prepareStatement(Customer);
//            preparedStatement.setString(1, "test");
//	    preparedStatement.setString(2, "test"); 
//            preparedStatement.setString(3, lastname);
//            preparedStatement.setDate(4, java.sql.Date.valueOf("1996-02-03"));
//            preparedStatement.setString(5, stad);
//            preparedStatement.setString(6, straat);
//            preparedStatement.setString(7, "test");
//            preparedStatement.setString(8, address);
//            preparedStatement.setDate(9, java.sql.Date.valueOf("1996-03-03"));
//            preparedStatement.setInt(10, telefoon);
//            preparedStatement.setInt(11, 1);
//            preparedStatement.executeUpdate();
//    }   catch (SQLException ex) {
//            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }
        
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
