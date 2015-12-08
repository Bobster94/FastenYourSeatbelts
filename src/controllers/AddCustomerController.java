
package controllers;

import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    @FXML private TextField txtFirstName;
    @FXML private TextField txtCountry;
    @FXML private TextField txtLastName;
    @FXML private TextField txtCity;
    @FXML private TextField txtEmail;
    @FXML private TextField txtStreet;
    @FXML private TextField txtPhone;
    @FXML private TextField txtZipcode;
    @FXML private TextField txtInsertion;
    @FXML private TextField txtHouseNumber;
    @FXML private TextField txtBirthdate;
    @FXML private TextField txtDate;
   
    
    @FXML
    private void saveCustomer(ActionEvent event) {
        String firstName = txtFirstName.getText();       
        String country = txtCountry.getText();      
        String lastName = txtLastName.getText();
        String city = txtCity.getText();
        String email= txtEmail.getText();
        String street = txtStreet.getText();
        int phoneNumber = Integer.parseInt(txtPhone.getText());
        String zipcode = txtZipcode.getText();
        String insertion = txtInsertion.getText();
        String houseNumber = txtHouseNumber.getText();
        String birthdate = txtBirthdate.getText();
        String date = txtDate.getText();

        try(Connection conn = (Connection) Database.initDatabase()){
            String Customer = "INSERT INTO customer (firstname,insertion,lastname,birthDate,country,"
                + "city,zipCode,street,houseNumber,email,date,phoneNumber,idEmployee) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";  
            
            
            PreparedStatement preparedStatement = conn.prepareStatement(Customer);
            preparedStatement.setString(1, firstName);
	    preparedStatement.setString(2, insertion); 
            preparedStatement.setString(3, lastName);
            preparedStatement.setDate(4, java.sql.Date.valueOf(birthdate));
            preparedStatement.setString(5, country);
            preparedStatement.setString(6, city);
            preparedStatement.setString(7, zipcode);
            preparedStatement.setString(8, street);
            preparedStatement.setString(9, houseNumber);
            preparedStatement.setString(10, email);
            preparedStatement.setDate(11, java.sql.Date.valueOf(date));
            preparedStatement.setInt(12, phoneNumber);
            preparedStatement.setInt(13, 1);
            preparedStatement.executeUpdate();
    }   catch (SQLException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
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
