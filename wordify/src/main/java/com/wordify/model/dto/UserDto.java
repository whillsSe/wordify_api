package com.wordify.model.dto;

import com.wordify.model.dto.common.BaseDto;

public class UserDto implements BaseDto{
    private int id;
    private String userName;
    private String icon;

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getValue() {
        return this.getUserName();
    }

    public String getUserName(){
        return this.userName;
    }
    
}
