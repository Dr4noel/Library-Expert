/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryexpert;

import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import static libraryexpert.DBFunctions.AddBook;
import static libraryexpert.DBFunctions.BookExists;
import static libraryexpert.DBFunctions.IdentifierExists;
import static libraryexpert.DBFunctions.UpdateQuantity;

/**
 * FXML Controller class
 *
 * @author Ghita David Leonard
 */
public class AddBookWindowController implements Initializable {

    @FXML
    private TextField titleRow;
    @FXML
    private TextField authorRow;
    @FXML
    private TextField publishierHouseRow;
    @FXML
    private Spinner<Integer> releaseDateSpinner;
    @FXML
    private Spinner<Integer> quantitySpinner;
    @FXML
    private TextField indentifierRow;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         final int initialDate = 1700;
         final int initialQuantity = 1;
        // Initialize spinners values
                 //release date
        SpinnerValueFactory<Integer> releaseDateFac = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1200, 2020, initialDate);
 
        releaseDateSpinner.setValueFactory(releaseDateFac);
        
                //Quantity
        SpinnerValueFactory<Integer> quantityFac = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5000, initialQuantity);
 
        quantitySpinner.setValueFactory(quantityFac);
    }
 
    public void AddBookBtn(ActionEvent eve) throws SQLException, ClassNotFoundException {       
        //We verify if filds have values if not we specifie that to the user
        if(titleRow.getText().length() == 0 && authorRow.getText().length() == 0 && publishierHouseRow.getText().length() == 0 && indentifierRow.getText().length() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fields are empty");
            alert.setHeaderText(null);
            alert.setContentText("Fields are empty please fill them!");
            alert.showAndWait();
        }else
            //We check the lenght of each field
            if(titleRow.getText().length() > 50 || titleRow.getText().length() < 2){
                if(titleRow.getText().length() > 50){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Title is too long");
                    alert.setHeaderText(null);
                    alert.setContentText("The title field can't have more than 50 characters.");
                    alert.showAndWait();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Title is too short");
                    alert.setHeaderText(null);
                    alert.setContentText("The title field can't have less than 2 characters.");
                    alert.showAndWait();
                }
            }else
                if(authorRow.getText().length() > 50 || authorRow.getText().length() < 2){
                    if(authorRow.getText().length() > 50){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Author name is too long");
                        alert.setHeaderText(null);
                        alert.setContentText("The author field can't have more than 50 characters.");
                        alert.showAndWait();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Author name is too short");
                        alert.setHeaderText(null);
                        alert.setContentText("The author field can't have less than 2 characters.");
                        alert.showAndWait();
                    }
                }else
                    if(publishierHouseRow.getText().length() > 50 || publishierHouseRow.getText().length() < 2){
                       if(publishierHouseRow.getText().length() > 50){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Publishier House name is too long");
                            alert.setHeaderText(null);
                            alert.setContentText("The publishier house field can't have more than 50 characters.");
                            alert.showAndWait();
                       }else{
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Publishier House name is too short");
                            alert.setHeaderText(null);
                            alert.setContentText("The publishier house field can't have less than 2 characters.");
                            alert.showAndWait();
                       }
                    }else
                        if(indentifierRow.getText().length() == 0){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Identifier is missing");
                            alert.setHeaderText(null);
                            alert.setContentText("The identifier can't be empty, please generate one!");
                            alert.showAndWait();
                        }else
                            //We check if the book exists allready, if it is true it's quantity will be increased with the new one
                            if(BookExists(titleRow.getText(), authorRow.getText(), publishierHouseRow.getText(), (Integer) releaseDateSpinner.getValue())){
                                UpdateQuantity(indentifierRow.getText(), (Integer) quantitySpinner.getValue());
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Book Exists");
                                alert.setHeaderText(null);
                                alert.setContentText("The book that you tried to save, allready exists, so we adeed it next to the old ones.");
                                alert.showAndWait();
                            }else{
                                if(IdentifierExists(indentifierRow.getText())){
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Identifier Exists");
                                    alert.setHeaderText(null);
                                    alert.setContentText("The ''" +indentifierRow.getText()+ "'' allready exists, please generate a new one!");
                                    alert.showAndWait();
                                }
                                else{
                                //We add the book to the database
                                AddBook(titleRow.getText(), authorRow.getText(), publishierHouseRow.getText(), (Integer) releaseDateSpinner.getValue(), (Integer) quantitySpinner.getValue(), indentifierRow.getText());
                        
                                //We notify the user that the book was added
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Book added");
                                alert.setHeaderText(null);
                                alert.setContentText("The ''" +titleRow.getText()+ "'' book, writted by " +authorRow.getText()+ " was added with succes!");
                                alert.showAndWait();
                                }
                            }
    }
    
    public void generateIdentifier(ActionEvent event) {
        int leftLimit = 48; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 15;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) 
            (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        indentifierRow.setText(generatedString);
    }
}
