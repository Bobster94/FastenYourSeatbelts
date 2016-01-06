package controllers;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.Node;
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
 *
 * @author Bas
 */
public class SpecificCustomer {
    private List<String> contentLuggage = new ArrayList<>();
    private List<String> contentCustomer = new ArrayList<>();
    private List<String> labelsLuggage = new ArrayList<>();
    private List<String> labelsCustomer = new ArrayList<>();
    private String firstname;
    private String lastname;
    
    public void buildscreen(String id) {
        BorderPane root = Main.getRoot();
        try (Connection conn = Database.initDatabase()) {
            String selectLuggage = "SELECT * FROM customer WHERE id = " + id;
            ResultSet rs = conn.createStatement().executeQuery(selectLuggage);
            BorderPane layout = new BorderPane();
            VBox customerInformation = new VBox();
            VBox customerMatch = new VBox();
            if (rs.next()) {
                for (int i = 2; i < rs.getMetaData().getColumnCount(); i++) {
                    firstname = rs.getString("firstName");
                    lastname = rs.getString("lastName");
                    contentCustomer.add(rs.getString(i));
                    labelsCustomer.add(rs.getMetaData().getColumnName(i));
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
                Button btnEdit = new Button();
                btnEdit.setText("Edit");
                btnEdit.setOnAction((ActionEvent actionEvent) -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditCustomer.fxml"));
                        BorderPane screen = loader.load();
                        EditCustomerController controller = loader.getController();
                        controller.buildScreen(id);
                        root.setLeft(screen);
                    } catch (IOException ex) {
                        Logger.getLogger(AllLuggageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                Button btnMatch = new Button();
                btnMatch.setText("Match with luggage");
                btnMatch.setOnAction((ActionEvent actionEvent) -> {
                    layout.setRight(null);
                    TableView tvLuggage = new TableView();
                    tvLuggage.setMaxHeight(300);
                    tvLuggage.setMaxWidth(600);
                    ObservableList<detailedLuggage> foundData = FXCollections.observableArrayList();
                    try {
                        String SQL = "SELECT id,brand,color,type,weight,size,barcode,"
                                + "lostAirport,extra,material,date,flightNumber "
                                + "FROM luggage "
                                + "WHERE lostFound = 1 "
                                + "AND NOT EXISTS (SELECT idLuggage "
                                + "FROM history "
                                + "WHERE  luggage.id = history.idLuggage)";
                        Connection newConn = Database.initDatabase();
                        ResultSet rsLuggage = newConn.createStatement().executeQuery(SQL);
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
                            foundData.add(new detailedLuggage(params));
                        }
                        tvLuggage.setItems(foundData);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    tvLuggage.setRowFactory(tv -> {
                        TableRow<detailedLuggage> row = new TableRow<>();
                        row.setOnMouseClicked(event -> {
                            if (event.getClickCount() == 2 && (!row.isEmpty())) {
                                try {
                                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    String dateToday = dateFormat.format(new Date());

                                    String handleLuggageQuery = "INSERT INTO history "
                                            + "(status,idLuggage,idCustomer,dateHandled,idEmployeeHandled) "
                                            + "VALUES (?,?,?,?,?)";

                                    Connection handleLuggage = Database.initDatabase();
                                    PreparedStatement preparedStatement = handleLuggage.prepareStatement(handleLuggageQuery);
                                    preparedStatement.setString(1, "handled");
                                    preparedStatement.setInt(2, Integer.parseInt(row.getItem().getID()));
                                    preparedStatement.setInt(3, Integer.parseInt(id));
                                    preparedStatement.setDate(4, java.sql.Date.valueOf(dateToday));
                                    preparedStatement.setInt(5, Main.employee.getEmployeeID());
                                    preparedStatement.executeUpdate();
                                    
                                    String getSpecificLuggage = "SELECT brand,color,"
                                        + "type,weight,size,barcode,"
                                        + "lostAirport,extra,material,date,flightNumber "
                                        + "FROM luggage "
                                        + "WHERE id = "+row.getItem().getID();
                                    Connection specificLuggage = Database.initDatabase();
                                    ResultSet matchedLuggage = specificLuggage.createStatement().executeQuery(getSpecificLuggage);
                                    
                                    if(matchedLuggage.next()) { 
                                        for (int i = 1; i < matchedLuggage.getMetaData().getColumnCount(); i++) {
                                            contentLuggage.add(matchedLuggage.getString(i));
                                            labelsLuggage.add(matchedLuggage.getMetaData().getColumnName(i));
                                        } 
                                    }
                                    pdfController pdf = new pdfController();
                                    
                                    String[] labelsCustomerArray = new String[ labelsCustomer.size() ];
                                    labelsCustomer.toArray( labelsCustomerArray );
                                    
                                    String[] contentCustomerArray = new String[ contentCustomer.size() ];
                                    contentCustomer.toArray( contentCustomerArray );
                                    
                                    String[] labelsLuggageArray = new String[ labelsLuggage.size() ];
                                    labelsLuggage.toArray( labelsLuggageArray );
                                    
                                    String[] contentLuggageArray = new String[ contentLuggage.size() ];
                                    contentLuggage.toArray( contentLuggageArray );
                                    
                                    pdf.createPdf(
                                            labelsCustomerArray, 
                                            contentCustomerArray, 
                                            labelsLuggageArray, 
                                            contentLuggageArray,
                                            "Matched"
                                    );
                                    
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
