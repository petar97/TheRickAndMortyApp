package com.example.ispitv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class EpisodesActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonUseFilter;
    private Button buttonNext;
    private int counter;
    private LinearLayout mainScrollView;
    private LayoutInflater inflater;
    private TextView labelLoading;

    private static final String URL = "https://rickandmortyapi.com/api/episode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initComponents();
    }

    @SuppressLint("HandlerLeak")
    private void initComponents() {
        mainScrollView = findViewById(R.id.mainScrollView);

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        buttonNext = findViewById(R.id.buttonNext);
        buttonUseFilter = findViewById(R.id.buttonUseFilter);
        buttonNext.setOnClickListener(this);
        buttonUseFilter.setOnClickListener(this);
        counter = 1;

        labelLoading = findViewById(R.id.labelLoading);
        labelLoading.setText("Fetching data...");

        API.getJSON(URL, new ReadDataHandler() {
            @Override
            public void handleMessage(Message msg) {
                labelLoading.setVisibility(View.GONE);
                String response = getJson();
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("results");

                    ArrayList<EpisodeModel> episodes = EpisodeModel.parseJSONArray(array);
                    for (EpisodeModel episode : episodes) {
                        ConstraintLayout item = (ConstraintLayout) inflater.inflate(R.layout.episode_view, null);

                        ((TextView) item.findViewById(R.id.labelEpisode)).setText(episode.getEpisode());
                        ((TextView) item.findViewById(R.id.labelEpName)).setText(" - " + episode.getName());
                        ((TextView) item.findViewById(R.id.labelAired)).setText(episode.getAired());

                        mainScrollView.addView(item);
                    }
                    if (object.getJSONObject("info").getString("next").equals("null")) {
                        buttonNext.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonUseFilter:
                startActivity(new Intent(EpisodesActivity.this, EpisodesFilterActivity.class));
                break;

            case R.id.buttonNext:
                counter++;
                API.getJSON(URL + "/?page=" + counter, new ReadDataHandler() {
                    @Override
                    public void handleMessage(Message msg) {
                        String response = getJson();
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("results");

                            ArrayList<EpisodeModel> episodes = EpisodeModel.parseJSONArray(array);
                            for (EpisodeModel episode : episodes) {
                                ConstraintLayout item = (ConstraintLayout) inflater.inflate(R.layout.episode_view, null);

                                ((TextView) item.findViewById(R.id.labelEpisode)).setText(episode.getEpisode());
                                ((TextView) item.findViewById(R.id.labelEpName)).setText(" - " + episode.getName());
                                ((TextView) item.findViewById(R.id.labelAired)).setText(episode.getAired());

                                mainScrollView.addView(item);
                            }
                            if (object.getJSONObject("info").getString("next").equals("null")) {
                                buttonNext.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
    }
}