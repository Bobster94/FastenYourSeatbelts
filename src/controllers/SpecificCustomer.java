package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is used to show the specific customer view 
 * @author Bas
 * @version 1.0
 */
public class SpecificCustomer {
    private List<String> contentLuggage = new ArrayList<>();
    private List<String> contentCustomer = new ArrayList<>();
    private List<String> labelsLuggage = new ArrayList<>();
    private List<String> labelsCustomer = new ArrayList<>();
    private String firstname;
    private String lastname;
    
    /*
    * This method builds the screen for specific customer
    * @param String id  This is de customer id
    */
    public void buildscreen(String id) {
        //Get the root borderpane from main
        BorderPane root = Main.getRoot();
        try (Connection conn = Database.initDatabase()) {
            //Build and run the sql query
            String selectLuggage = "SELECT * FROM customer WHERE id = " + id;
            ResultSet rs = conn.createStatement().executeQuery(selectLuggage);
            
            //Build the layout
            BorderPane layout = new BorderPane();
            VBox customerInformation = new VBox();
            VBox customerMatch = new VBox();
            
            //If there are results in the resultset
            if (rs.next()) {
                //Loop through the columns from the resultset
                for (int i = 2; i < rs.getMetaData().getColumnCount(); i++) {
                    //Fill these variables for the pdf later in this method
                    firstname = rs.getString("firstName");
                    lastname = rs.getString("lastName");
                    contentCustomer.add(rs.getString(i));
                    labelsCustomer.add(rs.getMetaData().getColumnName(i));
                    
                    //Create layout
                    Label label = new Label();
                    label.setText(rs.getMetaData().getColumnName(i) + ":");
                    label.setPadding(new Insets(10, 0, 0, 10));
                    Label dataLabel = new Label();
                    dataLabel.setText(rs.getString(i));
                    dataLabel.setPadding(new Insets(10, 0, 0, 10));
                    HBox hbox = new HBox();
                    hbox.getChildren().addAll(label, dataLabel);
                    customerInformation.getChildren().add(hbox);
                }
                HBox hbox = new HBox();
                //Create edit button and onClick function
                Button btnEdit = new Button();
                btnEdit.setText("Edit");
                btnEdit.setOnAction((ActionEvent actionEvent) -> {
                    try {
                        //Get and load edit customer fxml
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditCustomer.fxml"));
                        BorderPane screen = loader.load();
                        
                        //Get the controller for edit customer
                        EditCustomerController controller = loader.getController();
                        
                        //Go to the edit customer screen
                        controller.buildScreen(id);
                        root.setLeft(screen);
                    } catch (IOException ex) {
                        Logger.getLogger(AllLuggageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                //Create match button and onClick function
                Button btnMatch = new Button();
                btnMatch.setText("Match with luggage");
                btnMatch.setOnAction((ActionEvent actionEvent) -> {
                    layout.setRight(null);
                    
                    //Create new tableview
                    TableView tvLuggage = new TableView();
                    tvLuggage.setMaxHeight(300);
                    tvLuggage.setMaxWidth(600);
                    
                    //Create observableList with detailedLuggage datatype (class)
                    ObservableList<detailedLuggage> foundData = FXCollections.observableArrayList();
                    try {
                        //Create and run sql query
                        String SQL = "SELECT id,brand,color,type,weight,size,barcode,"
                                + "lostAirport,extra,material,date,flightNumber "
                                + "FROM luggage "
                                + "WHERE lostFound = 1 "
                                + "AND NOT EXISTS (SELECT idLuggage "
                                + "FROM history "
                                + "WHERE  luggage.id = history.idLuggage)";
                        Connection newConn = Database.initDatabase();
                        ResultSet rsLuggage = newConn.createStatement().executeQuery(SQL);
                        //Create the columns for the tableview
                        for (int i = 1; i < rsLuggage.getMetaData().getColumnCount(); i++) {
                            TableColumn col = new TableColumn(rsLuggage.getMetaData().getColumnLabel(i + 1));
                            col.setCellValueFactory(new PropertyValueFactory<>(rsLuggage.getMetaData().getColumnName(i + 1)));
                            tvLuggage.getColumns().addAll(col);
                        }

                        //Add data to the tableview
                        while (rsLuggage.next()) {
                            //Iterate Row
                            String[] params = new String[rsLuggage.getMetaData().getColumnCount()];
                            for (int i = 1; i <= rsLuggage.getMetaData().getColumnCount(); i++) {
                                //Iterate Column
                                params[i - 1] = rsLuggage.getString(i);
                            }
                            //Save the array into the detailedLuggage class
                            foundData.add(new detailedLuggage(params));
                        }
                        //Add the data to the tableview
                        tvLuggage.setItems(foundData);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    //On double click on a tableview row
                    tvLuggage.setRowFactory(tv -> {
                        TableRow<detailedLuggage> row = new TableRow<>();
                        row.setOnMouseClicked(event -> {
                            if (event.getClickCount() == 2 && (!row.isEmpty())) {
                                try {
                                    //Get current date
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    String dateToday = dateFormat.format(new Date());
                                    
                                    //Create and run sql query
                                    String handleLuggageQuery = "INSERT INTO history "
                                            + "(status,idLuggage,idCustomer,dateHandled,idEmployeeHandled) "
                                            + "VALUES (?,?,?,?,?)";

                                    Connection handleLuggage = Database.initDatabase();
                                    
                                    //Add values to the query
                                    PreparedStatement preparedStatement = handleLuggage.prepareStatement(handleLuggageQuery);
                                    preparedStatement.setString(1, "handled");
                                    preparedStatement.setInt(2, Integer.parseInt(row.getItem().getID()));
                                    preparedStatement.setInt(3, Integer.parseInt(id));
                                    preparedStatement.setDate(4, java.sql.Date.valueOf(dateToday));
                                    preparedStatement.setInt(5, Main.employee.getEmployeeID());
                                    preparedStatement.executeUpdate();
                                    
                                    //Create and run sql query
                                    String getSpecificLuggage = "SELECT brand,color,"
                                        + "type,weight,size,barcode,"
                                        + "lostAirport,extra,material,date,flightNumber "
                                        + "FROM luggage "
                                        + "WHERE id = "+row.getItem().getID();
                                    Connection specificLuggage = Database.initDatabase();
                                    ResultSet matchedLuggage = specificLuggage.createStatement().executeQuery(getSpecificLuggage);
                                    
                                    //Add values to the arrayList
                                    if(matchedLuggage.next()) { 
                                        for (int i = 1; i < matchedLuggage.getMetaData().getColumnCount(); i++) {
                                            contentLuggage.add(matchedLuggage.getString(i));
                                            labelsLuggage.add(matchedLuggage.getMetaData().getColumnName(i));
                                        } 
                                    }
                                    pdfController pdf = new pdfController();
                                    
                                    //Convert all arrayLists to normal array's
                                    String[] labelsCustomerArray = new String[ labelsCustomer.size() ];
                                    labelsCustomer.toArray( labelsCustomerArray );
                                    
                                    String[] contentCustomerArray = new String[ contentCustomer.size() ];
                                    contentCustomer.toArray( contentCustomerArray );
                                    
                                    String[] labelsLuggageArray = new String[ labelsLuggage.size() ];
                                    labelsLuggage.toArray( labelsLuggageArray );
                                    
                                    String[] contentLuggageArray = new String[ contentLuggage.size() ];
                                    contentLuggage.toArray( contentLuggageArray );
                                    
                                    //Create the pdf
                                    pdf.createPdf(
                                            labelsCustomerArray, 
                                            contentCustomerArray, 
                                            labelsLuggageArray, 
                                            contentLuggageArray,
                                            "Matched"
                                    );
                                    
                                    //Save the pdf witht the parameter as filename
                                    pdf.save("matchedLuggage_"+firstname+lastname+"_"+row.getItem().getID()+dateToday+".pdf");

                                    DashboardController dashboardCont = new DashboardController();
                                    BorderPane dashboard = dashboardCont.getDashboardScreen();
                                    root.setLeft(dashboard);

                                } catch (SQLException ex) {
                                    Logger.getLogger(AllLuggageController.class.getName())
                                            .log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                        return row;
                    });
                    btnMatch.setDisable(true);

                    customerMatch.getChildren().addAll(tvLuggage);
                    layout.setRight(customerMatch);
                });
                Button btnCreateLuggage = new Button();
//                btnCreateLuggage.setText("Add new luggage");
//                btnCreateLuggage.setOnAction((ActionEvent actionEvent) -> {
//                    layout.setRight(layout);
//                    try {
//                        URL paneOneUrl = getClass().getResource("/views/AddFoundLuggage.fxml");
//                        customerMatch.getChildren().addAll((Node) FXMLLoader.load(paneOneUrl));
//                        layout.setRight(customerMatch);
//                    } catch (IOException ex) {
//                        Logger.getLogger(SpecificCustomer.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                });
                
                hbox.getChildren().addAll(btnEdit, btnMatch);
                customerInformation.getChildren().addAll(hbox);
                layout.setLeft(customerInformation);
                root.setLeft(layout);
            } else {
                root.setLeft(new Label("Foutmelding"));
            }
        } catch (Exception ex) {
            System.out.println("ging wat fout");
        }
    }
}
