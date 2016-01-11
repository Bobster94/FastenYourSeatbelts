
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Jeroen 
 */
public class AddFoundLuggageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
<<<<<<< HEAD
        // TODO
    }   
    
=======
        
    }

>>>>>>> origin/master
    /*
    * @return the addFoundLuggage fxml view as borderPane
    */
    public BorderPane getAddFoundLuggageScreen() {
        BorderPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/views/AddFoundLuggage.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }
<<<<<<< HEAD
    
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
    
    
=======
    //Getting the textfields id's from view package: add customer.fmxl
    @FXML
    private TextField txtExtra;
    @FXML
    private TextField txtBarcode;
    @FXML
    private TextField txtLostAtAirport;
    @FXML
    private TextField txtFoundAtAirport;
    @FXML
    private TextField txtFlightNumber;
    @FXML
    private TextField txtDate;
    @FXML
    private ComboBox txtMaterial;
    @FXML
    private TextField txtColor;
    @FXML
    private ComboBox txtType;
    @FXML
    private ComboBox txtBrand;
    @FXML
    private ComboBox txtWeight;
    @FXML
    private ComboBox txtSize;
>>>>>>> origin/master
    /*
    *
    * Saves the newly created luggage into the database
    */
    @FXML
    public void AddFoundLuggage () {
        
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
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        //get current date time with Date()
        Date dateToday = new Date();
        date = dateFormat.format(dateToday);
        
<<<<<<< HEAD
        try(Connection conn = Database.initDatabase()){
=======
        //Establishing connection with database and placing the variables in the right order
        try (Connection conn = Database.initDatabase()) {
>>>>>>> origin/master
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
            preparedStatement.setInt(14, Main.employee.getEmployeeID());
            preparedStatement.executeUpdate();
<<<<<<< HEAD
        
=======
            ResultSet sr = preparedStatement.getGeneratedKeys();
            
            //update history table add customer       
            String history = "INSERT INTO history"+
                    "(status,idLuggage,dateHandled,idEmployeeHandled)"+
                    "VALUES(?,?,?,?)";
            preparedStatement = conn.prepareStatement(history);

            preparedStatement.setString(1,"addLuggage");
            if(sr.next()) {
                preparedStatement.setInt(2, sr.getInt(1));
            }
            preparedStatement.setDate(3, java.sql.Date.valueOf(date));
            
            System.out.println("data in database gelukt");
            
            preparedStatement.setInt(4, Main.employee.getEmployeeID());

            preparedStatement.executeUpdate();
>>>>>>> origin/master
            //Close connection
            conn.close();
        }catch(SQLException ex){
              Logger.getLogger(AddFoundLuggageController.class.getName()).log(Level.SEVERE,null,ex);
          }
       
    }
}
