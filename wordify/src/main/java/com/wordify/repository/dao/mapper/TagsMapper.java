package com.wordify.repository.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wordify.model.dto.TagDto;

public class TagsMapper extends _EntityMapperBase<TagDto>{
    @Override
    protected TagDto createDto(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String tag = rs.getString("tag");
        return new TagDto(id, tag);
    }
    
}
