package com.wordify.repository.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public abstract class _EntityMapperBase<T> {
    public List<T> mapToList(ResultSet rs) throws SQLException {
        List<T> dtoList = new ArrayList<>();
        while (rs.next()) {
            T dto = createDto(rs);
            dtoList.add(dto);
        }
        return dtoList;
    }
   abstract T createDto(ResultSet rs) throws SQLException;
}