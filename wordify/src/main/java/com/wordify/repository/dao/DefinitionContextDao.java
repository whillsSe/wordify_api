package com.wordify.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.wordify.model.dto.DefinitionContextDto;
import com.wordify.model.dto.DefinitionRequestDto;
import com.wordify.model.dto.QueryDto;
import com.wordify.repository.dao.mapper.DefinitionContextMapper;

public class DefinitionContextDao {
    public DefinitionContextDto getDefinitionContextByQuery(Connection conn,DefinitionRequestDto query)throws SQLException{
        DefinitionContextMapper mapper = new DefinitionContextMapper();
        List<Integer> userIds = query.getIds();
        String type = query.getType();
        if(type != "specifiedUser"){
            userIds.add(query.getLoginUser());
        }
        StringBuilder builder = new StringBuilder("WITH sorted_keys AS (SELECT w.id AS word_id,w.word AS word,p.id AS phonetic_id,p.phonetic AS phonetic,d1.id AS definition_id,d1.user_id AS user_id,ROW_NUMBER() OVER (ORDER BY p.phonetic,w.word) AS row_num FROM (");
        builder.append("SELECT d.id,d.user_id,d.word_id,d.phonetic_id FROM definitions d JOIN collections c ON c.definition_id = d.id AND c.user_id IN(");
        System.out.println(userIds);//1,9,38になってる＝wordもphoneticも入ってしまっている。
        for(int i=0;i<userIds.size();i++){
            builder.append("?,");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("))");//typeによってuserIdsに番号入れる所為を端折ってたからここでエラー吐いてる
        builder.append("AS d1 JOIN words w ON w.id = d1.word_id JOIN phonetics p ON p.id = d1.phonetic_id ) SELECT k_prev.word_id AS prev_word_id,k_prev.word AS prev_word,k_prev.phonetic_id AS prev_phonetic_id,k_prev.phonetic AS prev_phonetic,k_next.word_id AS next_word_id,k_next.word AS next_word,k_next.phonetic_id AS next_phonetic_id,k_next.phonetic AS next_phonetic ,k.definition_id AS definition_id,k.user_id AS user_id FROM sorted_keys k LEFT JOIN sorted_keys k_prev ON k_prev.row_num = k.row_num - 1 LEFT JOIN sorted_keys k_next ON k_next.row_num = k.row_num + 1 WHERE k.word_id = ? AND k.phonetic_id = ?;");
        
        String sql = builder.toString();
        System.out.println(sql);
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            int index = 1;
           for (Integer param : userIds) {
                pstmt.setInt(index, param);
                index++;
            }
            pstmt.setInt(index, query.getWordId());
            index++;
            pstmt.setInt(index, query.getPhoneticId());
            ResultSet rs = pstmt.executeQuery();
            return mapper.map(rs);
        }
    }
}
