package com.wordify.repository.dao;

public class WordDao extends _DaoBase{
    @Override
    protected String getColumnName() {
        return "word";
    }
    @Override
    protected String getTableName(){
        return "words";
    }
}
