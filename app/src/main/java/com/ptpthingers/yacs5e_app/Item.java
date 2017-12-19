package com.ptpthingers.yacs5e_app;

/**
 * Created by leo on 18.12.17.
 */

public class Item {
    private String description;
    private String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item() {
        description = "";
        name = "";
    }
}
