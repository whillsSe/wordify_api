package com.wordify.model.dto;

public class TagDto implements BaseDto{
    private int id;
    private String tag;

    public TagDto(int id,String tag){
        this.id = id;
        this.tag = tag;
    }

    @Override
    public int getId(){
        return this.id;
    }
    @Override 
    public String getValue(){
        return this.getTag();
    }

    public String getTag(){
        return this.tag;
    }
}
