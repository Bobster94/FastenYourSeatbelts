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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Bas
 * @version 1.0
 */
public class AllLostLuggageController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateTableView();
    }

    private ObservableList<detailedLuggage> data;
    @FXML
    private TableView tvLostLuggage;
    @FXML
    private ComboBox cbColor;
    @FXML
    private TextField cbBrand;
    @FXML
    private ComboBox cbSize;
    @FXML
    private TextField txtMaterial;
    @FXML
    private TextField txtFlightnumber;
    @FXML
    private ComboBox cbType;
    @FXML
    private ComboBox cbWeight;
    @FXML
    private TextField txtExtra;
    @FXML
    private TextField txtDate;

    
    /*
    *
    * Search lostLuggage with the given values
    * Renew the TableView with the results
    */
    @FXML
    protected void searchLostLuggage() {
        String extra = txtExtra.getText();
        String date = txtDate.getText();
        String brand = cbBrand.getText();
        String material = txtMaterial.getText();
        String flightnumber = txtFlightnumber.getText();

        String color = "";
        if (cbColor.getValue() != null) {
            color = cbColor.getValue().toString();
        }

        String size = "";
        if (cbSize.getValue() != null) {
            size = cbSize.getValue().toString();
        }

        String weight = "";
        if (cbWeight.getValue() != null) {
            weight = cbWeight.getValue().toString();
        }

        String type = "";
        if (cbType.getValue() != null) {
            type = cbType.getValue().toString();
        }

        data = FXCollections.observableArrayList();
        try (Connection conn = Database.initDatabase()) {
            String SQL = "SELECT id,brand,color,type,weight,"
                    + "size,barcode,lostAirport,extra,material,"
                    + "date,flightNumber "
                    + "FROM luggage "
                    + "WHERE lostFound = 0 "
                    + "AND IFNULL(extra,'') LIKE '%" + extra + "%' "
                    + "AND IFNULL(date,'') LIKE '%" + date + "%' "
                    + "AND IFNULL(color,'') LIKE '%" + color + "%' "
                    + "AND IFNULL(brand,'') LIKE '%" + brand + "%' "
                    + "AND IFNULL(size,'') LIKE '%" + size + "%' "
                    + "AND IFNULL(material,'') LIKE '%" + material + "%' "
                    + "AND IFNULL(flightNumber,'') LIKE '%" + flightnumber + "%' "
                    + "AND IFNULL(weight,'') LIKE '%" + weight + "%' "
                    + "AND IFNULL(type,'') LIKE '%" + type + "%'";

            ResultSet rs = conn.createStatement().executeQuery(SQL);
            //Add data to the tableview
            while (rs.next()) {
                //Iterate Row
                String[] params = new String[rs.getMetaData().getColumnCount()];
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    params[i - 1] = rs.getString(i);
                }
                data.add(new detailedLuggage(params));
            }
            tvLostLuggage.setItems(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*
    *
    * Populate the TableView with LostLuggage data if the AllLostLuggage fxml is called
    */
    public void populateTableView() {
        data = FXCollections.observableArrayList();
        try (Connection conn = Database.initDatabase()) {
            String SQL = "SELECT id,brand,color,type,weight,size,"
                    + "barcode,lostAirport,extra,material,"
                    + "date,flightNumber "
                    + "FROM luggage "
                    + "WHERE lostFound = 0 "
                    + "AND NOT EXISTS (SELECT idLuggage "
                    + "FROM history "
                    + "WHERE  luggage.id = history.idLuggage)";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i + 1)));
                tvLostLuggage.getColumns().addAll(col);
            }

            //Add data to the tableview
            while (rs.next()) {
                //Iterate Row
                String[] params = new String[rs.getMetaData().getColumnCount()];
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    params[i - 1] = rs.getString(i);
                }
                data.add(new detailedLuggage(params));
            }
            tvLostLuggage.setItems(data);
        } catch (Exception e) {
            System.out.println(e);
        }
        AllLuggageController allLuggage = new AllLuggageController();
        tvLostLuggage.setRowFactory(tv -> {
            TableRow<detailedLuggage> row = new TableRow<>();
            BorderPane root = Main.getRoot();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    allLuggage.buildscreen(row.getItem().getID(), "lostLuggage");
                }
            });
            return row;
        });
    }

    /*
    *
    * @return the AllLostLuggage fxml view as BorderPane
    */
    public BorderPane getAllLostLuggageScreen() {
        BorderPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/views/AllLostLuggage.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }
}
