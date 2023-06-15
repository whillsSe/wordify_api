package com.wordify.repository.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wordify.model.dto.DefinitionTagDto;
import com.wordify.model.dto.TagDto;

public class TagsMapperAsDefinitionElement extends _DefinitionElementMapperBase<TagDto>{
    @Override
    protected TagDto createDto(ResultSet rs)throws SQLException{
        //int definitionId = rs.getInt("definition_id");
        int id = rs.getInt("id");
        String tag = rs.getString("tag");
        return new TagDto(id, tag);
    }
}
