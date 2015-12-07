/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 */
public class Main extends Application {
    private static BorderPane root = new BorderPane();
    public static BorderPane getRoot(){
        return root;
    }
    
    @FXML private BorderPane master;
    @FXML private Label lblPage;
    LoginController login = new LoginController();
    DashboardController dashboard = new DashboardController();
    
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
        lblPage.setText("Manager");
        
        int admin2 = 1;
        if (admin2 == 1) {
            cbManager.setDisable(true);
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
            case 2:
                EditLostLuggageController editLostLuggage = new EditLostLuggageController();
                root.setLeft(editLostLuggage.getEditLostLuggageScreen());
                lblPage.setText("Edit lost luggage");
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
            case 2:
                //editCustomerController editCustomers = new editCustomerController();
                //master.setLeft(allCustomers.getAllCustomerScreen());
                lblPage.setText("Edit customer");
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
            case 2:
                
                lblPage.setText("Edit found luggage");
                break;
        }
    }
    
    @FXML
    protected void getDashboardScreen(ActionEvent event){
        root.setLeft(dashboard.getDashboardScreen());
        lblPage.setText("Dashboard");
    }
    
    public BorderPane getMainScreen()  {
        BorderPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }
    
    public void setMasterContent(BorderPane content) {
        try {
            root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
            root.setLeft(content);
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    } 
}