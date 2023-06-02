package com.wordify.repository.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class _DefinitionElementMapperBase<T>{
    public Map<Integer,List<T>> mapToMap(ResultSet rs)throws SQLException{
        Map<Integer,List<T>> dtoMap = new HashMap<>();
        while (rs.next()){
            T dto = createDto(rs);
            Integer definitionId = rs.getInt("definition_id");
            List<T> elementList = dtoMap.getOrDefault(definitionId,new ArrayList<T>());
            elementList.add(dto);
            dtoMap.put(definitionId, elementList);
        }
        return dtoMap;
    }
    protected abstract T createDto(ResultSet rs) throws SQLException;
}
