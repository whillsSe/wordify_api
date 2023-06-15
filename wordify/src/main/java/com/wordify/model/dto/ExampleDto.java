package com.wordify.model.dto;

import com.wordify.model.dto.common.BaseDto;

public class ExampleDto implements BaseDto{
    private int id;
    private String example;

    public ExampleDto(int id,String example){
        this.id = id;
        this.example = example;
    }

    @Override
    public int getId(){
        return this.id;
    }
    @Override
    public String getValue(){
        return this.getExample();
    }

    public String getExample(){
        return this.example;
    }
}
