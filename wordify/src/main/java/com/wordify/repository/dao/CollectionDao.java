package com.wordify.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CollectionDao {
    public void RegisterCollection(Connection conn,int definition_id,int user_id) throws SQLException{
        String sql = "INSERT INTO collections(definition_id,user_id) VALUES(?,?);";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, definition_id);
            pstmt.setInt(2, user_id);
            pstmt.executeUpdate();
        }
    }
}
