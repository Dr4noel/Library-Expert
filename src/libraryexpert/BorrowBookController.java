/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryexpert;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import static libraryexpert.DBFunctions.DeleteByQuantity;
import static libraryexpert.DBFunctions.SaveBorrower;
import static libraryexpert.DBFunctions.UpdateQuantity;
import static libraryexpert.DBFunctions.allreadyBoorowedBy;
import static libraryexpert.DBFunctions.getQuantity;


/**
 * FXML Controller class
 *
 * @author Ghita David Leonard
 */
public class BorrowBookController implements Initializable {

    @FXML
    private TextField nameRow;
    @FXML
    private TextField addressRow;
    @FXML
    private TextField phoneRow;
    @FXML
    private TextField dateRow;
    @FXML
    private TextField titleRow;
    @FXML
    private TextField identifierRow;
    
    private String author;
    private String publishHouse;
    private int releaseDate;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setValues(String title, String identifier, String author, int releaseDate, String publishHouse){
        this.titleRow.setText(title);
        this.identifierRow.setText(identifier);
        this.dateRow.setText(getCurrentDateTime());
        this.author = author;
        this.releaseDate = releaseDate;
        this.publishHouse = publishHouse;
    }
    
    public String getCurrentDateTime(){
       DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
       Date dateobj = new Date();
       return df.format(dateobj);
    }
    
    public void BorrowTheBook() throws ClassNotFoundException, ClassNotFoundException, SQLException{
        if(nameRow.getText().length() == 0 && addressRow.getText().length() == 0 && phoneRow.getText().length() == 0){
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Fields are empty");
            alert.setHeaderText(null);
            alert.setContentText("The fields must be completed, plase fill them all!");
            alert.showAndWait();
        }else
            if(nameRow.getText().length() > 50 || nameRow.getText().length() < 2){
                if(nameRow.getText().length() > 50){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Name is too long");
                    alert.setHeaderText(null);
                    alert.setContentText("The name can't have more than 50 characters!");
                    alert.showAndWait();
                }else{
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Name is too short");
                    alert.setHeaderText(null);
                    alert.setContentText("The name can't have less than 2 characters!");
                    alert.showAndWait();
                }
            }else
                if(addressRow.getText().length() > 50 || addressRow.getText().length() < 2){
                    if(addressRow.getText().length() > 50){
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Address is too long");
                        alert.setHeaderText(null);
                        alert.setContentText("The address can't have more than 50 characters!");
                        alert.showAndWait();
                    }else{
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Address is too short");
                        alert.setHeaderText(null);
                        alert.setContentText("The address can't have less than 2 characters!");
                        alert.showAndWait();
                    }
                }else
                    if(phoneRow.getText().length() > 50 || phoneRow.getText().length() < 2){
                        if(phoneRow.getText().length() > 50){
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Phone number is too long");
                            alert.setHeaderText(null);
                            alert.setContentText("The phone number can't have more than 50 characters!");
                            alert.showAndWait();
                        }else{
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Phone number is too short");
                            alert.setHeaderText(null);
                            alert.setContentText("The phone number can't have more than 50 characters!");
                            alert.showAndWait();
                        }
                    }else
                        if(allreadyBoorowedBy(nameRow.getText(), titleRow.getText(), identifierRow.getText())){
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Allready borrowed");
                            alert.setHeaderText(null);
                            alert.setContentText("The book ''" + titleRow.getText() + "'' was allready borrowed by " + nameRow.getText() + "!");
                            alert.showAndWait();
                        }else
                            if(getQuantity(identifierRow.getText()) == 0){
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("Book is not available");
                                alert.setHeaderText(null);
                                alert.setContentText("The book ''" + titleRow.getText() + "'' is not available anymore. Please come back later!");
                                alert.showAndWait();
                            }else{
                                SaveBorrower(titleRow.getText(), identifierRow.getText(), dateRow.getText(), nameRow.getText(), addressRow.getText(), phoneRow.getText(), author, releaseDate, publishHouse);
                                UpdateQuantity(identifierRow.getText(), -1);
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Book borrowed");
                                alert.setHeaderText(null);
                                alert.setContentText("The book ''" + titleRow.getText() + "'' was borrowed by " + nameRow.getText() + " on " + dateRow.getText() + "");
                                alert.showAndWait();
                            }
    }
    
}
