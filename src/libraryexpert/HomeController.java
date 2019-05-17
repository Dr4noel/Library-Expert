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
import static libraryexpert.DBFunctions.DeleteByQuantity;
import static libraryexpert.DBFunctions.DeleteEntry;

/**
 * FXML Controller class
 *
 * @author Ghita David Leoanard
 */
public class HomeController implements Initializable {

    @FXML
    private TableView<BooksModel> tableViewMain;
    @FXML
    private TableColumn<BooksModel, String> titleColumn;
    @FXML
    private TableColumn<BooksModel, String> authorColumn;
    @FXML
    private TableColumn<BooksModel, Integer> releaseColumn;
    @FXML
    private TableColumn<BooksModel, String> publishierHouseColumn;   
    @FXML
    private TableColumn<BooksModel, Integer> quantityColumn;
    @FXML
    private TableColumn<BooksModel, String> identifierColumn;
    @FXML
    private TextField searchField;
    /*@FXML
    private Button btnRefresh, btnAdd, btnEdit, btnDelete;*/
    
    private ObservableList<BooksModel> data;
    private ConnectionClass connectionClass;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            DeleteByQuantity();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Creating the connection to the database
        connectionClass  = new ConnectionClass();
        try(Connection connection = connectionClass.getConnection()){
            //Creating the sql command
            String sqlQ = "SELECT * FROM books";
            
            data = FXCollections.observableArrayList();
            //Execute query and store result in a 'ResultSet'
            ResultSet rs = connection.createStatement().executeQuery(sqlQ);
            while(rs.next()){
                //get string from db,wchicheverway
                data.add(new BooksModel(rs.getString(1),rs.getString(2),rs.getInt(3), rs.getString(6), rs.getInt(4),rs.getString(5)));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        //Set cell value factory to table view
        //NB.ProperyValue Factory  must be the same  with the one set in model class
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        releaseColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        publishierHouseColumn.setCellValueFactory(new PropertyValueFactory<>("publishierHouse"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        identifierColumn.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        
        //Fill the table with the existent data
        tableViewMain.setItems(null);
        tableViewMain.setItems(data);
    }
    
    public void OpenAddBookWindow(ActionEvent event) throws IOException{
        
        Parent addBook_parent = FXMLLoader.load(getClass().getResource("/Fxml/AddBookWindow.fxml"));
        
        Scene addBook_scene = new Scene(addBook_parent);
        addBook_scene.getStylesheets().add(getClass().getResource("/StaticFiles/style.css").toExternalForm());
        //We are creating a 'node' between the last view and this view so they can be swapped in the same window
        Stage addBook_stage = new Stage();
        
        addBook_stage.setScene(addBook_scene);
        
        addBook_stage.setTitle("Add book");
        
        addBook_stage.setResizable(false);
        
        addBook_stage.centerOnScreen();
        
        addBook_stage.show();
    } 
    
    public void Refresh(ActionEvent event) throws ClassNotFoundException, SQLException{
        DeleteByQuantity();
        data.clear();
        connectionClass  = new ConnectionClass();
        try(Connection connection = connectionClass.getConnection()){
            //Creating the sql command
            String sqlQ = "SELECT * FROM books";
            
            data = FXCollections.observableArrayList();
            //Execute query and store result in a 'ResultSet'
            ResultSet rs = connection.createStatement().executeQuery(sqlQ);
            while(rs.next()){
                //get string from db,wchicheverway
                data.add(new BooksModel(rs.getString(1),rs.getString(2),rs.getInt(3), rs.getString(6), rs.getInt(4),rs.getString(5)));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        //Set cell value factory to table view
        //NB.ProperyValue Factory  must be the same  with the one set in model class
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        releaseColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        publishierHouseColumn.setCellValueFactory(new PropertyValueFactory<>("publishierHouse"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        identifierColumn.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        
        //Fill the table with the existent data
        tableViewMain.setItems(null);
        tableViewMain.setItems(data);
    }
    
    public void Delete(ActionEvent event) throws ClassNotFoundException, ClassNotFoundException, SQLException{
        String identy = tableViewMain.getSelectionModel().getSelectedItem().GetIndentifier();
        String bookTitle = tableViewMain.getSelectionModel().getSelectedItem().GetTitle();
                
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Delete confirm");
        alert.setHeaderText(null);
        alert.setContentText("You are about to delete ''" + bookTitle + "''. Are you sure you want to do that?");
        Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                DeleteEntry(identy,"null","books");
                tableViewMain.getItems().removeAll(tableViewMain.getSelectionModel().getSelectedItem());
            }
    }

    public void Edit(ActionEvent event) throws IOException{
        String author = tableViewMain.getSelectionModel().getSelectedItem().GetAuthor();
        String title = tableViewMain.getSelectionModel().getSelectedItem().GetTitle();
        String publishHouse = tableViewMain.getSelectionModel().getSelectedItem().GetPublishierHouse();
        int releaseDate = tableViewMain.getSelectionModel().getSelectedItem().GetReleaseDate();
        int quantity = tableViewMain.getSelectionModel().getSelectedItem().GetQuantity();
        String identy = tableViewMain.getSelectionModel().getSelectedItem().GetIndentifier();
        
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/Fxml/EditBookWindow.fxml"));
        try{
            Loader.load();
        }catch(IOException ex){
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,  null, ex);
        }
        EditBookWindowController display = Loader.getController();
        display.setValues(title, author, publishHouse, releaseDate, quantity, identy);
        Parent p = Loader.getRoot();
        Scene scene = new Scene(p);
        scene.getStylesheets().add(getClass().getResource("/StaticFiles/style.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Edit Book");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
    
    public void Borrow(ActionEvent event){
            String author = tableViewMain.getSelectionModel().getSelectedItem().GetAuthor();
            String identy = tableViewMain.getSelectionModel().getSelectedItem().GetIndentifier();
            String title = tableViewMain.getSelectionModel().getSelectedItem().GetTitle();
            int releaseDate = tableViewMain.getSelectionModel().getSelectedItem().GetReleaseDate();
            String publishHouse = tableViewMain.getSelectionModel().getSelectedItem().GetPublishierHouse();
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("/Fxml/BorrowBook.fxml"));
            try{
                Loader.load();
            }catch(IOException ex){
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,  null, ex);
            }
            BorrowBookController display = Loader.getController();
            display.setValues(title, identy, author, releaseDate, publishHouse);
            Parent p = Loader.getRoot();
            Scene scene = new Scene(p);
            scene.getStylesheets().add(getClass().getResource("/StaticFiles/style.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Borrow Book");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
    }
    
    public void Lend(ActionEvent event){
            String identy = tableViewMain.getSelectionModel().getSelectedItem().GetIndentifier();
            String title = tableViewMain.getSelectionModel().getSelectedItem().GetTitle();
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("/Fxml/LendBook.fxml"));
            try{
                Loader.load();
            }catch(IOException ex){
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE,  null, ex);
            }
            LendBookController display = Loader.getController();
            display.setValues(title, identy);
            Parent p = Loader.getRoot();
            Scene scene = new Scene(p);
            scene.getStylesheets().add(getClass().getResource("/StaticFiles/style.css").toExternalForm());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Borrow Book");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
    }
    
    public void Search(ActionEvent event){
        connectionClass  = new ConnectionClass();
        try(Connection connection = connectionClass.getConnection()){
            //Creating the sql command
            String sqlQ = "SELECT * FROM `books` WHERE Title LIKE '%" + searchField.getText() + "%' OR Author LIKE '%" + searchField.getText() + "%' OR Editura LIKE '%" + searchField.getText() + "%' OR ReleaseDate = '" + searchField.getText() + "' OR Quantity = '" + searchField.getText() + "' OR Identifier = '" + searchField.getText() + "'";
            
            data = FXCollections.observableArrayList();
            //Execute query and store result in a 'ResultSet'
            ResultSet rs = connection.createStatement().executeQuery(sqlQ);
            while(rs.next()){
                //get data from db
                data.add(new BooksModel(rs.getString(1),rs.getString(2),rs.getInt(3), rs.getString(6), rs.getInt(4),rs.getString(5)));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        //Set cell value factory to table view
        //NB.ProperyValue Factory  must be the same  with the one set in model class
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        releaseColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        publishierHouseColumn.setCellValueFactory(new PropertyValueFactory<>("publishierHouse"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        identifierColumn.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        
        //Fill the table with the existent data
        tableViewMain.setItems(data);
    }
    
    public void RedirectToBorrowersTable(ActionEvent event) throws IOException{
        //We specifie the path to the view that we want to display
        Parent sign_up_parent = FXMLLoader.load(getClass().getResource("/Fxml/BorrowersTable.fxml"));
                
        Scene sign_up_scene = new Scene(sign_up_parent);
        //We are creating a 'node' between the last view and this view so they can be swapped in the same window
        Stage sign_up_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        sign_up_stage.setScene(sign_up_scene);
        
        sign_up_stage.setTitle("Borrowers Table");
        
        sign_up_stage.centerOnScreen();
        
        sign_up_stage.show();
    }
}
