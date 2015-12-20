package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 * This class is used to set all the content and methods for the Dashboard fxml view
 * 
 * @author Bas
 * @version 1.0
 */
public class DashboardController implements Initializable {

    @FXML private TableView tvLostLuggage;
    @FXML private TableView tvFoundLuggage;
    @FXML private TableView tvCustomers;

    /**
     * Initializes the controller class.
     * This method will run when the dashboard fxml is called
     * The method will initialize all the base content for all the tableviews
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AllLuggageController allLuggage = new AllLuggageController();
        ObservableList<Luggage> LostData = FXCollections.observableArrayList();
        try (Connection conn = Database.initDatabase()) {
            String SQL = "SELECT id,barcode,date,flightNumber as 'flight number' "
                    + "FROM luggage WHERE lostFound = 0 "
                    + "AND NOT EXISTS (SELECT idLuggage "
                    + "FROM history "
                    + "WHERE  luggage.id = history.idLuggage)";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
                TableColumn col = new TableColumn(rs.getMetaData().getColumnLabel(i + 1));
                col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i + 1)));
                tvLostLuggage.getColumns().add(col);
            }

            //Add data to the tableview
            while (rs.next()) {
                //Iterate Row
                String[] params = new String[rs.getMetaData().getColumnCount() + 1];
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    params[i - 1] = rs.getString(i);
                }
                params[4] = null;
                LostData.add(new Luggage(params));
            }
            tvLostLuggage.setItems(LostData);

        } catch (Exception e) {
            System.out.println(e);
        }

        tvLostLuggage.setRowFactory(tv -> {
            TableRow<Luggage> row = new TableRow<>();
            BorderPane root = Main.getRoot();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    allLuggage.buildscreen(row.getItem().getID(), "lostLuggage");
                }
            });
            return row;
        });

        ObservableList<Luggage> foundData = FXCollections.observableArrayList();
        try (Connection conn = Database.initDatabase()) {
            String SQL = "SELECT id,barcode,date,"
                    + "flightNumber as 'flight number',"
                    + "lostAirport as 'lost at airport' "
                    + "FROM luggage "
                    + "WHERE lostFound = 1 "
                    + "AND NOT EXISTS (SELECT idLuggage "
                    + "FROM history "
                    + "WHERE  luggage.id = history.idLuggage)";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
                TableColumn col = new TableColumn(rs.getMetaData().getColumnLabel(i + 1));
                col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i + 1)));
                tvFoundLuggage.getColumns().add(col);
            }

            //Add data to the tableview
            while (rs.next()) {
                //Iterate Row
                String[] params = new String[rs.getMetaData().getColumnCount()];
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    params[i - 1] = rs.getString(i);
                }
                foundData.add(new Luggage(params));
            }
            tvFoundLuggage.setItems(foundData);

        } catch (Exception e) {
            System.out.println(e);
        }

        tvFoundLuggage.setRowFactory(tv -> {
            TableRow<Luggage> row = new TableRow<>();
            BorderPane root = Main.getRoot();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    allLuggage.buildscreen(row.getItem().getID(), "foundLuggage");
                }
            });
            return row;
        });

        ObservableList<Customer> customerData = FXCollections.observableArrayList();
        try (Connection conn = Database.initDatabase()) {
            String SQL = "SELECT id,firstName as 'firstname',"
                    + "lastName as 'lastname',"
                    + "birthDate as 'date of birth',"
                    + "city,street,houseNumber as 'house number',email,"
                    + "phoneNumber as 'phone number' FROM customer";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
                TableColumn col = new TableColumn(rs.getMetaData().getColumnLabel(i + 1));
                col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i + 1)));
                tvCustomers.getColumns().addAll(col);
            }
            //Add data to the tableview
            while (rs.next()) {
                //Iterate Row
                String[] params = new String[rs.getMetaData().getColumnCount()];
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                  //  Iterate Column
                    params[i - 1] = rs.getString(i);
                }
                customerData.add(new Customer(params));
            }
            tvCustomers.setItems(customerData);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        SpecificCustomer specCustomer = new SpecificCustomer();
        tvCustomers.setRowFactory(tv -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    specCustomer.buildscreen(row.getItem().getID());
                }
            });
            return row;
        });
    }

    /*
    *
    * Calls the base root from main and sets the content
    */
    @FXML
    protected void GetAddFoundLuggage(){
        try {
            BorderPane root = Main.getRoot();
            URL paneOneUrl = getClass().getResource("/views/AddFoundLuggage.fxml");
            root.setLeft((Node) FXMLLoader.load(paneOneUrl));
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    *
    * Calls the base root from main and sets the content
    */    
    @FXML
    protected void GetAddLostLuggage() {
        try {
            BorderPane root = Main.getRoot();
            URL paneOneUrl = getClass().getResource("/views/AddLostLuggage.fxml");
            root.setLeft((Node) FXMLLoader.load(paneOneUrl));
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    *
    * Calls the base root from main and sets the content
    */    
    @FXML
    protected void GetAddCustomer() {
        try {
            BorderPane root = Main.getRoot();
            URL paneOneUrl = getClass().getResource("/views/AddCustomer.fxml");
            root.setLeft((Node) FXMLLoader.load(paneOneUrl));
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML private TextField txtSearchLostLuggage;
    
    /*
    *
    * Search lostLuggage with the given value
    * Renew the TableView with the results
    */
    @FXML
    protected void searchLostLuggage() {
        String searchField = txtSearchLostLuggage.getText();
        ObservableList<Luggage> data;
        data = FXCollections.observableArrayList();

        try (Connection conn = Database.initDatabase()) {
            String SQL = "SELECT id,barcode,date,flightNumber "
                    + "FROM luggage "
                    + "WHERE lostFound = 0 "
                    + "AND flightNumber LIKE '%" + searchField + "%' "
                    + "AND NOT EXISTS (SELECT idLuggage "
                    + "FROM history "
                    + "WHERE  luggage.id = history.idLuggage)";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            //Add data to the tableview
            while (rs.next()) {
                //Iterate Row
                String[] params = new String[rs.getMetaData().getColumnCount() + 1];
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    params[i - 1] = rs.getString(i);
                }
                params[4] = null;
                data.add(new Luggage(params));
            }
            tvLostLuggage.setItems(data);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    @FXML private TextField txtSearchFoundLuggage;

    /*
    *
    * Search foundLuggage with the given value
    * Renew the TableView with the results
    */    
    @FXML
    protected void searchFoundLuggage() {
        String searchField = txtSearchFoundLuggage.getText();
        ObservableList<Luggage> data;
        data = FXCollections.observableArrayList();

        try (Connection conn = Database.initDatabase()) {
            String SQL = "SELECT id,barcode,date,flightNumber,lostAirport "
                    + "FROM luggage "
                    + "WHERE lostFound = 1 "
                    + "AND flightNumber LIKE '%" + searchField + "%' "
                    + "AND NOT EXISTS (SELECT idLuggage "
                    + "FROM history "
                    + "WHERE  luggage.id = history.idLuggage)";
            ResultSet rs = conn.createStatement().executeQuery(SQL);

            //Add data to the tableview
            while (rs.next()) {
                //Iterate Row
                String[] params = new String[rs.getMetaData().getColumnCount()];
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    params[i - 1] = rs.getString(i);
                }
                data.add(new Luggage(params));
            }
            tvFoundLuggage.setItems(data);
        } catch (Exception e) {
            System.out.println("Error on filling the tableview");
        }
    }

    @FXML private TextField txtSearchCustomerBirthdate;
    @FXML private TextField txtSearchCustomerLastname;

    /*
    *
    * Search customers with the given value
    * Renew the TableView with the results
    */
    @FXML
    protected void searchCustomer() {
        String lastname = txtSearchCustomerLastname.getText();
        String birthdate = txtSearchCustomerBirthdate.getText();
        ObservableList<Customer> data;
        data = FXCollections.observableArrayList();

        try (Connection conn = Database.initDatabase()) {
            String SQL = "SELECT id,firstName,lastName,birthDate,"
                    + "city,street,houseNumber,email,phoneNumber "
                    + "FROM customer "
                    + "WHERE lastName LIKE '%" + lastname + "%'"
                    + "AND birthDate LIKE '%" + birthdate + "%'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);

            //Add data to the tableview
            while (rs.next()) {
                //Iterate Row
                String[] params = new String[rs.getMetaData().getColumnCount()];
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    params[i-1] = rs.getString(i);
                }
                data.add(new Customer(params));
            }
            tvCustomers.setItems(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*
    *
    * @return the dashboard fxml view as BorderPane
    */
    public BorderPane getDashboardScreen() {
        BorderPane screen = null;
        try {
            screen = FXMLLoader.load(
                    getClass().getResource("/views/Dashboard.fxml")
            );
        } catch (IOException ex) {
            Logger.getLogger(
                LoginController.class.getName()).log(Level.SEVERE, null, ex
            );
        }
        return screen;
    }
}
