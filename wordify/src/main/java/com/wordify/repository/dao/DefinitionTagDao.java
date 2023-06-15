package com.wordify.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wordify.model.dto.DefinitionTagDto;
import com.wordify.model.dto.TagDto;
import com.wordify.repository.dao.mapper.TagsMapperAsDefinitionElement;

public class DefinitionTagDao {
     public Map<Integer, List<TagDto>> getElementMapByDefinitionIds(Connection conn, Set<Integer> definitionIds) throws SQLException {
        TagsMapperAsDefinitionElement mapper = new TagsMapperAsDefinitionElement();
        StringBuilder builder = new StringBuilder("SELECT dt.definition_id, t.tag,t.id AS id FROM definitions_tags dt JOIN tags t ON dt.tag_id = t.id WHERE dt.definition_id IN ("); // カラム名を追加する
        for (int i = 0; i < definitionIds.size(); i++) {
            builder.append("?,");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(");");
        String sql = builder.toString();
        System.out.println(sql);
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int index = 1;
            for (Integer param : definitionIds) {
                pstmt.setInt(index, param);
                index++;
            }
            ResultSet rs = pstmt.executeQuery();
            return mapper.mapToMap(rs);
        }
    }
    public void RegisterElement(Connection conn,int definition_id,List<Integer> ids) throws SQLException{
        StringBuilder insertBuilder = new StringBuilder("INSERT INTO definitions_tags(definition_id,tag_id) VALUES");
        try(PreparedStatement insertStatement = conn.prepareStatement(buildQuery(insertBuilder,ids))){
            setParamsFromList(insertStatement,definition_id,ids);
            insertStatement.executeUpdate();
        }
    }
    private String buildQuery(StringBuilder builder,List<Integer> ids){
        for(int i=0;i<ids.size();i++){
            builder.append("(?,?),");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
    private void setParamsFromList(PreparedStatement statement,int definition_id,List<Integer> params) throws SQLException{
        int i = 1;
        for(Integer param : params){
            statement.setInt(i, definition_id);
            i++;
            statement.setInt(i,param);
            i++;
        }
    }
}
