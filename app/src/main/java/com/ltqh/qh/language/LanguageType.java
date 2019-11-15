package com.ltqh.qh.language;

public enum  LanguageType {

    CHINESE("zh_CN"),
    ENGLISH("en_US");

    private String language;

    LanguageType(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language == null ? "" : language;
    }
}
