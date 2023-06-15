package com.wordify.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.PreparableStatement;
import com.mysql.cj.xdevapi.SelectStatement;
import com.wordify.model.dto.TagDto;
import com.wordify.repository.dao.mapper.TagsMapper;

public class TagDao extends _DaoBase {

    public List<TagDto> retribeTags(Connection conn,List<String> tags) throws SQLException{
        List<TagDto> tagDtos;
        TagsMapper mapper = new TagsMapper();
        StringBuilder insertBuilder = new StringBuilder("INSERT IGNORE INTO tags (tag) VALUES ");
        StringBuilder selectBuilder = new StringBuilder("SELECT id,tag FROM tags WHERE tag IN (");
        try(PreparedStatement insertStatement = conn.prepareStatement(buildQueryInsert(insertBuilder,tags));            PreparedStatement selectStatement = conn.prepareStatement(buildQuery(selectBuilder, tags));){
            setParamsFromList(insertStatement, tags);
            insertStatement.executeUpdate();
            setParamsFromList(selectStatement, tags);
            ResultSet rs = selectStatement.executeQuery();
            tagDtos =  mapper.mapToList(rs);
        }
        return tagDtos;
    }
    private String buildQuery(StringBuilder builder,List<String> tags){
        for(int i=0;i<tags.size();i++){
            builder.append("?,");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(");");
        System.out.println(builder.toString());
        return builder.toString();
    }
        private String buildQueryInsert(StringBuilder builder,List<String> tags){
        for(int i=0;i<tags.size();i++){
            builder.append("(?),");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(";");
        System.out.println(builder.toString());
        return builder.toString();
    }
    
    private void setParamsFromList(PreparedStatement statement,List<String> params) throws SQLException{
        int i = 1;
        for(String param : params){
            statement.setString(i,param);
            i++;
        }
    }

    @Override
    protected String getColumnName(){
        return "tag";
    }
    @Override
    protected String getTableName() {
        return "tags"; // タグテーブルのテーブル名を返す
    }

}