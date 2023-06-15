package com.wordify.model.dto;

import com.wordify.model.dto.common.UserSearchRange;

public class SearchRequestDto extends UserSearchRange{
    private String searchString;
    public String getSearchString() {
        return searchString;
    }
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
