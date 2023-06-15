package com.wordify.model.dto;

import com.wordify.model.dto.common.BaseDto;

public class MeaningDto implements BaseDto{
    private int id;
    private String meaning;
    
    public MeaningDto(int id,String meaning){
        this.id = id;
        this.meaning = meaning;
    }

    @Override
    public int getId(){
        return this.id;
    }
    @Override
    public String getValue(){
        return this.getMeaning();
    }

    public String getMeaning(){
        return this.meaning;
    }
}
