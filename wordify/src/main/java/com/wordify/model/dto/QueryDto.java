package com.wordify.model.dto;

import java.util.List;

public class QueryDto {
    private List<Integer> userIds;
    private Integer wordId;
    private Integer phoneticId;
    private String word;
    private String phonetic;
    private int userId;//ログインしてるユーザーの情報がここに格納される

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public List<Integer> getUserIds() {
        return userIds;
    }
    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }

    public Integer getWordId() {
        return wordId;
    }
    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }

    public Integer getPhoneticId() {
        return phoneticId;
    }
    public void setPhoneticId(Integer phoneticId) {
        this.phoneticId = phoneticId;
    }

    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }

    public String getPhonetic() {
        return phonetic;
    }
    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    // ひらがなでの検索条件が必要かどうか判定するメソッド
    //今回は省略。(「ひらがな」と書いて"ひらがな"と読む可能性もあるし)
}
