package com.wordify.model.dto;

import com.wordify.model.dto.common.BaseDto;

public class WordDto implements BaseDto{
    private int id;
    private String word;

    public WordDto(int id,String word){
        this.id = id;
        this.word = word;
    }

    @Override
    public int getId(){
        return this.id;
    }
    @Override
    public String getValue(){
        return this.getWord();
    }
    public String getWord(){
        return this.word;
    }
}
