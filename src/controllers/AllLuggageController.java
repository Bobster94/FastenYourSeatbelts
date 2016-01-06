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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
 * FXML Controller class
 *
 * @author Bas
 * @version 1.0
 */
public class AllLuggageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    private String idCustomer = "";
    /*
     * Builds the screen with the specific luggage information on the screen.
     * @param    String  id          This is the id used in workbench for the luggage
     * @param    String  luggageType Is either lostLuggage or foundLuggage
     */
    public void buildscreen(String id, String luggageType) {
        BorderPane root = Main.getRoot();
        try (Connection conn = Database.initDatabase()) {
            String selectLuggage = "SELECT * FROM luggage WHERE id = " + id;
            ResultSet rs = conn.createStatement().executeQuery(selectLuggage);
            BorderPane layout2 = new BorderPane();
            VBox layout = new VBox();
            VBox customerLayout = new VBox();
            if (rs.next()) {
                for (int i = 2; i < rs.getMetaData().getColumnCount(); i++) {
                    if ("lostFound".equals(rs.getMetaData().getColumnName(i))) {
                        Label label = new Label();
                        label.setText("Status");
                        label.setPadding(new Insets(10, 0, 0, 10));
                        Label dataLabel = new Label();
                        if ("0".equals(rs.getString(i))) {
                            dataLabel.setText("Lost");
                        } else {
                            dataLabel.setText("Found");
                        }
                        dataLabel.setPadding(new Insets(10, 0, 0, 10));
                        HBox hbox = new HBox();
                        hbox.getChildren().addAll(label, dataLabel);
                        layout.getChildren().add(hbox);
                    } else {
                        Label label = new Label();
                        label.setText(rs.getMetaData().getColumnName(i) + ":");
                        label.setPadding(new Insets(10, 0, 0, 10));
                        Label dataLabel = new Label();
                        dataLabel.setText(rs.getString(i));
                        dataLabel.setPadding(new Insets(10, 0, 0, 10));
                        HBox hbox = new HBox();
                        hbox.getChildren().addAll(label, dataLabel);
                        layout.getChildren().add(hbox);
                    }
                }
                HBox hbox = new HBox();
                Button btnEdit = new Button();
                btnEdit.setText("Edit");
                btnEdit.setOnAction((ActionEvent actionEvent) -> {
                    try {
                        if ("lostLuggage".equals(luggageType)) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditLostLuggage.fxml"));
                            BorderPane screen = loader.load();
                            EditLostLuggageController controller = loader.getController();
                            controller.buildScreen(id);
                            root.setLeft(screen);
                        } else if ("foundLuggage".equals(luggageType)) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditFoundLuggage.fxml"));
                            BorderPane screen = loader.load();
                            EditFoundLuggageController controller = loader.getController();
                            controller.buildScreen(id);
                            root.setLeft(screen);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(AllLuggageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                if ("foundLuggage".equals(luggageType)) {
                    Button btnMatch = new Button();
                    btnMatch.setText("Match with customer");
                    btnMatch.setOnAction((ActionEvent actionEvent) -> {
                        TableView tvLuggage = new TableView();
                        tvLuggage.setMaxHeight(300);
                        tvLuggage.setMaxWidth(600);
                        ObservableList<detailedCustomer> foundData = FXCollections.observableArrayList();
                        try {
                            String SQL = "SELECT id,firstName as 'firstname',"
                                    + "insertion,lastName as 'lastname',"
                                    + "birthDate as 'date of birth',"
                                    + "country,city,zipCode as 'zipcode', "
                                    + "street,houseNumber as 'house number',"
                                    + "email,phoneNumber as 'phone number' "
                                    + "FROM customer";
                            Connection newConn = Database.initDatabase();
                            ResultSet rsLuggage = newConn.createStatement().executeQuery(SQL);
                            for (int i = 1; i < rsLuggage.getMetaData().getColumnCount(); i++) {
                                TableColumn col = new TableColumn(rsLuggage.getMetaData().getColumnLabel(i + 1));
                                col.setCellValueFactory(new PropertyValueFactory<>(rsLuggage.getMetaData().getColumnName(i + 1)));
                                tvLuggage.getColumns().add(col);
                            }

                            //Add data to the tableview
                            while (rsLuggage.next()) {
                                //Iterate Row
                                String[] params = new String[rsLuggage.getMetaData().getColumnCount()];
                                for (int i = 1; i <= rsLuggage.getMetaData().getColumnCount(); i++) {
                                    //Iterate Column
                                    params[i - 1] = rsLuggage.getString(i);
                                }
                                foundData.add(new detailedCustomer(params));
                            }
                            tvLuggage.setItems(foundData);

                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        tvLuggage.setRowFactory(tv -> {
                            TableRow<detailedCustomer> row = new TableRow<>();
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
                                        preparedStatement.setInt(2, Integer.parseInt(id));
                                        preparedStatement.setInt(3, Integer.parseInt(row.getItem().getId()));
                                        preparedStatement.setDate(4, java.sql.Date.valueOf(dateToday));
                                        preparedStatement.setInt(5, Main.employee.getEmployeeID());
                                        preparedStatement.executeUpdate();

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
                        layout2.setRight(tvLuggage);
                        btnMatch.setDisable(true);
                    });
                    hbox.getChildren().addAll(btnMatch);
                } else {
                    String matchedCustomer = "SELECT id,firstName as 'firstname',"
                            + "insertion,lastName as 'lastname',"
                            + "birthDate as 'date of birth',"
                            + "country,city,zipCode as 'zipcode', "
                            + "street,houseNumber as 'house number',"
                            + "email,phoneNumber as 'phone number' "
                            + "FROM customer "
                            + "Where id = " + rs.getString("idCustomer");
                    Connection newConn = Database.initDatabase();
                    ResultSet rsMatchedCustomer = newConn.createStatement().executeQuery(matchedCustomer);
                    while (rsMatchedCustomer.next()) {
                        idCustomer = rsMatchedCustomer.getString("id");
                        for (int i = 2; i < rsMatchedCustomer.getMetaData().getColumnCount(); i++) {
                            Label label = new Label();
                            label.setText(rsMatchedCustomer.getMetaData().getColumnLabel(i) + ":");
                            label.setPadding(new Insets(10, 0, 0, 10));
                            Label dataLabel = new Label();
                            dataLabel.setText(rsMatchedCustomer.getString(i));
                            dataLabel.setPadding(new Insets(10, 0, 0, 10));
                            HBox hboxx = new HBox();
                            hboxx.getChildren().addAll(label, dataLabel);
                            customerLayout.getChildren().addAll(hboxx);
                        }
                    }
                    HBox buttons = new HBox();
                    Button btnMatch = new Button();
                    btnMatch.setText("Set as solved");
                    btnMatch.setOnAction((ActionEvent actionEvent) -> {
                        try {
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String dateToday = dateFormat.format(new Date());

                            String handleLuggageQuery = "INSERT INTO history "
                                    + "(status,idLuggage,idCustomer,dateHandled,idEmployeeHandled) "
                                    + "VALUES (?,?,?,?,?)";

                            Connection handleLuggage = Database.initDatabase();
                            PreparedStatement preparedStatement = handleLuggage.prepareStatement(handleLuggageQuery);
                            preparedStatement.setString(1, "handled");
                            preparedStatement.setInt(2, Integer.parseInt(id));
                            preparedStatement.setInt(3, Integer.parseInt(idCustomer));
                            preparedStatement.setDate(4, java.sql.Date.valueOf(dateToday));
                            preparedStatement.setInt(5, Main.employee.getEmployeeID());
                            preparedStatement.executeUpdate();

                            DashboardController dashboardCont = new DashboardController();
                            BorderPane dashboard = dashboardCont.getDashboardScreen();
                            root.setLeft(dashboard);

                        } catch (SQLException ex) {
                            Logger.getLogger(AllLuggageController.class.getName())
                                    .log(Level.SEVERE, null, ex);
                        }
                    });

                    Button btnGenPDF = new Button();
                    btnGenPDF.setText("Create PDF");
                    btnGenPDF.setOnAction((ActionEvent actionEvent) -> {

                    });
                    buttons.getChildren().addAll(btnMatch, btnGenPDF);
                    customerLayout.getChildren().add(buttons);
                    layout2.setRight(customerLayout);
                }
                hbox.getChildren().addAll(btnEdit);
                layout.getChildren().add(hbox);
                layout2.setLeft(layout);
                root.setLeft(layout2);
            } else {
                root.setLeft(new Label("Foutmelding"));
            }
        } catch (Exception ex) {
            System.out.println("ging wat fout");
        }
    }
}
