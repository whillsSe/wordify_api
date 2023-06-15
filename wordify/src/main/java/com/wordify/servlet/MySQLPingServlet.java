package com.wordify.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/pingMySQL")
public class MySQLPingServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.init();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dbUrl = "jdbc:mysql://localhost:3306/dict"; // Replace with your MySQL DB URL
        //String dbUser = "root";// Replace with your MySQL DB username
        //String dbPassword = "password"; // Replace with your MySQL DB password
        setAccessControlHeaders(response);
        String param = "?user=dict_web&password=dict_webpass";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try (
            Connection connection = DriverManager.getConnection(dbUrl+param);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 1")) {
             PrintWriter out = response.getWriter();
            if (resultSet.next()) {
                out.println("Ping to MySQL server successful!");
            } else {
                out.println("Ping to MySQL server failed!");
            }

        } catch (SQLException e) {
            throw new ServletException("SQL error", e);
        }
    }
    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:8000"); // ここで許可するオリジンを指定します
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT"); // 許可するHTTPメソッドを指定します
        resp.setHeader("Access-Control-Max-Age", "3600"); // プリフライトレスポンスをキャッシュする時間を秒単位で指定します // 許可するリクエストヘッダーを指定します
    }
}
