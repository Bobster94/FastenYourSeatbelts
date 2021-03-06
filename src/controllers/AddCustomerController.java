
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Bas
 * @version 1.0
 */
public class AddCustomerController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    //Getting the textfields id's from view package: add customer.fmxl
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
   
    /*
    * Saves the newly created customer into the database
    */
    @FXML
    private void saveCustomer() {
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
<<<<<<< HEAD
        String date = txtDate.getText();
=======
>>>>>>> origin/master
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        //get current date time with Date()
        Date dateToday = new Date();
        date = dateFormat.format(dateToday);

        //Establishing connection with database and placing the variables in the right order
        try(Connection conn = (Connection) Database.initDatabase()){
            String Customer = "INSERT INTO customer (firstname,insertion,lastname,birthDate,country,"
                + "city,zipCode,street,houseNumber,email,date,phoneNumber,idEmployee) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";  
            
<<<<<<< HEAD
            
            PreparedStatement preparedStatement = conn.prepareStatement(Customer);
=======
            PreparedStatement preparedStatement = conn.prepareStatement(Customer, PreparedStatement.RETURN_GENERATED_KEYS);
>>>>>>> origin/master
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
            preparedStatement.setInt(13, Main.employee.getEmployeeID());
            preparedStatement.executeUpdate();
<<<<<<< HEAD
    }   catch (SQLException ex) {
            Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
=======
            
            ResultSet sr = preparedStatement.getGeneratedKeys();
            //update history table add customer            
            String history = "INSERT INTO history"+
                    "(status,idCustomer,dateHandled,idEmployeeHandled)"+
                    "VALUES(?,?,?,?)";
            preparedStatement = conn.prepareStatement(history);

            preparedStatement.setString(1,"addCustomer");
            if(sr.next()) {
                preparedStatement.setInt(2, sr.getInt(1));
            }
            preparedStatement.setDate(3, java.sql.Date.valueOf(date));
            
            System.out.println("data in database gelukt");
            
            preparedStatement.setInt(4, Main.employee.getEmployeeID());

            preparedStatement.executeUpdate();
    }          catch (SQLException ex) {
     Logger.getLogger(AddCustomerController.class.getName()).log(Level.SEVERE, null, ex);
>>>>>>> origin/master
        }
    }
    
    /*
    *
    * @return the addCustomer view as borderPane
    */
    
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
