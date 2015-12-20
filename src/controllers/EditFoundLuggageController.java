package controllers;

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
 * @version 1.0
 */
public class EditFoundLuggageController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    /*
    *
    * @return the EditFoundLuggage fxml view as BorderPane
    */
    public BorderPane getEditFoundLuggageScreen() {
        BorderPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/views/EditFoundLuggage.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }
    
    @FXML private TextField txtExtra;
    @FXML private TextField txtBarcode;
    @FXML private TextField txtLostAtAirport;
    @FXML private TextField txtFoundAtAirport;
    @FXML private TextField txtFlightNumber;
    @FXML private TextField txtDate;
    @FXML private ComboBox  txtMaterial;
    @FXML private ComboBox  txtColor;
    @FXML private ComboBox  txtType;
    @FXML private ComboBox  txtBrand;
    @FXML private ComboBox  txtWeight;
    @FXML private ComboBox  txtSize;
    
    /*
    *
    * Edit the foundLuggage with the given values
    */
    @FXML
    public void AddFoundLuggage() {
        
        String barcode = txtBarcode.getText();
        String foundAirport = txtFoundAtAirport.getText();
        String extra = txtExtra.getText();
        String lostAirport = txtLostAtAirport.getText();
        String flightNumber = txtFlightNumber.getText();
        String date = txtDate.getText();
        String material = txtMaterial.getValue().toString();
        String color = txtColor.getValue().toString();
        String type = txtType.getValue().toString();
        String brand = txtBrand.getValue().toString();
        String weight = txtWeight.getValue().toString();
        String size = txtSize.getValue().toString();
        
        try(Connection conn = Database.initDatabase()) {
            String SQL = "INSERT INTO luggage (brand,color,type,weight,size,barcode,lostAirport,"
                    + "foundAirport,extra,lostFound,material,date,flightNumber,idEmployee) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
           
            PreparedStatement preparedStatement = conn.prepareStatement(SQL);
            preparedStatement.setString(1, brand);
	    preparedStatement.setString(2, color);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, weight);
            preparedStatement.setString(5, size);
            preparedStatement.setString(6, barcode);
            preparedStatement.setString(7, lostAirport);
            preparedStatement.setString(8, foundAirport);
            preparedStatement.setString(9, extra);
            preparedStatement.setInt(10, 1);
            preparedStatement.setString(11, material);
            preparedStatement.setDate(12, java.sql.Date.valueOf(date));
            preparedStatement.setString(13, flightNumber);
            preparedStatement.setInt(14, 1);
            preparedStatement.executeUpdate();
        
            //Close connection
            conn.close();
        }catch(SQLException ex){
              Logger.getLogger(AddFoundLuggageController.class.getName()).log(Level.SEVERE,null,ex);
          }
       
    }
   
    /*
    * Get the luggage from the database and fill all the fields with the resultset
    * @param    String  id  luggage id. Used to search luggage from the database
    */
    public void buildScreen(String id) {
        try {
            Connection conn = Database.initDatabase();
            String selectLuggage = "SELECT brand,color,type,weight,size,extra,material,flightNumber FROM luggage WHERE id = "+id;
            ResultSet rs = conn.createStatement().executeQuery(selectLuggage);
            if(rs.next()) {
                txtExtra.setText(new String(rs.getBytes("extra"), "UTF-8"));
                txtFlightNumber.setText(new String(rs.getBytes("flightNumber"), "UTF-8"));
            }
        } catch (SQLException | UnsupportedEncodingException ex) {
            Logger.getLogger(EditLostLuggageController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
}
