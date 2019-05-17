/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryexpert;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Ghita David Leonard
 */
public class BooksModel {
    
    private final StringProperty title;
    private final StringProperty author;
    private final IntegerProperty releaseDate;
    private final StringProperty publishierHouse;    
    private final IntegerProperty quantity;
    private final StringProperty identifier;
    
    //Default Constructor
    
    public BooksModel(String title, String author, int releaseDate, String publishierHouse, int quantity, String identifier){        
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.releaseDate = new SimpleIntegerProperty(releaseDate);
        this.publishierHouse = new SimpleStringProperty(publishierHouse);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.identifier = new SimpleStringProperty(identifier);
    }
    
    //Getters
    
    public String GetTitle(){
        return title.get();
    }
    
    public String GetAuthor(){
        return author.get();
    }
    
    public Integer GetReleaseDate(){
        return releaseDate.get();
    }
    
    public String GetPublishierHouse(){
        return publishierHouse.get();
    }
    
    public Integer GetQuantity(){
        return quantity.get();
    }
    
     public String GetIndentifier(){
        return identifier.get();
    }
    
    //Setters
    
    public void SetTitle(String title){
        this.title.set(title);
    }
    
    public void SetAuthor(String author){
        this.author.set(author);
    }
    
    public void SetReleaseDate(int releaseDate){
        this.releaseDate.set(releaseDate);
    }
    
    public void SetPublishierHouse(String publishierHouse){
        this.publishierHouse.set(publishierHouse);
    }
    
    public void SetQuantity(int quantity){
        this.quantity.set(quantity);
    }
    
    public void SetIdentifier(String identifier){
        this.identifier.set(identifier);
    }
   
    //Property Values
    public StringProperty titleProperty(){
        return title;
    }
    
    public StringProperty authorProperty(){
        return author;
    }
    
    public IntegerProperty releaseDateProperty(){
        return releaseDate;
    }
    
    public StringProperty publishierHouseProperty(){
        return publishierHouse;
    }
    
    public IntegerProperty quantityProperty(){
        return quantity;
    }
    
    public StringProperty identifierProperty(){
        return identifier;
    } 
}
