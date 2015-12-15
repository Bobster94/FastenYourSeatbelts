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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Bas
 */
public class AllCustomerController implements Initializable {
    private ObservableList<detailedCustomer> data;
    @FXML private TableView tvCustomers;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList();
          try(Connection conn = Database.initDatabase()) {
            String SQL = "SELECT id,firstName as 'firstname',"
                    + "insertion,lastName as 'lastname',"
                    + "birthDate as 'date of birth',"
                    + "country,city,zipCode as 'zipcode', street,houseNumber as 'house number',"
                    + "email,phoneNumber as 'phone number' "
                    + "FROM customer";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            for(int i=1 ; i<rs.getMetaData().getColumnCount(); i++) {           
                TableColumn col = new TableColumn(rs.getMetaData().getColumnLabel(i+1));
                col.setCellValueFactory(new PropertyValueFactory<>(rs.getMetaData().getColumnName(i + 1)));
                tvCustomers.getColumns().addAll(col); 
            }
            
            //Add data to the tableview
            while(rs.next()) {
                //Iterate Row
                String[] params = new String[rs.getMetaData().getColumnCount()];
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    params[i-1] = rs.getString(i);
                }
                data.add(new detailedCustomer(params));
            }
            tvCustomers.setItems(data);
            }catch(Exception e){
                System.out.println(e);             
            }
        SpecificCustomer specCustomer = new SpecificCustomer();
        tvCustomers.setRowFactory(tv -> {
            TableRow<detailedCustomer> row = new TableRow<>();
            BorderPane root = Main.getRoot();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    specCustomer.buildscreen(row.getItem().getId());
                }
            });
            return row;
        });
    }   
    
    public BorderPane getAllCustomerScreen() {
        BorderPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/views/AllCustomer.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }   
}
