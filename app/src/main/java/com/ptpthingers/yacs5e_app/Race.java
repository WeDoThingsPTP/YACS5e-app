package com.ptpthingers.yacs5e_app;

import android.util.Log;

import java.util.Set;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
    public Race() {
        this.name = "";
        this.parentName = "";
    }

    @Override
    public String toString() {
        return "Race{" +
                "name='" + name + '\'' +
                ", parentName='" + parentName + '\'' +
                ", strMod=" + strMod +
                ", dexMod=" + dexMod +
                ", conMod=" + conMod +
                ", intMod=" + intMod +
                ", wisMod=" + wisMod +
                ", chaMod=" + chaMod +
                ", speed=" + speed +
                '}';
    }

    static List<Race> makeRaceList(Context context) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("CompletePHBSquireRaces.json");
        } catch (IOException e) {
            Log.d("Race", "Can't open asset .json");
            return null;
        }

        List<Race> returnRaceList = new ArrayList<>();

        JsonElement root = new JsonParser().parse(Utils.stringFromInputStream(inputStream));
        JsonArray jsonRaceArray = root.getAsJsonObject().get("objects").getAsJsonArray();

        for (JsonElement element : jsonRaceArray) {
            JsonObject raceJsonObj = element.getAsJsonObject();
            returnRaceList.add(singleRace(raceJsonObj));
        }
        return returnRaceList;
    }

    //

    static Race getRaceByName(Context context, String name) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("CompletePHBSquireRaces.json");
        } catch (IOException e) {
            Log.d("Race", "Can't open asset .json");
            return null;
        }

        Race returnRace = new Race();

        JsonElement root = new JsonParser().parse(Utils.stringFromInputStream(inputStream));
        JsonArray jsonRaceArray = root.getAsJsonObject().get("objects").getAsJsonArray();

        for (JsonElement element : jsonRaceArray) {
            JsonObject raceJsonObj = element.getAsJsonObject();
            Race race = singleRace(raceJsonObj);
            if (race.name.equals(name)) {
                return race;
            }
        }
        return null;
    }

    private static Race singleRace(JsonObject raceJsonObj) {
        Set<String> raceKeySet = raceJsonObj.keySet();
        Race race = new Race();

        for (String raceKey : raceKeySet) {
            switch (raceKey) {
                case "type":
                    // Obviously type is Race
                    break;
                case "armorProfs":
                    break;
                case "armorTypeProfs":
                    break;
                case "burrowSpeed":
                    break;
                case "chaMod":
                    race.chaMod = raceJsonObj.getAsJsonPrimitive(raceKey).getAsInt();
                    break;
                case "climbingSpeed":
                    break;
                case "conMod":
                    race.conMod = raceJsonObj.getAsJsonPrimitive(raceKey).getAsInt();
                    break;
                case "crawlingSpeed":
                    break;
                case "dexMod":
                    race.dexMod = raceJsonObj.getAsJsonPrimitive(raceKey).getAsInt();
                    break;
                case "flyingSpeed":
                    break;
                case "intMod":
                    race.intMod = raceJsonObj.getAsJsonPrimitive(raceKey).getAsInt();
                    break;
                case "languageProfs":
                    break;
                case "name":
                    race.name = raceJsonObj.getAsJsonPrimitive(raceKey).getAsString();
                    break;
                case "savingThrowProfs":
                    break;
                case "size":
                    break;
                case "skillProfs":
                    break;
                case "speed":
                    race.speed = raceJsonObj.getAsJsonPrimitive(raceKey).getAsInt();
                    break;
                case "strMod":
                    race.strMod = raceJsonObj.getAsJsonPrimitive(raceKey).getAsInt();
                    break;
                case "swimmingSpeed":
                    break;
                case "toolProfs":
                    break;
                case "weaponProfs":
                    break;
                case "weaponTypeProfs":
                    break;
                case "wisMod":
                    race.wisMod = raceJsonObj.getAsJsonPrimitive(raceKey).getAsInt();
                    break;
                case "parentRace":
                    race.parentName = raceJsonObj.getAsJsonObject(raceKey).getAsJsonPrimitive("name").getAsString();
                    break;

                default:
                    Log.i("Race", "Switch I don't know: " + raceKey);
            }
        }
        return race;
    }

}
