package com.wordify.model.dto.common;
import java.util.List;

public abstract class UserSearchRange {
    private Integer loginUser;
    private String type;
    private List<Integer> ids;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<Integer> getIds() {
        return ids;
    }
    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
    public Integer getLoginUser() {
        return loginUser;
    }
    public void setLoginUser(Integer loginUser) {
        this.loginUser = loginUser;
    }
}
