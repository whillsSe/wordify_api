package com.wordify.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class _DaoBase {
    public int getId(Connection conn , String element) throws SQLException{
        int id ;
        StringBuilder builder = new StringBuilder("SELECT id FROM ");
        builder.append(getTableName()).append(" WHERE ").append(getColumnName()).append(" = ?");
        String sql = builder.toString();
        System.out.println("DaoBaseで実行するsqlはコレや！" + sql);
        PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, element);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
               id = rs.getInt(1);
            }else{
                StringBuilder builder2 = new StringBuilder("INSERT INTO ");
                builder2.append(getTableName()).append("(").append(getColumnName()).append(") VALUES (?);");
                String sql2 = builder2.toString();
                System.out.println(sql2);
                PreparedStatement pstmt2 = conn.prepareStatement(sql2,PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt2.setString(1, element);
                pstmt2.executeUpdate();
                ResultSet generatedKeys = pstmt2.getGeneratedKeys();
                if(generatedKeys.next()){
                    id = generatedKeys.getInt(1);
                }else{
                    throw new SQLException();
                }
                pstmt2.close();
            }
            pstmt.close();
            System.out.println(id);
            return id;
    }
    protected abstract String getTableName();
    protected abstract String getColumnName();
}
