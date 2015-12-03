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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

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
        AllLuggageController allL = new AllLuggageController();
        tvLostLuggage = allL.buildData(tvLostLuggage,0, "barcode,date,flightNummer");
        tvFoundLuggage = allL.buildData(tvFoundLuggage,1, "barcode,lostAirport,date,flightNummer");
        tvCustomers = buildCustomersTable(tvCustomers);
    }
    private ObservableList<ObservableList> data;
    public TableView buildCustomersTable(TableView tableview) {
       data = FXCollections.observableArrayList();
          try(Connection conn = Database.initDatabase()){
            String SQL = "SELECT firstName,lastName,birthDate,city,street,houseNumber,email,phoneNumber FROM customer";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;                
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                //Connect cell to the respective column
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){                    
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {                                                                                              
                        return new SimpleStringProperty(param.getValue().get(j).toString());                        
                    }                    
                });
                tableview.getColumns().addAll(col); 
            }
            //Add data to the tableview
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
                tableview.setItems(data);
            }catch(Exception e){
                System.out.println("Error on filling the tableview");             
            }
            return tableview;
      }
    
    
    @FXML
    protected void GetAddFoundLuggage(ActionEvent event) throws IOException{
        BorderPane root = Main.getRoot();
        //try {
            
           
//        } catch (IOException ex) {
//            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        URL paneOneUrl = getClass().getResource("/views/AddFoundLuggage.fxml");
        root.setLeft(FXMLLoader.load(paneOneUrl));
        //AddFoundLuggageController add = new AddFoundLuggageController();
        //root.setLeft(add.getAddFoundLuggageScreen());
    }
    
    @FXML
    protected void GetAddCustomer(ActionEvent event) {
        //TODO
    }
    
    @FXML private TextField txtSearchLostLuggage;
    @FXML
    protected void searchLostLuggage(ActionEvent event) {
        String searchField = txtSearchLostLuggage.getText();
        ObservableList<ObservableList> data;
        data = FXCollections.observableArrayList();
        
        try(Connection conn = Database.initDatabase()){
            String SQL = "SELECT barcode,date,flightNummer "
                + "FROM luggage "
                + "WHERE lostFound = 0 "
                + "AND flightNummer LIKE '%"+searchField+"%'";
        ResultSet rs = conn.createStatement().executeQuery(SQL);
            //Add data to the tableview
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
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
        ObservableList<ObservableList> data;
        data = FXCollections.observableArrayList();
        
        try(Connection conn = Database.initDatabase()) {
            String SQL = "SELECT barcode,date,lostAirport,flightNummer "
                + "FROM luggage "
                + "WHERE lostFound = 1 "
                + "AND flightNummer LIKE '%"+searchField+"%'";
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
                + "WHERE lastName LIKE '%"+lastname+"%' "
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
