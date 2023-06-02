package com.wordify.repository.dao;

import com.wordify.model.dto.MeaningDto;
import com.wordify.repository.dao.mapper.MeaningsMapperAsDefinitionElement;
public class MeaningDao extends _DefinitionElementDaoBase<MeaningDto, MeaningsMapperAsDefinitionElement> {

    @Override
    protected MeaningsMapperAsDefinitionElement createMapper() {
        return new MeaningsMapperAsDefinitionElement();
    }

    @Override
    protected String getTableName() {
        return "meanings"; // 意味テーブルのテーブル名を返す
    }

    @Override
    protected String getColumnName() {
        return "meaning";
    }

}
