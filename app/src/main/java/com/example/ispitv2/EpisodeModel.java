package com.example.ispitv2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class EpisodeModel {
    private String episode;
    private String name;
    private String aired;

    public EpisodeModel() {
    }

    public EpisodeModel(String episode, String name, String aired) {
        this.episode = episode;
        this.name = name;
        this.aired = aired;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAired() {
        return aired;
    }

    public void setAired(String aired) {
        this.aired = aired;
    }

    public static ArrayList<EpisodeModel> parseJSONArray(JSONArray array) {
        ArrayList<EpisodeModel> list = new ArrayList<>();

        try {
            for (int i = 0; i < array.length(); i++) {
                EpisodeModel episode = parseJSONObject(array.getJSONObject(i));
                list.add(episode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public static EpisodeModel parseJSONObject(JSONObject object) {
        EpisodeModel episode = new EpisodeModel();

        try {
            if (object.has("episode")) {
                episode.setEpisode(object.getString("episode"));
            }

            if (object.has("name")) {
                episode.setName(object.getString("name"));
            }

            if (object.has("air_date")) {
                episode.setAired(object.getString("air_date"));
            }

        } catch (Exception e) {

        }

        return episode;
    }
}
