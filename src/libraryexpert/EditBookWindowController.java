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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import static libraryexpert.DBFunctions.EditEntry;

/**
 * FXML Controller class
 *
 * @author Ghita David Leonard
 */
public class EditBookWindowController implements Initializable {

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
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setValues(String title, String author, String publishierHouse, int releaseDate, int quantity, String identifier){
        this.titleRow.setText(title);
        this.authorRow.setText(author);
        this.publishierHouseRow.setText(publishierHouse);
        SpinnerValueFactory<Integer> releaseDateFac = new SpinnerValueFactory.IntegerSpinnerValueFactory(1200, 2020,releaseDate);
        this.releaseDateSpinner.setValueFactory(releaseDateFac);
        SpinnerValueFactory<Integer> quantityFac = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5000, quantity);
        this.quantitySpinner.setValueFactory(quantityFac);
        this.indentifierRow.setText(identifier);        
    }
    
    public void EditBtn(ActionEvent event) throws SQLException, ClassNotFoundException{
        //We call the EditEntry to update our data from database
        EditEntry(titleRow.getText(), authorRow.getText(), publishierHouseRow.getText(),(Integer) releaseDateSpinner.getValue(), (Integer) quantitySpinner.getValue(), indentifierRow.getText());
        
        //Creates an alert to specifie the user what happend
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Edit Book");
        alert.setHeaderText(null);
        alert.setContentText("Your edit was done with succes!");
        alert.showAndWait();
    }
    
}
