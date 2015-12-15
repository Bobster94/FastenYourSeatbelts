package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Bas
 */
public class SpecificCustomer {

    public void buildscreen(String id) {
        BorderPane root = Main.getRoot();
        try (Connection conn = Database.initDatabase()) {
            String selectLuggage = "SELECT * FROM customer WHERE id = " + id;
            ResultSet rs = conn.createStatement().executeQuery(selectLuggage);
            VBox layout = new VBox();
            if (rs.next()) {
                for (int i = 2; i < rs.getMetaData().getColumnCount(); i++) {
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

                });
                hbox.getChildren().addAll(btnEdit, btnMatch);
                layout.getChildren().add(hbox);
                root.setLeft(layout);
            } else {
                root.setLeft(new Label("Foutmelding"));
            }
        } catch (Exception ex) {
            System.out.println("ging wat fout");
        }
    }
}
