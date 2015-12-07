
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Jeroen
 */
public class AddLostLuggageController implements Initializable {
 

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
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtInsertion;
    @FXML private TextField txtBirthdate;
    @FXML private TextField txtCity;
    @FXML private TextField txtStreet;
    @FXML private TextField txtHouseNumber;
    @FXML private TextField txtEmail;
    @FXML private TextField txtDate;
    @FXML private TextField txtLostAirport;
    @FXML private TextField txtPhoneNumber;
    @FXML private TextField txtExtra;
    @FXML private TextField txtZipcode;
    @FXML private TextField txtCountry;
    @FXML private ComboBox  txtBrand;
    @FXML private ComboBox  txtSize;
    @FXML private ComboBox  txtWeight;
    @FXML private ComboBox  txtColor;
    @FXML private ComboBox  txtType;  
    @FXML
    
    public void savechanges(ActionEvent event) {
    
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String insertion = txtInsertion.getText();
        String birthdate = txtBirthdate.getText();
        String city = txtCity.getText();
        String street = txtStreet.getText();
        String houseNumber = txtHouseNumber.getText();
        String email = txtEmail.getText();
        String date = txtDate.getText();
        String lostAirport = txtLostAirport.getText();
        int phoneNumber = Integer.parseInt(txtPhoneNumber.getText());
        String extra = txtExtra.getText();
        String zipcode = txtZipcode.getText();
        String country = txtCountry.getText();
        String brand = txtBrand.getValue().toString();
        String size = txtSize.getValue().toString();
        String weight = txtWeight.getValue().toString();
        String color = txtColor.getValue().toString();
        String type = txtType.getValue().toString();
        
        try(Connection conn = Database.initDatabase()){
            String SQL = "INSERT INTO luggage (brand,color,type,weight,size,barcode,lostAirport,"
                    + "extra,lostFound,material,date,flightNumber,idEmployee) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            
            PreparedStatement preparedStatement = conn.prepareStatement(SQL);
            preparedStatement.setString(1, brand);
	    preparedStatement.setString(2, color);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, weight);
            preparedStatement.setString(5, size);
            preparedStatement.setString(6, "barcode");
            preparedStatement.setString(7, lostAirport);
            preparedStatement.setString(8, extra);
            preparedStatement.setInt(9, 0);
            preparedStatement.setString(10,"material");
            preparedStatement.setDate(11, java.sql.Date.valueOf(date));
            preparedStatement.setString(12,"flightnumber");
            preparedStatement.setInt(13, 1);
            preparedStatement.executeUpdate();
        
        String Customer = "INSERT INTO customer (firstname,insertion,lastname,birthDate,country,"
                + "city,zipCode,street,houseNumber,email,date,phoneNumber,idEmployee) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";  
        
            preparedStatement = conn.prepareStatement(Customer);
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
            
            //Close connection
            conn.close();
        }catch(SQLException ex){
              Logger.getLogger(AddLostLuggageController.class.getName()).log(Level.SEVERE,null,ex);             
          }
        
    }   
    }

