package com.wipro.bank.util;

import java.sql.*;


public class DBUtil {
        
        public static Connection getDBConnection() throws SQLException{
                Connection con = null;
                try {
                        Class.forName("com.mysql.jdbc.Driver");
                        con = DriverManager.getConnection
                        ("jdbc:mysql://localhost:3306/demo","root","");
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } 
                
                return con;
        }


}
