package com.wordify.repository.dao;

import com.wordify.model.dto.ExampleDto;
import com.wordify.repository.dao.mapper.ExamplesMapperAsDefinitionElement;
public class ExampleDao extends _DefinitionElementDaoBase<ExampleDto, ExamplesMapperAsDefinitionElement> {

    @Override
    protected ExamplesMapperAsDefinitionElement createMapper() {
        return new ExamplesMapperAsDefinitionElement();
    }
    @Override
    protected String getTableName() {
        return "examples"; // 意味テーブルのテーブル名を返す
    }
    @Override
    protected String getColumnName() {
        return "example";
    }

}