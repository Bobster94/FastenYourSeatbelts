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
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Bas
 */
public class AllFoundLuggageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateTableView();
    } 
    private ObservableList<ObservableList> data;
    @FXML private TableView tvFoundLuggage;
    @FXML private ComboBox cbColor;
    @FXML private TextField cbBrand;
    @FXML private ComboBox cbSize;
    @FXML private TextField txtMaterial;
    @FXML private TextField txtFlightnumber;
    @FXML private ComboBox cbType;
    @FXML private ComboBox cbWeight;
    @FXML private TextField txtExtra;
    @FXML private TextField txtDate;
    
    @FXML
    protected void searchFoundLuggage(ActionEvent event) {
        String extra = txtExtra.getText();
        String date = txtDate.getText();
        String brand = cbBrand.getText();
        String material = txtMaterial.getText();
        String flightnumber = txtFlightnumber.getText();
        
        String color = "";
        if(cbColor.getValue() != null ) {
            color = cbColor.getValue().toString();
        }
        
        String size = "";
        if(cbSize.getValue() != null ) {
            size = cbSize.getValue().toString();
        }
        
        String weight = "";
        if(cbWeight.getValue() != null ) {
            weight = cbWeight.getValue().toString();
        }
        
        String type = "";
        if(cbType.getValue() != null ) {
            type = cbType.getValue().toString();
        }
        
          data = FXCollections.observableArrayList();
          try(Connection conn = Database.initDatabase()){
            String SQL = "SELECT brand,color,type,weight,"
                    + "size,barcode,lostAirport,extra,material,"
                    + "date,flightNumber "
                    + "FROM luggage "
                    + "WHERE lostFound = 0 "
                    + "AND extra LIKE '%"+extra+"%' "
                    + "AND date LIKE '%"+date+"%' "
                    + "AND color LIKE '%"+color+"%' "
                    + "AND brand LIKE '%"+brand+"%' "
                    + "AND size LIKE '%"+size+"%' "
                    + "AND material LIKE '%"+material+"%' "
                    + "AND flightNummer LIKE '%"+flightnumber+"%' "
                    + "AND weight LIKE '%"+weight+"%' "
                    + "AND type LIKE '%"+type+"%'";
            
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
                tvFoundLuggage.setItems(data);
            }catch(Exception e){
                System.out.println("Error on filling the tableview");             
            }
      }
    
    public void populateTableView() {
        data = FXCollections.observableArrayList();
          try(Connection conn = Database.initDatabase()){
            String SQL = "SELECT brand,color,type,weight,size,barcode,lostAirport,extra,material,date,flightNumber FROM luggage WHERE lostFound = 1";
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
                tvFoundLuggage.getColumns().addAll(col); 
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
                tvFoundLuggage.setItems(data);
            }catch(Exception e){
                System.out.println("Error on filling the tableview");             
            }
    }
    
    public BorderPane getAllFoundLuggageScreen() {
        BorderPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/views/AllFoundLuggage.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }  
}
