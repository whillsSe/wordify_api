package com.wordify.repository.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wordify.model.dto.PhoneticDto;

public class PhoneticsMapper extends _EntityMapperBase<PhoneticDto>{
    @Override
    protected PhoneticDto createDto(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String phonetic = rs.getString("phonetic");
        return new PhoneticDto(id, phonetic);
    }
    
}
