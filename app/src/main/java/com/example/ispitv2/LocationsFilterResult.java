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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LocationsFilterResult extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLoadMoreFilter;
    private LinearLayout mainScrollView;
    private LayoutInflater inflater;
    private int counter;
    private String filterURLString;
    private TextView labelLoadingFilter;

    private static final String URL = "https://rickandmortyapi.com/api/location?page=";

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
        String type = extras.getString("type");
        String dimension = extras.getString("dimension");
        filterURLString = "&name=" + name + "&type=" + type + "&dimension=" + dimension;

        API.getJSON(URL + filterURLString, new ReadDataHandler() {
            @Override
            public void handleMessage(Message msg) {
                labelLoadingFilter.setVisibility(View.GONE);
                String response = getJson();
                if (response == "[]") {
                    new AlertDialog.Builder(LocationsFilterResult.this)
                            .setTitle("Nothing found for input parameters.")
                            .setMessage("Go back to filter screen.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(LocationsFilterResult.this, LocationsFilterActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
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
                        buttonLoadMoreFilter.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}