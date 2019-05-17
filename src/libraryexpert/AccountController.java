/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryexpert;

import static MD5Hash.MD5.getHash;
import connectivity.ConnectionClass;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static libraryexpert.DBFunctions.UsernameTaken;
import static libraryexpert.DBFunctions.VerifyCredentials;
/**
 *
 * @author Ghita David Leonard
 */
public class AccountController {    
    @FXML
    private TextField txtUsername;
    
    @FXML
    private PasswordField txtPassword, confirmPassword, txtAdminPassword;
    
    public void Register(ActionEvent event) throws IOException, ClassNotFoundException, SQLException, NoSuchAlgorithmException{
        //We use this object to get the connection string to our database
        ConnectionClass connectionClass = new ConnectionClass();
        
         //We try to establish our connection and at the end of the 'try' the 'connection' object will be destroyed
        try (Connection connection = connectionClass.getConnection()) {
            //We are creating our sql command
            String sqlQ = "INSERT INTO `Users` (`Username`, `Password`) VALUES ('"+txtUsername.getText()+"', '"+getHash(txtPassword.getText())+"')";
            
            //We check if username is available
            if(UsernameTaken(txtUsername.getText())){
                
                //If username is taken we create an alert that specifies our error
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Username taken");
                alert.setHeaderText(null);
                alert.setContentText("The username you tried to use is already registered!");
                alert.showAndWait();
            }
            else 
                //We check if the password and confirmation password are the same
                if(!txtPassword.getText().equals(confirmPassword.getText())){
                    
                    //If password doesn't match we create an alert that specifies our error
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Passwrod doesn't match");
                    alert.setHeaderText(null);
                    alert.setContentText("The confirmation password entered is different from the entered password!");
                    alert.showAndWait();
            }
            else{
                //We check the length of the 'username' to not have too many or to few characters
                if(txtUsername.getText().length() > 50 || txtUsername.getText().length() < 2){
                    if(txtUsername.getText().length() > 50){
                        //If length is more than 50 we create an alert that specifies the error
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Username too long");
                        alert.setHeaderText(null);
                        alert.setContentText("The username can't have more than 50 characters!");
                        alert.showAndWait();
                    }else{
                        //If length if less than 50 we create an alert that specifies the error
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Username too short");
                        alert.setHeaderText(null);
                        alert.setContentText("The username can't have less than 2 characters!");
                        alert.showAndWait();
                    }
                }
                else
                    //We check the length of the 'password' to not have too many or to few characters
                    if(txtPassword.getText().length() > 50 || txtPassword.getText().length() < 6){
                        if(txtPassword.getText().length() > 50){
                            //If length is more than 50 we create an alert that specifies the error
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Password too long");
                            alert.setHeaderText(null);
                            alert.setContentText("The password can't have more that 50 characters!");
                            alert.showAndWait();
                        }
                        else{
                            //If length if less than 50 we create an alert that specifies the error
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Password too short");
                            alert.setHeaderText(null);
                            alert.setContentText("The password can't have less that 6 characters!");
                            alert.showAndWait();
                        }
                    }else
                        //We check if the person was authorized to make an account
                        if(!txtAdminPassword.getText().equals("parolaDeVerificare1234")){
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Verification password error");
                            alert.setHeaderText(null);
                            alert.setContentText("The admin password is incorrect!");
                            alert.showAndWait();
                        }
                     //If all potetial errors werr passed we can register the user
                    else{
                        //We are creating our command
                        Statement statement = connection.createStatement();
                        //We are executing our command
                        statement.executeUpdate(sqlQ);
                        //We specifie to the user that his account was succesfully created
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Account ''"+txtUsername.getText()+"'' was created succesfully!");
                        alert.showAndWait();
                    }
                }
        }
    }
    
    public void SignIn(ActionEvent event) throws IOException, ClassNotFoundException, SQLException, NoSuchAlgorithmException{
        //We check if the credentials exists in our database
        if(VerifyCredentials(txtUsername.getText(),txtPassword.getText())){
            //If the credentials exists we display to our user the home view
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("/Fxml/Home.fxml"));
        
            Scene home_page_scene = new Scene(home_page_parent);
            home_page_scene.getStylesheets().add(getClass().getResource("/StaticFiles/style.css").toExternalForm());
            Stage libraryExpert_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
            libraryExpert_stage.setScene(home_page_scene);
            
            libraryExpert_stage.setTitle("Home");
            
            libraryExpert_stage.show();
        }
        else{
            //If the credentials doesn't exists we specifie that to the user
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Username or password is invalid");
            alert.setHeaderText(null);
            alert.setContentText("The username or password is incorrect. Please try logging in again.");
            alert.showAndWait();
        }
       
    }
    
    //This method is used to change the view with 'SignUp' view
    public void RedirectToSignUp(ActionEvent event) throws IOException{
        //We specifie the path to the view that we want to display
        Parent sign_up_parent = FXMLLoader.load(getClass().getResource("/Fxml/Register.fxml"));
                
        Scene sign_up_scene = new Scene(sign_up_parent);
        //We are creating a 'node' between the last view and this view so they can be swapped in the same window
        Stage sign_up_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        sign_up_stage.setScene(sign_up_scene);
        
        sign_up_stage.setTitle("Register");
        
        sign_up_stage.centerOnScreen();
        
        sign_up_stage.show();
    }
    
    //This method is used to change the view with 'SignIn' view
    public void RedirectToSignIn(ActionEvent event) throws IOException{
        //We specifie the path to the view that we want to display
        Parent sign_up_parent = FXMLLoader.load(getClass().getResource("/Fxml/Login.fxml"));
        
        Scene sign_up_scene = new Scene(sign_up_parent);
        //We are creating a 'node' between the last view and this view so they can be swapped in the same window
        Stage sign_up_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        sign_up_stage.setScene(sign_up_scene);
        
        sign_up_stage.setTitle("Login");
        
        sign_up_stage.centerOnScreen();
        
        sign_up_stage.show();
    }
    
}
