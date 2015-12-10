/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.EditCustomerController;
import controllers.Database;
import controllers.LoginController;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        
        };
    //Customer id
    private static int id;
    
    @FXML private TextField txtFirstname;
    @FXML private TextField txtInsertion;
    @FXML private TextField txtLastname;
    @FXML private TextField txtCountry;
    @FXML private TextField txtCity;
    @FXML private TextField txtEmail;
    @FXML private TextField txtStreetname;
    @FXML private TextField txtHousenumber;
    @FXML private TextField txtPhonenumber;
    @FXML private TextField txtZipcode;
    @FXML private TextField txtBirthdate;
    
    @FXML
    private void editCustomer(ActionEvent event) {
        String firstname = txtFirstname.getText();
        String birthDate = txtBirthdate.getText();
        String insertion = txtInsertion.getText();
        String country = txtCountry.getText();
        String lastname = txtLastname.getText();
        String city = txtCity.getText();
        String address = txtEmail.getText();
        String street = txtStreetname.getText();
        String houseNumber = txtHousenumber.getText();
        int telephoneNumber = Integer.parseInt(txtPhonenumber.getText());
        String zipcode = txtZipcode.getText();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //get current date time with Date()
        Date date = new Date();
        String dateToday = dateFormat.format(date);
        
        try(Connection conn = Database.initDatabase()) {
            String Customer = "UPDATE customer "
                    + "SET firstName = ?,"
                    + " insertion = ?,"
                    + " lastName = ?,"
                    + " birthDate = ?,"
                    + " country = ?,"
                    + " city = ?,"
                    + " zipCode = ?,"
                    + " street = ?,"
                    + " houseNumber = ?,"
                    + " email = ?,"
                    + " date = ?,"
                    + " phoneNumber = ?,"
                    + " idEmployee = ? "
                    + "WHERE id = ?";
            
            PreparedStatement preparedStatement = conn.prepareStatement(Customer);
            preparedStatement.setString(1, firstname);
	    preparedStatement.setString(2, insertion); 
            preparedStatement.setString(3, lastname);
            preparedStatement.setDate(4, java.sql.Date.valueOf(birthDate));
            preparedStatement.setString(5, country);
            preparedStatement.setString(6, city);
            preparedStatement.setString(7, zipcode);
            preparedStatement.setString(8, street);
            preparedStatement.setString(9, houseNumber);
            preparedStatement.setString(10, address);
            preparedStatement.setDate(11, java.sql.Date.valueOf(dateToday));
            preparedStatement.setInt(12, telephoneNumber);
            preparedStatement.setInt(13, 2);
            preparedStatement.setInt(14, id);
            preparedStatement.executeUpdate();
            
        }   catch (SQLException ex) {
                Logger.getLogger(EditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void buildScreen(String id) {
        try {
            this.id = Integer.parseInt(id);
            Connection conn = Database.initDatabase();
            String selectCustomer = "SELECT * FROM customer WHERE id = "+id;
            ResultSet rs = conn.createStatement().executeQuery(selectCustomer);
            if(rs.next()) {
                txtFirstname.setText(new String(rs.getBytes("firstName"), "UTF-8"));
                txtInsertion.setText(new String(rs.getBytes("insertion"), "UTF-8"));
                txtLastname.setText(new String(rs.getBytes("lastName"), "UTF-8"));
                txtBirthdate.setText(new String(rs.getBytes("birthDate"), "UTF-8"));
                txtCountry.setText(new String(rs.getBytes("country"), "UTF-8"));
                txtCity.setText(new String(rs.getBytes("city"), "UTF-8"));
                txtZipcode.setText(new String(rs.getBytes("zipCode"), "UTF-8"));
                txtStreetname.setText(new String(rs.getBytes("street"),"UTF-8"));
                txtHousenumber.setText(new String(rs.getBytes("houseNumber"),"UTF-8"));
                txtEmail.setText(new String(rs.getBytes("email"),"UTF-8"));
                txtPhonenumber.setText(new String(rs.getBytes("phoneNumber"),"UTF-8"));
            }
        } catch (SQLException | UnsupportedEncodingException ex) {
            Logger.getLogger(EditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
