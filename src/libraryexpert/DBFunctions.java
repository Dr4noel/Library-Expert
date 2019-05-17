/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryexpert;

import static MD5Hash.MD5.getHash;
import connectivity.ConnectionClass;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Ghita David Leoanard
 */
public class DBFunctions {
   
    //We use this object to get the connection string to our database
    private static ConnectionClass connectionClass = new ConnectionClass();
    
    //Check if username is taked
    public static boolean UsernameTaken(String username) throws ClassNotFoundException, SQLException{
        
        //We try to establish our connection and at the end of the 'try' the 'connection' object will be destroyed
        try(Connection connection = connectionClass.getConnection()){
            
            //Command to get the rows that have the specified username
            String sqlQ = "SELECT * FROM `Users` WHERE Username = '" + username + "'";
            
            //We are creating the command
            Statement statement = connection.createStatement();
            
            //We save our command result in a 'ResultSet' to search for a potentially existent username
            ResultSet rs = statement.executeQuery(sqlQ);
            
            if(rs.next()){
                //If the username is taken we return true
                return true;
            }else{
                //If the username is free we return false
                return false;
            }
            
        }
    }
    
    //Check if the credentials are existent also we also throw the potential errors
    public static boolean VerifyCredentials(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException{
        
        //We try to establish our connection and at the end of the 'try' the 'connection' object will be destroyed
        try (Connection connection = connectionClass.getConnection()){
            
            //Command to get the rows that have the specified username and password
            String sqlQ = "SELECT * FROM `Users` WHERE Username = '" + username + "' AND Password = '" + getHash(password) + "'";
            
            //We are creating the command to be applied to the specified connection
            Statement statement = connection.createStatement();
            
            //We save our command result in a 'ResultSet' to search for a potentially existent account
            ResultSet rs = statement.executeQuery(sqlQ);
            
            if(rs.next()){
                //If the account exists we return true
                return true;
            }else{
                //If the account doesn't exists we return false
                return false;
            }
        }
    }
    
    //With this method we are adding new Entrys
    public static void AddBook(String title, String author, String publishierHouse, int releaseDate, int quantity, String identifier) throws ClassNotFoundException, SQLException{
        
        //We try to establish our connection and at the end of the 'try' the 'connection' object will be destroyed
        try(Connection connection = connectionClass.getConnection()){
            
            //SqlCommand to isert in our database a new book
            String sqlQ = "INSERT INTO `books` VALUES('" + title + "', '" + author + "', '" + releaseDate + "', '" + quantity + "', '" + identifier + "', '" + publishierHouse + "')";
            //We are creating our command
            Statement statement = connection.createStatement();
            //We are executing our command
            statement.executeUpdate(sqlQ);
        }
    }

    //With this method we are deleting entrys form database using the "identifier"
    public static void DeleteEntry(String identifier, String name ,String table) throws ClassNotFoundException, SQLException{
        String sqlQ = null;
        //We are creating the sql delete command
        if(table == "books"){
            sqlQ = "DELETE FROM `books` WHERE Identifier = '" + identifier + "'";
        }else
            if(table == "borrowedbooks"){
            sqlQ = "DELETE FROM `borrowedbooks` WHERE Identificator = '" + identifier + "' AND Name = '"+ name +"'";
            }
        //We try to establish our connection and at the of try the 'connection' object will be destroyed
        try(Connection connection = connectionClass.getConnection()){
            //We are creating our command
            Statement statement = connection.createStatement();
            //We are executing our command
            statement.executeUpdate(sqlQ);
        }
        
        
    }
    
    //Edit a table enrty
    public static void EditEntry(String title, String author, String publishierHouse, int releaseDate, int quantity, String identifier) throws SQLException, ClassNotFoundException{
        //WE are creating the sql command
        String sqlQ = "UPDATE `books` SET Title = '" + title + "', Author = '" + author +  "', Editura = '" + publishierHouse + "', ReleaseDate = '" + releaseDate + "', Quantity = '" + quantity + "' WHERE Identifier = '" + identifier + "'";
        //We try to establish our connection and at the of try the 'connection' object will be destroyed
        try(Connection connection = connectionClass.getConnection()){
            //We are creating our command
            Statement statement = connection.createStatement();
            //We are executing our command
            statement.executeUpdate(sqlQ);
        }
    }
    
    //Check if a book exists
    public static boolean BookExists(String title, String author, String publishierHouse, int releaseDate) throws ClassNotFoundException, SQLException{
        //We are creating out command
        String sqlQ = "SELECT * FROM `books` WHERE Title = '" + title + "' AND Author = '" + author + "' AND Editura = '" + publishierHouse + "' AND ReleaseDate = '" + releaseDate + "'";
        //We try to establish our connection and at the of try the 'connection' object will be destroyed
        try(Connection connection = connectionClass.getConnection()){
            //We are creating our command
            Statement statement = connection.createStatement();
            //We are executing our command
            ResultSet rs = statement.executeQuery(sqlQ);
            if(rs.next()){
                //If the book exists we return true
                return true;
            }else{
                //If not we return false
                return false;
            }
        }
    }
    
    public static boolean IdentifierExists(String identifier) throws ClassNotFoundException, SQLException{
         //We are creating out command
        String sqlQ = "SELECT Identifier FROM `books` WHERE Identifier = '" + identifier + "'";
        //We try to establish our connection and at the of try the 'connection' object will be destroyed
        try(Connection connection = connectionClass.getConnection()){
            //We are creating our command
            Statement statement = connection.createStatement();
            //We are executing our command
            ResultSet rs = statement.executeQuery(sqlQ);
            if(rs.next()){
                //If the book exists we return true
                return true;
            }else{
                //If not we return false
                return false;
            }
        }
    }
    
    //Check if BorrowerExists
    public static boolean BorrowerExists(String name, String identifier) throws ClassNotFoundException, SQLException{
        //We create our command
        String sqlQ = "SELECT Name FROM `borrowedbooks` WHERE Name = '" + name + "' AND Identificator = '" + identifier + "'";
        //We try to establish our connection
        try(Connection connection = connectionClass.getConnection()){
            //We create out statement
            Statement statement = connection.createStatement();
            //Execute the query
            ResultSet rs = statement.executeQuery(sqlQ);
            
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        }
    }
    
    //update the quantity of books
    public static void UpdateQuantity(String identifier, int quantity) throws ClassNotFoundException, SQLException{
        //We are creating out command
        String sqlQ1 = "SELECT Quantity FROM `books` WHERE Identifier = '" + identifier + "'";
        //We try to establish our connection and at the of try the 'connection' object will be destroyed
        try(Connection connection = connectionClass.getConnection()){
            //We are creating our command
            Statement statement = connection.createStatement();
            //We are executing our command
            ResultSet rs = statement.executeQuery(sqlQ1);
            //We get the quantity value
            rs.next();
            int total = (Integer) rs.getInt(1) + quantity;
            //We are creating a new command
            String sqlQ2 = "UPDATE `books` SET Quantity = '" + total + "' WHERE Identifier = '" + identifier + "'";
            //We create it and execute it
            statement.executeUpdate(sqlQ2);
        }
    }
    
    //Save borower data
    public static void SaveBorrower(String title, String identifier, String date,String name, String address, String phone, String author, int releaseDate, String publishierHouse) throws ClassNotFoundException, SQLException{
        //We create our command
        String sqlQ = "INSERT INTO `borrowedBooks`(`Name`, `Address`, `Phone`, `Date`, `BookTitle`, `Identificator`, `Author`, `ReleaseDate`, `PublishierHouse`) VALUES ('" + name + "', '" + address + "', '" + phone + "', '" + date + "', '" + title + "', '" + identifier + "', '" + author + "', " + releaseDate + ", '" + publishierHouse + "')";
        //We try to establish our connection
        try(Connection connection = connectionClass.getConnection()){
            //We create out statement
            Statement statement = connection.createStatement();
            //Execute the query
            statement.executeUpdate(sqlQ);
        }
    }
    
    //gets the quantity of books 
    public static int getQuantity(String identifier) throws ClassNotFoundException, SQLException{
        //Create our command
        String sql = "SELECT Quantity FROM `books` WHERE Identifier = '" + identifier + "'";
        //We try to establish our connection
        try(Connection connection = connectionClass.getConnection()){
            //Create the statement
            Statement statement = connection.createStatement();
            //Save quantity value and return it
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            
            return rs.getInt(1);
        }
    }
    
    //delete the entrys that have quantity 0
    public static void DeleteByQuantity() throws ClassNotFoundException, SQLException{
        String sqlQ = "DELETE FROM `books` WHERE Quantity = 0";
        try(Connection connection = connectionClass.getConnection()){
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQ);
        }
    }
    
