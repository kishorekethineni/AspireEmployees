package com.aspiresys.AspireEmployees;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {


    private static DBConnection instance;
    /*
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String login;
    @Value("${spring.datasource.password}")
    private String pass;
    */
    private final String connectionUrl = "jdbc:mysql://mysqljava.cokejsmbaxif.us-east-2.rds.amazonaws.com/Employees?useUnicode=true&characterEncoding=UTF-8&user=admin&password=8247745215";


    private DBConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
            System.out.println(" Connection  - - - - - - - -  New DBConnection created");
        }
        try {
            return DriverManager.getConnection(instance.connectionUrl);
        } catch (SQLException e) {
            throw e;
        }
    }

    public static void close(Connection connection)
    {
        try {
            if (connection != null) {
                connection.close();
                connection=null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
