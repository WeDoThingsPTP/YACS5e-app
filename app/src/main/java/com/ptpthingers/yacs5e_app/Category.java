package com.ptpthingers.yacs5e_app;

/**
 * Created by leo on 18.12.17.
 */

public enum Category {
    CLASS("CLASS"), RACE("RACE"), ITEM("ITEM"), FEAT("FEAT");

    private final String desc;

    Category(String str) {
        this.desc = str;
    }
}
