package com.wordify.repository.dao;

public class PhoneticDao extends _DaoBase{
    @Override
    protected String getColumnName() {
        return "phonetic";
    }
    @Override
    protected String getTableName() {
        return "phonetics";
    }
}
