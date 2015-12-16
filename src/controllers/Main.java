package controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
 */
public class Main extends Application {
    private static BorderPane root = new BorderPane();
    public static Admin admin = new Admin();
    public static Username username = new Username();

    public static BorderPane getRoot(){
        return root;
    }
    
    @FXML private BorderPane master;
    @FXML private Label lblPage;
    @FXML private Label lblUser;
    @FXML private Label lblFunction;
    LoginController login = new LoginController();
    DashboardController dashboard = new DashboardController();
    
    @Override
    public void start(Stage primaryStage) {
        //Get login fxml from LoginController      
        BorderPane bpRoot = login.getLoginScreen();
        
        //Add fxml to the scene
        primaryStage.setScene(new Scene(bpRoot, 320, 400));
        primaryStage.setResizable(true);
        primaryStage.getIcons().add(new Image("images/favicon.png"));
        primaryStage.setTitle("Corendon Luggage Application");
        primaryStage.show();
    }
    
    @FXML
    protected void Logout(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML private Button cbManager;
    @FXML
    protected void getManagerScreen(ActionEvent event){
        ManagerController manager = new ManagerController();
        root.setLeft(manager.getManagerScreen());
    }
    
    public void hallo(){
        lblUser.setText(username.getUsername());
        int admin2=  admin.getAdmin();
        if (admin2 == 0) {
            cbManager.setVisible(false);
            lblFunction.setText("employee");
        }else{
            cbManager.setVisible(true);
            lblFunction.setText("Manager");
        }
    }
            
    @FXML private ComboBox cbLostLuggage;
    @FXML
    protected void selectLostLuggage(ActionEvent event){
        int selectedValue = cbLostLuggage.getSelectionModel().getSelectedIndex();
        switch(selectedValue) {
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
    
    @FXML private ComboBox cbCustomer;
    @FXML
    protected void selectCustomer(ActionEvent event){
        int selectedValue = cbCustomer.getSelectionModel().getSelectedIndex();
        switch(selectedValue) {
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
    
    @FXML private ComboBox cbFoundLuggage;
    @FXML
    protected void selectFoundLuggage(ActionEvent event){
        int selectedValue = cbFoundLuggage.getSelectionModel().getSelectedIndex();
        switch(selectedValue) {
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
    
    @FXML
    protected void getDashboardScreen(ActionEvent event){
        root.setLeft(dashboard.getDashboardScreen());
        lblPage.setText("Dashboard");
    }
    
    public static void main(String[] args) {
        launch(args);
    } 
}