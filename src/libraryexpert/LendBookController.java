/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryexpert;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import static libraryexpert.DBFunctions.AddBook;
import static libraryexpert.DBFunctions.BookExists;
import static libraryexpert.DBFunctions.BorrowerExists;
import static libraryexpert.DBFunctions.DeleteEntry;
import static libraryexpert.DBFunctions.GetAuthor;
import static libraryexpert.DBFunctions.GetPublishierHouse;
import static libraryexpert.DBFunctions.GetReleaseDate;
import static libraryexpert.DBFunctions.UpdateQuantity;

/**
 * FXML Controller class
 *
 * @author Ghita David Leonard
 */
public class LendBookController implements Initializable {

    @FXML
    private TextField nameRow;
    @FXML
    private TextField titleRow;
    @FXML
    private TextField identifierRow;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    //We get this values from homeController
    public void setValues(String title, String identy){
        this.titleRow.setText(title);
        this.identifierRow.setText(identy);
    }
    
    public void LendBook(ActionEvent event) throws ClassNotFoundException, SQLException{
        //Check if the user exists and have the specified book before he can lend us that book
        if(BorrowerExists(nameRow.getText(), identifierRow.getText())){
            //If the user exists and he has the book, we just update the quantity
            UpdateQuantity(identifierRow.getText(), 1);
            DeleteEntry(identifierRow.getText(), nameRow.getText(),"borrowedbooks");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("The book was lendded");
            alert.setHeaderText(null);
            alert.setContentText("The book ''" + titleRow.getText() + "'' was lendded by " + nameRow.getText() + ".");
            alert.showAndWait();
            
        }else{
            //If the user doesn't not have the specified book we tell that to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Borrower doesn't exists");
            alert.setHeaderText(null);
            alert.setContentText("This borrower doesn't have this book, please insert another name.");
            alert.showAndWait();
        }
    }
    
}
