package com.wordify.repository.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wordify.model.dto.MeaningDto;

public class MeaningsMapperAsDefinitionElement extends _DefinitionElementMapperBase<MeaningDto>{

    @Override
    protected MeaningDto createDto(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String meaning = rs.getString("meaning");
        return new MeaningDto(id, meaning);
    }
    
}
