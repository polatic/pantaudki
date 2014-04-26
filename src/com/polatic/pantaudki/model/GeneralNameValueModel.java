package com.polatic.pantaudki.model;

public class GeneralNameValueModel {
    private String name;
    private String value;

    public GeneralNameValueModel(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}