    //check if the book was alleready borrowed by the same person
    public static boolean allreadyBoorowedBy(String name, String title, String identifier) throws ClassNotFoundException, SQLException{
        String sqlQ = "SELECT * FROM `borrowedBooks` WHERE Name = '" + name + "' AND BookTitle = '" + title + "' AND Identificator = '" + identifier + "'";
    
        try(Connection connection = connectionClass.getConnection()){
            Statement statement = connection.createStatement();
            
            ResultSet rs = statement.executeQuery(sqlQ);
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        }
    }
    
    public static String GetAuthor(String identifier) throws SQLException, ClassNotFoundException{
        //Create our command
        String sql = "SELECT Author FROM `borrowedBooks` WHERE Identificator = '" + identifier + "'";
        //We try to establish our connection
        try(Connection connection = connectionClass.getConnection()){
            //Create the statement
            Statement statement = connection.createStatement();
            //Save quantity value and return it
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            
            return rs.getString(1);
        }
    }
    
    public static String GetPublishierHouse(String identifier) throws SQLException, ClassNotFoundException{
        //Create our command
        String sql = "SELECT PublishierHouse FROM `borrowedBooks` WHERE Identificator = '" + identifier + "'";
        //We try to establish our connection
        try(Connection connection = connectionClass.getConnection()){
            //Create the statement
            Statement statement = connection.createStatement();
            //Save quantity value and return it
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            
            return rs.getString(1);
        }
    }
    
    public static int GetReleaseDate(String identifier) throws SQLException, ClassNotFoundException{
        //Create our command
        String sql = "SELECT ReleaseDate FROM `borrowedBooks` WHERE Identificator = '" + identifier + "'";
        //We try to establish our connection
        try(Connection connection = connectionClass.getConnection()){
            //Create the statement
            Statement statement = connection.createStatement();
            //Save quantity value and return it
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            
            return rs.getInt(1);
        }
    }
    
}
