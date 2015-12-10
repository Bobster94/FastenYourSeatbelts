/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.event.ActionEvent;
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
 *
 * @author Bas
 */
public class DashboardController implements Initializable {
    @FXML private TableView tvLostLuggage;
    @FXML private TableView tvFoundLuggage;
    @FXML private TableView tvCustomers;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AllLuggageController allLuggage = new AllLuggageController();
        ObservableList<Luggage> LostData = FXCollections.observableArrayList();
        try (Connection conn = Database.initDatabase()) {
            String SQL = "SELECT id,barcode,date,flightNumber FROM luggage WHERE lostFound = 0";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
                tvLostLuggage.getColumns().add(col);
            }

            //Add data to the tableview
            while (rs.next()) {
                //Iterate Row
                String[] params = new String[rs.getMetaData().getColumnCount() + 1];
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    params[i-1] = rs.getString(i);
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
                    allLuggage.buildscreen(row.getItem().getID());
                }
            });
            return row;
        });
        
        ObservableList<Luggage> foundData = FXCollections.observableArrayList();
        try (Connection conn = Database.initDatabase()) {
            String SQL = "SELECT id,barcode,date,flightNumber,lostAirport FROM luggage WHERE lostFound = 1";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
                tvFoundLuggage.getColumns().add(col);
            }

            //Add data to the tableview
            while (rs.next()) {
                //Iterate Row
                String[] params = new String[rs.getMetaData().getColumnCount()];
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    params[i-1] = rs.getString(i);
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
                    allLuggage.buildscreen(row.getItem().getID());
                }
            });
            return row;
        });
        
        ObservableList<Customer> customerData = FXCollections.observableArrayList();
          try(Connection conn = Database.initDatabase()){
            String SQL = "SELECT id,firstName,lastName,birthDate,city,street,houseNumber,email,phoneNumber FROM customer";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            for(int i=1 ; i<rs.getMetaData().getColumnCount(); i++){
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i+1)));
                tvCustomers.getColumns().addAll(col); 
            }
            //Add data to the tableview
            while(rs.next()){
                //Iterate Row
                String[] params = new String[rs.getMetaData().getColumnCount()];
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    params[i-1] = rs.getString(i);
                }
                customerData.add(new Customer(params));
            }
            tvCustomers.setItems(customerData);
            }catch(Exception e){
                System.out.println("Error on filling the tableview");             
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
    
    @FXML
    protected void GetAddFoundLuggage(ActionEvent event) throws IOException{
        BorderPane root = Main.getRoot();
        URL paneOneUrl = getClass().getResource("/views/AddFoundLuggage.fxml");
        root.setLeft((Node) FXMLLoader.load(paneOneUrl));
    }
    
    @FXML
    protected void GetAddCustomer(ActionEvent event) {
        //TODO
    }
    
    @FXML private TextField txtSearchLostLuggage;
    @FXML
    protected void searchLostLuggage(ActionEvent event) {
        String searchField = txtSearchLostLuggage.getText();
        ObservableList<Luggage> data;
        data = FXCollections.observableArrayList();
        
        try(Connection conn = Database.initDatabase()){
            String SQL = "SELECT id,barcode,date,flightNummer "
                + "FROM luggage "
                + "WHERE lostFound = 0 "
                + "AND flightNumber LIKE '%"+searchField+"%'";
        ResultSet rs = conn.createStatement().executeQuery(SQL);
            //Add data to the tableview
            while(rs.next()){
                //Iterate Row
                String[] params = new String[rs.getMetaData().getColumnCount()];
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    params[i-1] = rs.getString(i);
                }
                data.add(new Luggage(params));
            }
                tvLostLuggage.setItems(data);
            }catch(Exception e){
                System.out.println("Error on filling the tableview");             
            }  
        
    }
    @FXML private TextField txtSearchFoundLuggage;
    @FXML
    protected void searchFoundLuggage(ActionEvent event) {
        String searchField = txtSearchFoundLuggage.getText();
        ObservableList<Luggage> data;
        data = FXCollections.observableArrayList();
        
        try(Connection conn = Database.initDatabase()) {
            String SQL = "SELECT id,barcode,date,flightNumber,lostAirport "
                + "FROM luggage "
                + "WHERE lostFound = 1 "
                + "AND flightNummer LIKE '%"+searchField+"%'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            
            //Add data to the tableview
            while(rs.next()) {
                //Iterate Row
                String[] params = new String[rs.getMetaData().getColumnCount()];
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    params[i-1] = rs.getString(i);
                }
                params[4] = null;
                data.add(new Luggage(params));
            }
            tvFoundLuggage.setItems(data);
        } catch(Exception e){
            System.out.println("Error on filling the tableview");             
        }    
    }
    
    
    @FXML private TextField txtSearchCustomerBirthdate;
    @FXML private TextField txtSearchCustomerLastname;
    @FXML
    protected void searchCustomer(ActionEvent event) {
        String lastname = txtSearchCustomerLastname.getText();
        String birthdate = txtSearchCustomerBirthdate.getText();
        ObservableList<ObservableList> data;
        data = FXCollections.observableArrayList();
        
        try(Connection conn = Database.initDatabase()) {
            String SQL = "SELECT firstName,lastName,birthDate,city,street,houseNumber,email,phoneNumber "
                + "FROM customer "
                + "WHERE lastName LIKE '%"+lastname+"%'"
                + "AND birthDate LIKE '%"+birthdate+"%'";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            
            //Add data to the tableview
            while(rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            tvCustomers.setItems(data);
        } catch(Exception e){
            System.out.println("Error on filling the tableview");             
        } 
    }
    
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
