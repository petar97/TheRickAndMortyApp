package com.example.ispitv2;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CharacterModel {
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private String origin;
    private String location;
    private String image;

    public CharacterModel() {

    }

    public CharacterModel(String name, String status, String species, String type, String gender, String origin, String location, String image) {
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.origin = origin;
        this.location = location;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public static ArrayList<CharacterModel> parseJSONArray(JSONArray array) {
        ArrayList<CharacterModel> list = new ArrayList<>();

        try {
            for (int i = 0; i < array.length(); i++) {
                CharacterModel character = parseJSONObject(array.getJSONObject(i));
                list.add(character);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public static CharacterModel parseJSONObject(JSONObject object) {
        CharacterModel character = new CharacterModel();

        try {
            if (object.has("name")) {
                character.setName(object.getString("name"));
            }

            if (object.has("status")) {
                character.setStatus(object.getString("status"));
            }

            if (object.has("species")) {
                character.setSpecies(object.getString("species"));
            }

            if (object.has("type")) {
                character.setType(object.getString("type"));
            }

            if (object.has("gender")) {
                character.setGender(object.getString("gender"));
            }

            if (object.has("origin")) {
                character.setOrigin(object.getJSONObject("origin").getString("name"));
            }

            if (object.has("location")) {
                character.setLocation(object.getJSONObject("location").getString("name"));
            }

            if (object.has("image")) {
                character.setImage(object.getString("image"));
            }

        } catch (Exception e) {

        }

        return character;
    }
}
