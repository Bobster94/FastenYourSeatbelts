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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Jeroen
 * @version 1.0
 */
public class AddLostLuggageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /*
     *
     * @return the addLostLuggage fxml view as borderPane
     */
    public BorderPane getAddLostLuggageScreen() {
        BorderPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/views/AddLostLuggage.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtInsertion;
    @FXML
    private TextField txtBirthdate;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtStreet;
    @FXML
    private TextField txtHouseNumber;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtLostAirport;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtExtra;
    @FXML
    private TextField txtZipcode;
    @FXML
    private TextField txtCountry;
    @FXML
    private ComboBox txtBrand;
    @FXML
    private ComboBox txtSize;
    @FXML
    private ComboBox txtWeight;
    @FXML
    private TextField txtColor;
    @FXML
    private ComboBox txtType;
    @FXML
    private ComboBox cbMaterial;
    @FXML
    private TextField txtFlightnumber;

    /*
     *
     * Save the newly created luggage into the database
     */
    @FXML
    public void savechanges() {

        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String insertion = txtInsertion.getText();
        String birthdate = txtBirthdate.getText();
        String city = txtCity.getText();
        String street = txtStreet.getText();
        String houseNumber = txtHouseNumber.getText();
        String email = txtEmail.getText();
        String lostAirport = txtLostAirport.getText();
        int phoneNumber = Integer.parseInt(txtPhoneNumber.getText());
        String extra = txtExtra.getText();
        String zipcode = txtZipcode.getText();
        String country = txtCountry.getText();
        String brand = txtBrand.getValue().toString();
        String size = txtSize.getValue().toString();
        String weight = txtWeight.getValue().toString();
        String color = txtColor.getText();
        String type = txtType.getValue().toString();
        String material = cbMaterial.getValue().toString();
        String flightnumber = txtFlightnumber.getText();

        String[] contentCustomer = {firstName, insertion, lastName, birthdate, country, city, zipcode, street, houseNumber, email, String.valueOf(phoneNumber)};
        String[] labelsCustomer = {"firstName", "insertion", "lastName", "birthdate", "country", "city", "zipcode", "street", "housenumber", "email", "phoneNumber"};
        String[] contentLuggage = {brand, color, type, weight, size, material, lostAirport};
        String[] labelsLuggage = {"brand", "color", "type", "weight", "size", "material", "lost at airport"};

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //get current date time with Date()
        Date dateToday = new Date();
        String date = dateFormat.format(dateToday);

        try (Connection conn = Database.initDatabase()) {

            String Customer = "INSERT INTO customer (firstname,insertion,lastname,birthDate,country,"
                    + "city,zipCode,street,houseNumber,email,date,phoneNumber,idEmployee) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(Customer, PreparedStatement.RETURN_GENERATED_KEYS);
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
            ResultSet rs = preparedStatement.getGeneratedKeys();

            String SQL = "INSERT INTO luggage "
                    + "(brand,color,type,weight,size,barcode,lostAirport,"
                    + "extra,lostFound,material,date,flightNumber,idEmployee, idCustomer) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            preparedStatement = conn.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, brand);
            preparedStatement.setString(2, color);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, weight);
            preparedStatement.setString(5, size);
            preparedStatement.setNull(6, java.sql.Types.VARCHAR);
            preparedStatement.setString(7, lostAirport);
            preparedStatement.setString(8, extra);
            preparedStatement.setInt(9, 0); //lost luggage
            preparedStatement.setString(10, material);
            preparedStatement.setDate(11, java.sql.Date.valueOf(date));
            preparedStatement.setString(12, flightnumber);
            preparedStatement.setInt(13, Main.employee.getEmployeeID());
            if (rs.next()) {
                preparedStatement.setInt(14, rs.getInt(1));
            }
            preparedStatement.executeUpdate();
            ResultSet rsLuggageID = preparedStatement.getGeneratedKeys();
            if (rsLuggageID.next()) {
                pdfController pdf = new pdfController();
                pdf.createPdf(
                        labelsCustomer,
                        contentCustomer,
                        labelsLuggage,
                        contentLuggage,
                        "Added lost luggage and customer"
                );

                pdf.save("createdLuggage_" + firstName + lastName + "_" + rsLuggageID.getInt(1) + date + ".pdf");
            }

            //Close connection
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AddLostLuggageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
