package com.example.dmc.entity;

public enum Type {

    PICTURE("PICTURE"),
    STRING("STRING"),
    NUMBER("NUMBER"),
    TEXT("TEXT");

    private final String str;

    Type(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
