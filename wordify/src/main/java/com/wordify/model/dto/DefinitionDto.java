package com.wordify.model.dto;

import java.util.List;

public class DefinitionDto {
    private int id;
    private List<MeaningDto> meanings;
    private int user_id;
    private List<TagDto> tags;
    private List<ExampleDto> examples;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public List<MeaningDto> getMeanings() {
        return meanings;
    }
    public void setMeanings(List<MeaningDto> meanings) {
        this.meanings = meanings;
    }
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public List<TagDto> getTags() {
        return tags;
    }
    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }
    public List<ExampleDto> getExamples() {
        return examples;
    }
    public void setExamples(List<ExampleDto> examples) {
        this.examples = examples;
    }
    
}
