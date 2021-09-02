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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LocationsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonUseFilter;
    private Button buttonNext;
    private int counter;
    private  LinearLayout mainScrollView;
    private LayoutInflater inflater;
    private TextView labelLoading;


    private static final String URL = "https://rickandmortyapi.com/api/location";

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
        buttonNext.setOnClickListener(this);

        buttonUseFilter = findViewById(R.id.buttonUseFilter);
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

                    ArrayList<LocationModel> locations = LocationModel.parseJSONArray(array);
                    for (LocationModel location : locations) {
                        ConstraintLayout item = (ConstraintLayout) inflater.inflate(R.layout.location_view, null);

                        ImageView imageView = item.findViewById(R.id.imageLocation);
                        ((TextView) item.findViewById(R.id.labelName)).setText(location.getName());
                        ((TextView) item.findViewById(R.id.labelType)).setText(location.getType());
                        ((TextView) item.findViewById(R.id.labelDimension)).setText(location.getDimension());

                        switch (location.getType()) {
                            case "Planet":
                                imageView.setImageResource(R.drawable.planet);
                                break;

                            case "Dwarf planet":
                                imageView.setImageResource(R.drawable.planet);
                                break;

                            case "Dwarf planet (Celestial Dwarf)":
                                imageView.setImageResource(R.drawable.planet);
                                break;

                            case "Cluster":
                                imageView.setImageResource(R.drawable.cluster);
                                break;

                            case "Space station":
                                imageView.setImageResource(R.drawable.station);
                                break;

                            case "Microverse":
                                imageView.setImageResource(R.drawable.microverse);
                                break;

                            case "Miniverse":
                                imageView.setImageResource(R.drawable.microverse);
                                break;

                            case "Teenyverse":
                                imageView.setImageResource(R.drawable.microverse);
                                break;

                            case "TV":
                                imageView.setImageResource(R.drawable.tv);
                                break;

                            case "Resort":
                                imageView.setImageResource(R.drawable.resort);
                                break;

                            case "Fantasy town":
                                imageView.setImageResource(R.drawable.fantasy);
                                break;

                            case "Dream":
                                imageView.setImageResource(R.drawable.dream);
                                break;

                            case "Dimension":
                                imageView.setImageResource(R.drawable.dimension);
                                break;

                            case "Game":
                                imageView.setImageResource(R.drawable.game);
                                break;

                            case "unknown":
                                imageView.setImageResource(R.drawable.unknown);
                                break;

                            case "Spacecraft":
                                imageView.setImageResource(R.drawable.spacecraft);
                                break;

                            case "Box":
                                imageView.setImageResource(R.drawable.box);
                                break;

                            //TREBA DODATI JOS SLIKA

                            default:
                                imageView.setImageResource(R.drawable.location);
                                break;
                        }

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
                startActivity(new Intent(LocationsActivity.this, LocationsFilterActivity.class));
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

                            ArrayList<LocationModel> locations = LocationModel.parseJSONArray(array);
                            for (LocationModel location : locations) {
                                ConstraintLayout item = (ConstraintLayout) inflater.inflate(R.layout.location_view, null);

                                ImageView imageView = item.findViewById(R.id.imageLocation);
                                ((TextView) item.findViewById(R.id.labelName)).setText(location.getName());
                                ((TextView) item.findViewById(R.id.labelType)).setText(location.getType());
                                ((TextView) item.findViewById(R.id.labelDimension)).setText(location.getDimension());

                                switch (location.getType()) {
                                    case "Planet":
                                        imageView.setImageResource(R.drawable.planet);
                                        break;

                                    case "Dwarf planet":
                                        imageView.setImageResource(R.drawable.planet);
                                        break;

                                    case "Dwarf planet (Celestial Dwarf)":
                                        imageView.setImageResource(R.drawable.planet);
                                        break;

                                    case "Cluster":
                                        imageView.setImageResource(R.drawable.cluster);
                                        break;

                                    case "Space station":
                                        imageView.setImageResource(R.drawable.station);
                                        break;

                                    case "Microverse":
                                        imageView.setImageResource(R.drawable.microverse);
                                        break;

                                    case "Miniverse":
                                        imageView.setImageResource(R.drawable.microverse);
                                        break;

                                    case "Teenyverse":
                                        imageView.setImageResource(R.drawable.microverse);
                                        break;

                                    case "TV":
                                        imageView.setImageResource(R.drawable.tv);
                                        break;

                                    case "Resort":
                                        imageView.setImageResource(R.drawable.resort);
                                        break;

                                    case "Fantasy town":
                                        imageView.setImageResource(R.drawable.fantasy);
                                        break;

                                    case "Dream":
                                        imageView.setImageResource(R.drawable.dream);
                                        break;

                                    case "Dimension":
                                        imageView.setImageResource(R.drawable.dimension);
                                        break;

                                    case "Game":
                                        imageView.setImageResource(R.drawable.game);
                                        break;

                                    case "unknown":
                                        imageView.setImageResource(R.drawable.unknown);
                                        break;

                                    case "Spacecraft":
                                        imageView.setImageResource(R.drawable.spacecraft);
                                        break;

                                    case "Box":
                                        imageView.setImageResource(R.drawable.box);
                                        break;

                                    //TREBA DODATI JOS SLIKA

                                    default:
                                        imageView.setImageResource(R.drawable.location);
                                        break;
                                }

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