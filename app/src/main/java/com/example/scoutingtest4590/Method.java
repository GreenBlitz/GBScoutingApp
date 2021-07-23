package com.example.scoutingtest4590;

public enum Method {
    GET("GET"),
    POST("POST"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS"),
    PUT("PUT"),
    DELETE("DELEE"),
    TRACE("TRACE");

    private String display;

    Method(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return this.display;
    }

}
