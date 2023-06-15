package com.wordify.repository.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wordify.model.dto.DefinitionTagDto;

public class DefinitionTagMapper extends _DefinitionElementMapperBase<DefinitionTagDto>{
    @Override
    protected DefinitionTagDto createDto(ResultSet rs)throws SQLException{
        int definitionId = rs.getInt("definiton_id");
        int tagId = rs.getInt("tag_id");
        return new DefinitionTagDto(definitionId, tagId);
    }
}
