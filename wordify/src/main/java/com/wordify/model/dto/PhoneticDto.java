package com.wordify.model.dto;

public class PhoneticDto implements BaseDto{
    private int id;
    private String phonetic;

    public PhoneticDto(int id,String phonetic){
        this.id = id;
        this.phonetic = phonetic;
    }

    @Override
    public int getId(){
        return this.id;
    }
    @Override
    public String getValue(){
        return this.getPhonetic();
    }
    public String getPhonetic(){
        return this.phonetic;
    }
}
