package com.wordify.repository.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wordify.model.dto.EntryDto;
import com.wordify.model.dto.PhoneticDto;
import com.wordify.model.dto.WordDto;

public class EntryMapper extends _EntityMapperBase<EntryDto>{

    @Override
    EntryDto createDto(ResultSet rs) throws SQLException {
        String word = rs.getString("word");
        String phonetic = rs.getString("phonetic");
        Integer wordId = rs.getInt("word_id");
        Integer phoneticId = rs.getInt("phonetic_id");
        WordDto wDto = new WordDto(wordId, word);
        PhoneticDto pDto = new PhoneticDto(phoneticId, phonetic);
        return new EntryDto(wDto, pDto);
    }
    
}
