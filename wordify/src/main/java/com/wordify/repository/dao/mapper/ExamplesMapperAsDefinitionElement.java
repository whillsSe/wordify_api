package com.wordify.repository.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wordify.model.dto.ExampleDto;

public class ExamplesMapperAsDefinitionElement extends _DefinitionElementMapperBase<ExampleDto>{
    @Override
    protected ExampleDto createDto(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String example = rs.getString("example");
        return new ExampleDto(id, example);
    }
    
}
