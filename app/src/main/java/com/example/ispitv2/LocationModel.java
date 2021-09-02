package com.example.ispitv2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class LocationModel {
    private String name;
    private String type;
    private String dimension;

    public LocationModel() {
    }

    public LocationModel(String name, String type, String dimension) {
        this.name = name;
        this.type = type;
        this.dimension = dimension;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }


    public static ArrayList<LocationModel> parseJSONArray(JSONArray array) {
        ArrayList<LocationModel> list = new ArrayList<>();

        try {
            for (int i = 0; i < array.length(); i++) {
                LocationModel location = parseJSONObject(array.getJSONObject(i));
                list.add(location);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public static LocationModel parseJSONObject(JSONObject object) {
        LocationModel character = new LocationModel();

        try {
            if (object.has("name")) {
                character.setName(object.getString("name"));
            }

            if (object.has("type")) {
                character.setType(object.getString("type"));
            }

            if (object.has("dimension")) {
                character.setDimension(object.getString("dimension"));
            }

        } catch (Exception e) {

        }

        return character;
    }
}
