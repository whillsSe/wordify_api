package com.wordify.repository.dao;

import com.wordify.model.dto.TagDto;
import com.wordify.repository.dao.mapper.TagsMapperAsDefinitionElement;

public class TagsDao extends _DefinitionElementDaoBase<TagDto, TagsMapperAsDefinitionElement> {

    @Override
    protected TagsMapperAsDefinitionElement createMapper() {
        return new TagsMapperAsDefinitionElement();
    }
    @Override
    protected String getColumnName(){
        return "tag";
    }
    @Override
    protected String getTableName() {
        return "tags"; // タグテーブルのテーブル名を返す
    }

}
