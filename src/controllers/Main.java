package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author jandorresteijn
 * @version 1.0
 */
public class Main extends Application {
    
    @FXML private Label lblPage;
    @FXML private Label lblUser;
    @FXML private Label lblFunction;
    @FXML private Label lblDate;

    /*
    * This borderpane is the base of the programm
    */
    private static BorderPane root = new BorderPane();
    public static Employee employee = new Employee();

    LoginController login = new LoginController();
    DashboardController dashboard = new DashboardController();

    /*
    * This returns the root borderpane so other classes can change the
    * content of the screen.
    * @return  the root borderpane. 
    */
    public static BorderPane getRoot() {
        return root;
    }

    @Override
    public void start(Stage primaryStage) {
        //Get login fxml from LoginController      
        BorderPane bpRoot = login.getLoginScreen();

        //Add fxml to the scene
        primaryStage.setScene(new Scene(bpRoot, 320, 400));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("images/favicon.png"));
        primaryStage.setTitle("Corendon Luggage Application");
        primaryStage.show();
    }

    /*
    * If logout button is clicked. The programm will close.
    * @param ActionEvent event
    */
    @FXML
    protected void Logout(ActionEvent event) {
        try {
            //Set master screen
            BorderPane loginScreen = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
            
            //Add the fxml to the scene
            Scene scene = new Scene(loginScreen);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            stage.hide();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private Button cbManager;

    /*
    * If the Manager button is clicked. The root content will be set to the 
    * manager screen
    */
    @FXML
    protected void getManagerScreen() {
        ManagerController manager = new ManagerController();
        root.setLeft(manager.getManagerScreen());
    }

    /*
    * This method will set the username, function and disable/allow the
    * manager button in the menu on login
    */
    public void UsernameManager() {
        lblUser.setText(employee.getUsername());
        int functionId = employee.getFunctionID();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        if (functionId == 0) {
            cbManager.setVisible(false);
            lblFunction.setText("employee");
            lblDate.setText("Date: " + dateFormat.format(date));
        } else {
            cbManager.setVisible(true);
            lblFunction.setText("Manager");
            lblDate.setText("Date: " + dateFormat.format(date));
        }
    }

    @FXML
    private ComboBox cbLostLuggage;

    /*
    * Get the selected value from the dropdown in the menu
    * and set the screen content to the chosen value
    */
    @FXML
    protected void selectLostLuggage() {
        int selectedValue = cbLostLuggage.getSelectionModel().getSelectedIndex();
        switch (selectedValue) {
            case 0:
                AllLostLuggageController allLostLuggage = new AllLostLuggageController();
                root.setLeft(allLostLuggage.getAllLostLuggageScreen());
                lblPage.setText("All lost luggage");
                break;
            case 1:
                AddLostLuggageController addLostLuggage = new AddLostLuggageController();
                root.setLeft(addLostLuggage.getAddLostLuggageScreen());
                lblPage.setText("Add lost luggage");
                break;
        }
    }

    @FXML
    private ComboBox cbCustomer;
    
    /*
    * Get the selected value from the dropdown in the menu
    * and set the screen content to the chosen value
    */
    @FXML
    protected void selectCustomer() {
        int selectedValue = cbCustomer.getSelectionModel().getSelectedIndex();
        switch (selectedValue) {
            case 0:
                AllCustomerController allCustomers = new AllCustomerController();
                root.setLeft(allCustomers.getAllCustomerScreen());
                lblPage.setText("All customers");
                break;
            case 1:
                AddCustomerController addCustomer = new AddCustomerController();
                root.setLeft(addCustomer.getAddCustomerScreen());
                lblPage.setText("Add customer");
                break;
        }
    }

    @FXML
    private ComboBox cbFoundLuggage;

    /*
    * Get the selected value from the dropdown in the menu
    * and set the screen content to the chosen value
    */
    @FXML
    protected void selectFoundLuggage() {
        int selectedValue = cbFoundLuggage.getSelectionModel().getSelectedIndex();
        switch (selectedValue) {
            case 0:
                AllFoundLuggageController allFoundLuggage = new AllFoundLuggageController();
                root.setLeft(allFoundLuggage.getAllFoundLuggageScreen());
                lblPage.setText("All found luggage");
                break;
            case 1:
                AddFoundLuggageController addFoundLuggage = new AddFoundLuggageController();
                root.setLeft(addFoundLuggage.getAddFoundLuggageScreen());
                lblPage.setText("Add found luggage");
                break;
        }
    }

    /*
    * Set the screen content to the dashboard view.
    */
    @FXML
    protected void getDashboardScreen(ActionEvent event) {
        root.setLeft(dashboard.getDashboardScreen());
        lblPage.setText("Dashboard");
    }

    /*
    * 
    */
    public static void main(String[] args) {
        launch(args);
    }
}
