/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryexpert;

import connectivity.ConnectionClass;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static libraryexpert.DBFunctions.AddBook;
import static libraryexpert.DBFunctions.BookExists;
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
public class BorrowersTableController implements Initializable {
    
    @FXML
    private TableView<BorrowersModel> tableViewBorrowers;
    @FXML
    private TableColumn<BorrowersModel, String> nameColumn;
    @FXML
    private TableColumn<BorrowersModel, String> addressColumn;
    @FXML
    private TableColumn<BorrowersModel, String> phoneColumn;
    @FXML
    private TableColumn<BorrowersModel, String> dateColumn;   
    @FXML
    private TableColumn<BorrowersModel, String> bookTitleColumn;
    @FXML
    private TableColumn<BorrowersModel, String> identificatorColumn;
    @FXML
    private TextField searchField;
    
    private ObservableList<BorrowersModel> borrowers;
    private ConnectionClass connectionClass;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Creating the connection to the database
        connectionClass  = new ConnectionClass();
        try(Connection connection = connectionClass.getConnection()){
            //Creating the sql command
            String sqlQ = "SELECT * FROM `borrowedbooks`";
            
            borrowers = FXCollections.observableArrayList();
            //Execute query and store result in a 'ResultSet'
            ResultSet rs = connection.createStatement().executeQuery(sqlQ);
            while(rs.next()){
                //get string from db,wchicheverway
                borrowers.add(new BorrowersModel(rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7)));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        //Set cell value factory to table view
        //NB.ProperyValue Factory  must be the same  with the one set in model class
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        identificatorColumn.setCellValueFactory(new PropertyValueFactory<>("identificator"));
        
        //Fill the table with the existent data
        tableViewBorrowers.setItems(null);
        tableViewBorrowers.setItems(borrowers);
    }   
    
    //A method that get's us back to HomePage
    public void RedirectToHome(ActionEvent event) throws IOException{
        //We specifie the path to the view that we want to display
        Parent sign_up_parent = FXMLLoader.load(getClass().getResource("/Fxml/Home.fxml"));
                
        Scene sign_up_scene = new Scene(sign_up_parent);
        //We are creating a 'node' between the last view and this view so they can be swapped in the same window
        Stage sign_up_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        sign_up_stage.setScene(sign_up_scene);
        
        sign_up_stage.setTitle("Home");
        
        sign_up_stage.centerOnScreen();
        
        sign_up_stage.show();
    }
    
    public void Search(ActionEvent event){
        //Get the connection and try to establish it
        connectionClass  = new ConnectionClass();
        try(Connection connection = connectionClass.getConnection()){
            //Creating the sql command
            String sqlQ = "SELECT * FROM `borrowedbooks` WHERE Name LIKE '%" + searchField.getText() + "%'";
            
            borrowers = FXCollections.observableArrayList();
            //Execute query and store result in a 'ResultSet'
            ResultSet rs = connection.createStatement().executeQuery(sqlQ);
            while(rs.next()){
                //get data from db
                borrowers.add(new BorrowersModel(rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6),rs.getString(7)));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        //Set cell value factory to table view
        //NB.ProperyValue Factory  must be the same  with the one set in model class
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        identificatorColumn.setCellValueFactory(new PropertyValueFactory<>("identificator"));
        
        //Fill the table with the existent data
        tableViewBorrowers.setItems(borrowers);
    }

    public void Lend(ActionEvent event) throws ClassNotFoundException, SQLException{
        //Get data from the selected row
        String identy = tableViewBorrowers.getSelectionModel().getSelectedItem().GetIdentificator();
        String bookTitle = tableViewBorrowers.getSelectionModel().getSelectedItem().GetBookTitle();
        String name = tableViewBorrowers.getSelectionModel().getSelectedItem().GetName();
        
        //Notify the user what he is about to do
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Lend confirm");
        alert.setHeaderText(null);
        alert.setContentText("You are about to LEND ''" + bookTitle + "'', borrower by " + name + " . Are you sure you want to do that?");
        Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                //If user choses to do the action we verify if his book is still existent in our `book` table, if it is we delete it's entry from `borrowedbooks` and increment it's quantity value from `books` with 1
                if(BookExists(bookTitle, GetAuthor(identy), GetPublishierHouse(identy), GetReleaseDate(identy))){
                    UpdateQuantity(identy, 1);
                    DeleteEntry(identy,name,"borrowedbooks");
                    tableViewBorrowers.getItems().removeAll(tableViewBorrowers.getSelectionModel().getSelectedItem());
                }
                //If the book does't exists in our `book` table we delete add it with quantity 1 and delete it;s entry from `borrowedbooks`
                else{
                    AddBook(bookTitle, GetAuthor(identy), GetPublishierHouse(identy), GetReleaseDate(identy), 1, identy);
                    DeleteEntry(identy,name,"borrowedbooks");
                    tableViewBorrowers.getItems().removeAll(tableViewBorrowers.getSelectionModel().getSelectedItem());
                }
            }else
                if(result.get() == ButtonType.CANCEL){
                    //Nothing
                }
        
    }
}
