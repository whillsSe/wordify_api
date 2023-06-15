package com.wordify.model.dto;

import com.wordify.model.dto.common.UserSearchRange;

public class DefinitionRequestDto extends UserSearchRange{
    private int wordId;
    private int phoneticId;
    public int getWordId() {
        return wordId;
    }
    public void setWordId(int wordId) {
        this.wordId = wordId;
    }
    public int getPhoneticId() {
        return phoneticId;
    }
    public void setPhoneticId(int phoneticId) {
        this.phoneticId = phoneticId;
    }
}
