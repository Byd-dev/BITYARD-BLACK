package com.ltqh.qh.language;

public enum  LanguageType {

    CHINESE("ch"),
    ENGLISH("en"),
    THAILAND("th");

    private String language;

    LanguageType(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language == null ? "" : language;
    }
}
