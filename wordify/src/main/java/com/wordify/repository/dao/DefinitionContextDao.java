package com.wordify.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.wordify.model.dto.DefinitionContextDto;
import com.wordify.model.dto.QueryDto;
import com.wordify.repository.dao.mapper.DefinitionContextMapper;

public class DefinitionContextDao {
    public DefinitionContextDto getDefinitionContextByQuery(Connection conn,QueryDto query)throws SQLException{
        DefinitionContextMapper mapper = new DefinitionContextMapper();
        List<Integer> userIds = query.getUserIds();
        List<Integer> params = query.getUserIds();
            params.add(query.getWordId());
            params.add(query.getPhoneticId());
        StringBuilder builder = new StringBuilder("WITH sorted_keys AS (SELECT w.id AS word_id,w.word AS word,p.id AS phonetic_id,p.phonetic AS phonetic,ROW_NUMBER() OVER (ORDER BY p.phonetic,w.word) AS row_num FROM (");
        builder.append("SELECT d.id,d.user_name,d.word_id,d.phonetic_id FROM definitions d JOIN collections c ON c.definition_id = d.id AND c.user_id IN(");
        for(int i=0;i<userIds.size();i++){
            builder.append("?,");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");
        builder.append("AS d1 JOIN words w ON w.id = d1.word_id JOIN phonetics p ON p.id = d1.phonetic_id ) SELECT k_prev.word_id AS prev_word_id,k_prev.word AS prev_word,k_prev.phonetic_id AS prev_phonetic_id,k_prev.phonetic AS prev_phonetic,k_next.word_id AS next_word_id,k_next.word AS next_word,k_next.phonetic_id AS next_phonetic_id,k_next.phonetic AS next_phonetic FROM sorted_keys k LEFT JOIN sorted_keys k_prev ON k_prev.row_num = k.row_num - 1 LEFT JOIN sorted_keys k_next ON k_next.row_num = k.row_num + 1 WHERE k.word_id = ? AND k.phonetic_id = ?;");
        
        String sql = builder.toString();
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            int index = 1;
            for (Integer param : params) {
                pstmt.setInt(index, param);
                index++;
            }
            ResultSet rs = pstmt.executeQuery();
            return mapper.map(rs);
        }
    }
}
