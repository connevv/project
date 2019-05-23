package com.sap.database;

import com.sap.data.User;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private  static DatabaseHandler handler = null;
    private final static String URL="jdbc:mysql://localhost:3306/library_dp";
    private final static String USER="root";
    private final static String PASS="jovanconev123";
    private static Statement statement;

    public static DatabaseHandler getInstance() throws Exception{
        if(handler == null){
            handler = new DatabaseHandler();
        }
        return handler;
    }
    private DatabaseHandler() {
        try {
             statement = connectWithDatabase();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

        private static Statement connectWithDatabase() throws SQLException {
            Connection connection = DriverManager.getConnection(URL, USER, PASS);
            statement = connection.createStatement();
            return statement;
    }

    public static  boolean executeAction (String query){
        try {
            statement = connectWithDatabase();
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static  ResultSet executeQuery (String query){
        ResultSet resultSet;
        try {
            statement = connectWithDatabase();
            resultSet = statement.executeQuery(query);

        } catch (SQLException e) {
            System.out.println("Exception at executing query " + e.getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
        return resultSet;
    }

}
