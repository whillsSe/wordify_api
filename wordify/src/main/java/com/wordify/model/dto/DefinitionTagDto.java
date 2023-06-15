package com.wordify.model.dto;

public class DefinitionTagDto {
    private int definitionId;
    private int tagId;
    private String tag;

    public DefinitionTagDto(Integer definitionId,Integer tagId){
        this.definitionId = definitionId;
        this.tagId = tagId;
    }

    public DefinitionTagDto(Integer definitionId,Integer tagId,String tag){
        this.definitionId = definitionId;
        this.tagId = tagId;
        this.tag = tag;
    }
    public int getDefinitionId(){
        return this.definitionId;
    }
    public int getTagId(){
        return this.tagId;
    }
    public void setDefinitionId(int definitionId) {
        this.definitionId = definitionId;
    }
    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
}
