package com.wordify.model.dto;

public class DefinitionTagDto {
    private int definitionId;
    private int tagId;
    public DefinitionTagDto(Integer definitionId,Integer tagId){
        this.definitionId = definitionId;
        this.tagId = tagId;
    }
    public int getDefinitionId(){
        return this.definitionId;
    }
    public int getTagId(){
        return this.tagId;
    }
}
