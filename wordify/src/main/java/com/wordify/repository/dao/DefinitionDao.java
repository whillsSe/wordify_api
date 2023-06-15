package com.wordify.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DefinitionDao {
    public int createDefinition(Connection conn,int wId,int pId,int uId) throws SQLException{
        int id;
        String sql = "INSERT INTO definitions(word_id,phonetic_id,user_id) VALUES(?,?,?);";
        try(PreparedStatement pstmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)){
            pstmt.setInt(1, wId);
            pstmt.setInt(2, pId);
            pstmt.setInt(3, uId);
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if(keys.next()){
                id = keys.getInt(1);
            }else{
                throw new SQLException();
            }
        }
        return id;
    }
}
