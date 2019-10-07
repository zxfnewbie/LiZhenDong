package com.test1.calculator.converter.adapters;

import java.io.Serializable;

public class ItemUnitConverter implements Serializable {
    private String title;
    private String res;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

}
