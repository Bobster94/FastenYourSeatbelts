package controllers;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Bas
 * @version 1.0
 */
public class RegisterController implements Initializable {
    Encryption encryption = new Encryption();
    
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    TextField txtUsername;
    @FXML
    TextField txtPassword;
    @FXML
    TextField txtFirstname;
    @FXML
    TextField txtLastname;
    @FXML
    TextField txtInsertion;
    @FXML
    TextField txtEmail;
    @FXML
    ComboBox cbFunction;

    @FXML
    private void registerAccount(ActionEvent event) throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String firstname = txtFirstname.getText();
            String lastname = txtLastname.getText();
            String insertion = txtInsertion.getText();
            String email = txtEmail.getText();
            int function = cbFunction.getSelectionModel().getSelectedIndex();
            
            byte[] salt = encryption.generateSalt();
            byte[] encryptedPassword = encryption.getEncryptedPassword(password, salt);
            
            Connection conn = (Connection) Database.initDatabase();
            String addEmployee = "INSERT INTO employee "
                    + "(userName,firstName,insertion,lastName,password,email,admin,salt) "
                    + "VALUES (?,?,?,?,?,?,?,?)";  
            
            PreparedStatement preparedStatement = conn.prepareStatement(addEmployee);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, firstname);
            preparedStatement.setString(3, insertion); 
            preparedStatement.setString(4, lastname);
            preparedStatement.setBytes(5, encryptedPassword);
            preparedStatement.setString(6, email);
            preparedStatement.setInt(7, function);
            preparedStatement.setBytes(8, salt);
            preparedStatement.executeUpdate();
            
            BorderPane root = Main.getRoot();
            DashboardController dashboard = new DashboardController();
            root.setLeft(dashboard.getDashboardScreen());
        } catch (SQLException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public BorderPane getRegisterScreen() {
        BorderPane screen = null;
        try {
            screen = FXMLLoader.load(getClass().getResource("/views/Register.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return screen;
    }
}
