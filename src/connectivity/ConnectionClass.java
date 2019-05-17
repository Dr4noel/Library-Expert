/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ghita David Leonard
 */
public class ConnectionClass {
    
    public Connection connection;
    
    public Connection getConnection() throws ClassNotFoundException, SQLException{
        //db name and credentials
        //Pentru a se realiza conexiuniunea la baza de date schimba stringurile de mai jos cu datele specifice
        String dbName = "libraryexpert"; 
        String userName = "root";
        String password = "";
        //driver for java connection
        Class.forName("com.mysql.cj.jdbc.Driver");
        //create our connection string
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName,userName,password);
        
        return connection;
    }
    
}
