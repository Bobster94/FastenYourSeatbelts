package controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mark
 */
public class LoginController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML private Label lblError;
    /*
    * This method validates the username and password 
    * @param String username    Username from the employee
    * @param String password    Password from the employee
    * @return Boolean           If the username and password match a record. Return true
    */
    private Boolean loginValidation(String username, String password) {
        Boolean valid = false;
        
        try(Connection conn = Database.initDatabase()) {
            //Select the employee with the given username and password
            String selectEmployee = 
                    "SELECT id,userName,firstName,lastName,password,email,admin " +
                    "FROM employee " +
                    "WHERE userName = ? AND password = ?";
            
            //Create prepared statment
            PreparedStatement preparedStatement = conn.prepareStatement(selectEmployee);
            
            //set values
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            //execute query and get results
            ResultSet employee = preparedStatement.executeQuery();
       
            //if there are no records found.
            if (!employee.next()) { 
                lblError.setText("Username and/or password is wrong");
                lblError.setVisible(true);
            } else {
                Main.employee.setUsername(employee.getString("firstName"));
                Main.employee.setFunctionID(employee.getInt("admin"));
                Main.employee.setEmployeeID(employee.getInt("id"));
                valid = true;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valid;
    }
    
    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;
    @FXML private Button cbManager;
    
    /*
    * This method is called if the employee clicked on the login button
    * @param ActionEvent event
    */
    @FXML 
    protected void Login(ActionEvent event) throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        
        Boolean valid = loginValidation(username,password);
        if(valid) {
            //Get the main fxml
            Main main = new Main();
            
            //Set master screen
            BorderPane root = main.getRoot();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Main.fxml"));
            root.setTop((Node) loader.load());
            Main controller = loader.getController();
            controller.UsernameManager();
            
            root.setLeft((Node) FXMLLoader.load(getClass().getResource("/views/Dashboard.fxml")));
            
            //Add the fxml to the scene
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            stage.hide();
            stage.setScene(scene);
            stage.show();
        }
    }
    
    /*
    * This method will load the login fxml into a borderpane
    * @return borderpane    Returns the login view as borderpane
    */
    public BorderPane getLoginScreen() {
        BorderPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return screen;
    }
    
    /*
    * If the decline button is pressed the application will close
    *@param ActionEvent event
    */
    @FXML
    protected void Logout(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}