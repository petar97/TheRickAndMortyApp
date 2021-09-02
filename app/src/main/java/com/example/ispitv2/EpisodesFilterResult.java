package com.example.ispitv2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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

public class EpisodesFilterResult extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLoadMoreFilter;
    private LinearLayout mainScrollView;
    private LayoutInflater inflater;
    private int counter;
    private String filterURLString;
    private TextView labelLoadingFilter;

    private static final String URL = "https://rickandmortyapi.com/api/episode?page=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_result);

        initComponents();
    }

    @SuppressLint("HandlerLeak")
    private void initComponents() {
        mainScrollView = findViewById(R.id.mainScrollView);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        buttonLoadMoreFilter = findViewById(R.id.buttonLoadMoreFilter);
        buttonLoadMoreFilter.setOnClickListener(this);
        counter = 1;

        labelLoadingFilter = findViewById(R.id.labelLoadingFilter);
        labelLoadingFilter.setText("Fetching data...");

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        String season = extras.getString("season");
        switch (season) {
            case "All seasons":
                season = "";
                break;
            case "Season 1":
                season = "s01";
                break;
            case "Season 2":
                season = "s02";
                break;
            case "Season 3":
                season = "s03";
                break;
            case "Season 4":
                season = "s04";
                break;
        }
        filterURLString = "&name=" + name + "&episode=" + season;


        API.getJSON(URL + filterURLString, new ReadDataHandler() {
            @Override
            public void handleMessage(Message msg) {
                labelLoadingFilter.setVisibility(View.GONE);
                String response = getJson();
                if (response == "[]") {
                    new AlertDialog.Builder(EpisodesFilterResult.this)
                            .setTitle("Nothing found for input parameters.")
                            .setMessage("Go back to filter screen.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(EpisodesFilterResult.this, EpisodesFilterActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

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
                        buttonLoadMoreFilter.setVisibility(View.GONE);
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
        counter++;
        API.getJSON(URL + counter + filterURLString, new ReadDataHandler() {
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
                        buttonLoadMoreFilter.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}