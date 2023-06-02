package com.wordify.repository.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wordify.model.dto.WordDto;

public class WordsMapper extends _EntityMapperBase<WordDto> {
    @Override
    protected WordDto createDto(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String word = rs.getString("word");
        return new WordDto(id, word);
    }
}
