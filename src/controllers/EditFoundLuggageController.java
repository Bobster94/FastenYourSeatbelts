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
    private static int id;
    @FXML private TextField txtExtra;
    @FXML private TextField txtBarcode;
    @FXML private TextField txtLostAtAirport;
    @FXML private TextField txtFoundAtAirport;
    @FXML private TextField txtFlightNumber;
    @FXML private TextField txtDate;
    @FXML private ComboBox  cbMaterial;
    @FXML private ComboBox  cbColor;
    @FXML private ComboBox  cbType;
    @FXML private ComboBox  cbBrand;
    @FXML private ComboBox  cbWeight;
    @FXML private ComboBox  cbSize;
    
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
        String material = cbMaterial.getValue().toString();
        String color = cbColor.getValue().toString();
        String type = cbType.getValue().toString();
        String brand = cbBrand.getValue().toString();
        String weight = cbWeight.getValue().toString();
        String size = cbSize.getValue().toString();
        
        try(Connection conn = Database.initDatabase()) {
            String SQL = "UPDATE luggage "
                    + "SET brand = ?, color = ?, type = ?, weight = ?,"
                    + " size = ?, barcode = ?, lostAirport = ?, foundAirport = ?,"
                    + " extra = ?, lostFound = ?, material = ?, date = ?,"
                    + " flightNumber = ?, idEmployee = ? "
                    + "WHERE id = ?";
           
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
            preparedStatement.setInt(15, id);
            preparedStatement.executeUpdate();
        
            //Close connection
            conn.close();
            
            //Redirect to detail page of this luggage
            AllLuggageController AllLuggage = new AllLuggageController();
            AllLuggage.buildscreen(String.valueOf(id), "foundLuggage");
            
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
            this.id = Integer.parseInt(id);
            Connection conn = Database.initDatabase();
            String selectLuggage = "SELECT date,foundAirport,lostAirport,"
                    + "barcode,brand,color,type,weight,"
                    + "size,extra,material,flightNumber "
                    + "FROM luggage "
                    + "WHERE id = "+id;
            ResultSet rs = conn.createStatement().executeQuery(selectLuggage);
            if(rs.next()) {
                txtExtra.setText(new String(rs.getBytes("extra"), "UTF-8"));
                txtFlightNumber.setText(new String(rs.getBytes("flightNumber"), "UTF-8"));
                cbBrand.setValue(new String(rs.getBytes("brand"), "UTF-8"));
                cbColor.setValue(new String(rs.getBytes("color"), "UTF-8"));
                cbType.setValue(new String(rs.getBytes("type"), "UTF-8"));
                cbWeight.setValue(new String(rs.getBytes("weight"), "UTF-8"));
                cbSize.setValue(new String(rs.getBytes("size"), "UTF-8"));
                cbMaterial.setValue(new String(rs.getBytes("material"), "UTF-8"));
                txtBarcode.setText(new String(rs.getBytes("barcode"), "UTF-8"));
                txtDate.setText(new String(rs.getBytes("date"), "UTF-8"));
                txtFoundAtAirport.setText(new String(rs.getBytes("foundAirport"), "UTF-8"));
                txtLostAtAirport.setText(new String(rs.getBytes("lostAirport"), "UTF-8"));
            }
        } catch (SQLException | UnsupportedEncodingException ex) {
            Logger.getLogger(EditLostLuggageController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
}
