/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryexpert;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Ghita David Leonard
 */
public class BorrowersModel {
    
    private final StringProperty name;
    private final StringProperty address;
    private final StringProperty phone;
    private final StringProperty date;    
    private final StringProperty bookTitle;
    private final StringProperty identificator;
    
    public BorrowersModel(String name, String address, String phone, String date, String bookTitle, String identificator){        
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.date = new SimpleStringProperty(date);
        this.bookTitle = new SimpleStringProperty(bookTitle);
        this.identificator = new SimpleStringProperty(identificator);
    }
    
    //Getters
    
    public String GetName(){
        return name.get();
    }
    
    public String GetAddress(){
        return address.get();
    }
    
    public String GetPhone(){
        return phone.get();
    }
    
    public String GetDate(){
        return date.get();
    }
    
    public String GetBookTitle(){
        return bookTitle.get();
    }
    
     public String GetIdentificator(){
        return identificator.get();
    }
    
    //Setters
    
    public void SetName(String name){
        this.name.set(name);
    }
    
    public void SetAddress(String address){
        this.address.set(address);
    }
    
    public void SetPhone(String phone){
        this.phone.set(phone);
    }
    
    public void SetDate(String date){
        this.date.set(date);
    }
    
    public void SetBookTitle(String bookTitle){
        this.bookTitle.set(bookTitle);
    }
    
    public void SetIdenificator(String identificator){
        this.identificator.set(identificator);
    }
     
    //Property Values
    public StringProperty nameProperty(){
        return name;
    }
    
    public StringProperty addressProperty(){
        return address;
    }
    
    public StringProperty phoneProperty(){
        return phone;
    }
    
    public StringProperty dateProperty(){
        return date;
    }
    
    public StringProperty bookTitleProperty(){
        return bookTitle;
    }
    
    public StringProperty identificatorProperty(){
        return identificator;
    }
    
}
