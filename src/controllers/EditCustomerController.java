package controllers;

import java.io.IOException;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Mark
 * @version 1.0
 */
public class EditCustomerController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
    
    /*
    * Used to edit the customer with the given values
    *
    */
    @FXML
    private void editCustomer() {
        //Get values from the screen
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
        
        //Get the current date
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
            //Set the values in the query
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
            preparedStatement.setInt(13, Main.employee.getEmployeeID());
            preparedStatement.setInt(14, id);
            preparedStatement.executeUpdate();
            
            //Create the sql query
            String history = "INSERT INTO history"+
                    "(status,idCustomer,dateHandled,idEmployeeHandled)"+
                    "VALUES(?,?,?,?)";
            preparedStatement = conn.prepareStatement(history);
            
            //Set the values in the query
            preparedStatement.setString(1,"EditCustomer");
            preparedStatement.setInt(2,id);
            preparedStatement.setDate(3, java.sql.Date.valueOf(dateToday));
            preparedStatement.setInt(4, Main.employee.getEmployeeID());
            preparedStatement.executeUpdate();
            
            //Redirect to specific customer
            SpecificCustomer specCustomer = new SpecificCustomer();
            specCustomer.buildscreen(String.valueOf(id));
            
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
    
    /*
    * Get the customer from the database and fill all the fields with the resultset
    * @param    String  id  The from the customer.
    */
    public void buildScreen(String id) {
        try {
            this.id = Integer.parseInt(id);
            Connection conn = Database.initDatabase();
            String selectCustomer = "SELECT * FROM customer WHERE id = "+id;
            ResultSet rs = conn.createStatement().executeQuery(selectCustomer);
            if(rs.next()) {
                //Get the value. If the value is null set an empty string
                txtFirstname.setText(rs.getString("firstName"));
                if(rs.wasNull()) txtFirstname.setText("");
                txtInsertion.setText(rs.getString("insertion"));
                if(rs.wasNull()) txtInsertion.setText("");
                txtLastname.setText(rs.getString("lastName"));
                if(rs.wasNull()) txtLastname.setText("");
                txtBirthdate.setText(rs.getString("birthDate"));
                if(rs.wasNull()) txtBirthdate.setText("");
                txtCountry.setText(rs.getString("country"));
                if(rs.wasNull()) txtCountry.setText("");
                txtCity.setText(rs.getString("city"));
                if(rs.wasNull()) txtCity.setText("");
                txtZipcode.setText(rs.getString("zipCode"));
                if(rs.wasNull()) txtZipcode.setText("");
                txtStreetname.setText(rs.getString("street"));
                if(rs.wasNull()) txtStreetname.setText("");
                txtHousenumber.setText(rs.getString("houseNumber"));
                if(rs.wasNull()) txtHousenumber.setText("");
                txtEmail.setText(rs.getString("email"));
                if(rs.wasNull()) txtEmail.setText("");
                txtPhonenumber.setText(rs.getString("phoneNumber"));
                if(rs.wasNull())txtPhonenumber.setText("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditCustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
