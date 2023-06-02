package com.wordify.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.wordify.model.dto.EntryDto;
import com.wordify.model.dto.QueryDto;
import com.wordify.repository.dao.mapper.EntryMapper;

public class EntryDao {
    public List<EntryDto> getEntryByQuery(Connection conn,QueryDto query)throws SQLException{
        EntryMapper mapper = new EntryMapper();
        List<Integer> userIds = query.getUserIds();
        List<Integer> params = query.getUserIds();
            params.add(query.getWordId());
            params.add(query.getPhoneticId());
        StringBuilder builder = new StringBuilder("SELECT w.id AS word_id,w.word AS word,p.id AS phonetic_id,p.phonetic AS phonetic FROM (");
        //ここから共通検索範囲部分
        builder.append("SELECT d.id,d.user_name,d.word_id,d.phonetic_id FROM definitions d JOIN collections c ON c.definition_id = d.id AND c.user_id IN(");
        for(int i=0;i<userIds.size();i++){
            builder.append("?,");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");
        //ここまで共通検索範囲部分
        builder.append("AS d1 JOIN words w ON w.id = d1.word_id JOIN phonetics p ON p.id = d1.phonetic_id WHERE w.word LIKE ?");
        String sql = builder.toString();
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            int index = 1;
            for (Integer param : params) {
                pstmt.setInt(index, param);
                index++;
            }
            pstmt.setString(index,query.getWord()+"%");
            ResultSet rs = pstmt.executeQuery();
            return mapper.mapToList(rs);
        }
    }
}
