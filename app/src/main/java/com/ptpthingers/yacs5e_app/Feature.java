package com.ptpthingers.yacs5e_app;

/**
 * Created by Kacper on 15.11.2017.
 */

class Feature {
    private Item background;
    private Category category;
    private Item characterClass;
    private String name;
    private String description;
    private int minimumLevel;
    private String race;
    private Item subclass;
    private boolean ranged;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Feature(Item background, Category category, Item characterClass, String name, String description, int minimumLevel, String race, Item subclass, int range) {
        this.background = background;
        this.category = category;
        this.characterClass = characterClass;
        this.name = name;
        this.description = description;
        this.minimumLevel = minimumLevel;
        this.race = race;
        this.subclass = subclass;
        this.ranged = ranged;
    }

    public Feature() {
        this.background = new Item();
        this.category = Category.valueOf("CLASS");
        this.characterClass = new Item();
        this.name = "feature";
        this.description = "lorem ipsum dolor sit amet.";
        this.minimumLevel = 0;
        this.race = "";
        this.subclass = new Item();
        this.ranged = false;
    }
}