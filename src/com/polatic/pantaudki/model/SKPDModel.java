package com.polatic.pantaudki.model;

import java.util.Date;

public class SKPDModel {
    private String name;
    private String code;

    public SKPDModel(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
