package com.wordify.repository;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection implements AutoCloseable {
    private static DatabaseConnection instance;
    private Connection connection;
    private String host;
    private String port;
    private String dbName;
    private String user;
    private String password;
    
    private DatabaseConnection() {
        Properties prop = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream input = classLoader.getResourceAsStream("config.properties")) {
            prop.load(input);
            this.host = prop.getProperty("db.host");
            this.port = prop.getProperty("db.port");
            this.dbName = prop.getProperty("db.name");
            this.user = prop.getProperty("db.user");
            this.password = prop.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection(){
        if (connection == null) {
            String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    
    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
