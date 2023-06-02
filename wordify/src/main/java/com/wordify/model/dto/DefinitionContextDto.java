package com.wordify.model.dto;

import java.util.List;

public class DefinitionContextDto{
    private EntryDto prev_entry;
    private EntryDto next_entry;
    private List<DefinitionDto> definitions;
    public void setPrev_entry(EntryDto prev_entry) {
        this.prev_entry = prev_entry;
    }
    public EntryDto getPrevEntry(){
        return this.prev_entry;
    }
    public void setNext_entry(EntryDto next_entry) {
        this.next_entry = next_entry;
    }
    public EntryDto getNextEntry(){
        return this.next_entry;
    }
    public void setDefinitionIds(List<DefinitionDto> definitions) {
        this.definitions = definitions;
    }
    public List<DefinitionDto> getDefinitions(){
        return this.definitions;
    }
}
