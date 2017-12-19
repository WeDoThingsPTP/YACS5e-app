package com.ptpthingers.yacs5e_app;

public class Race {
    private String name;
    private String parentName;

    private int strMod;
    private int dexMod;
    private int conMod;
    private int intMod;
    private int wisMod;
    private int chaMod;

    private int speed;
//    private List<Item> languages;
//    private List<Item> skills;
//    private List<Item> weapons;
//    private List<Item> armor;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getStrMod() {
        return strMod;
    }

    public void setStrMod(int strMod) {
        this.strMod = strMod;
    }

    public int getDexMod() {
        return dexMod;
    }

    public void setDexMod(int dexMod) {
        this.dexMod = dexMod;
    }

    public int getConMod() {
        return conMod;
    }

    public void setConMod(int conMod) {
        this.conMod = conMod;
    }

    public int getIntMod() {
        return intMod;
    }

    public void setIntMod(int intMod) {
        this.intMod = intMod;
    }

    public int getWisMod() {
        return wisMod;
    }

    public void setWisMod(int wisMod) {
        this.wisMod = wisMod;
    }

    public int getChaMod() {
        return chaMod;
    }

    public void setChaMod(int chaMod) {
        this.chaMod = chaMod;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Race(String name, String parentName, int strMod, int dexMod, int conMod, int intMod, int wisMod, int chaMod, int speed) {
        this.name = name;
        this.parentName = parentName;
        this.strMod = strMod;
        this.dexMod = dexMod;
        this.conMod = conMod;
        this.intMod = intMod;
        this.wisMod = wisMod;
        this.chaMod = chaMod;
        this.speed = speed;
    }
}
