package com.wordify.model.dto;

import java.util.List;

public class EntryDto {
    private WordDto word;
    private PhoneticDto phonetic;
    private List<DefinitionDto> definitions;
    public EntryDto(WordDto word,PhoneticDto phonetic){
        this.word = word;
        this.phonetic = phonetic;
    }
    public WordDto getWord() {
        return word;
    }
    public PhoneticDto getPhonetic() {
        return phonetic;
    }
    public void setDefinitions(List<DefinitionDto> definitions){
        this.definitions = definitions;
    }
    public List<DefinitionDto> getDefinitions(){
        return this.definitions;
    }
}
