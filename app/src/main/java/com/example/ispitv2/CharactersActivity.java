package com.example.ispitv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class CharactersActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonUseFilter;
    private Button buttonNext;
    private int counter;
    private LinearLayout mainScrollView;
    private LayoutInflater inflater;
    private TextView labelLoading;

    private static final String URL = "https://rickandmortyapi.com/api/character";

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

                    ArrayList<CharacterModel> characters = CharacterModel.parseJSONArray(array);
                    for (CharacterModel character : characters) {

                        ConstraintLayout item = (ConstraintLayout) inflater.inflate(R.layout.character_view, null);
                        ImageView imageCharacter = item.findViewById(R.id.imageCharacter);
                        class ImageTask extends AsyncTask<String, Void, Bitmap> {
                            @Override
                            protected Bitmap doInBackground(String... strings) {
                                try {
                                    java.net.URL url = new java.net.URL(strings[0]);
                                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                    con.setDoInput(true);
                                    con.connect();
                                    InputStream input = con.getInputStream();
                                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                                    input.close();
                                    return bitmap;
                                } catch (Exception e) {

                                }
                                return null;
                            }
                            @Override
                            protected void onPostExecute(Bitmap result) {
                                imageCharacter.setImageBitmap(result);
                            }
                        }

                        new ImageTask().execute(character.getImage());

                        ((TextView) item.findViewById(R.id.labelName)).setText(character.getName());
                        ((TextView) item.findViewById(R.id.labelStatus)).setText(character.getStatus());
                        ((TextView) item.findViewById(R.id.labelSpecies)).setText(" - " + character.getSpecies());
                        ((TextView) item.findViewById(R.id.labelOrigin)).setText(character.getOrigin());
                        ((TextView) item.findViewById(R.id.labelLocation)).setText(character.getLocation());

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
                startActivity(new Intent(CharactersActivity.this, CharactersFilterActivity.class));
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

                            ArrayList<CharacterModel> characters = CharacterModel.parseJSONArray(array);
                            for (CharacterModel character : characters) {
                                ConstraintLayout item = (ConstraintLayout) inflater.inflate(R.layout.character_view, null);
                                ImageView imageCharacter = item.findViewById(R.id.imageCharacter);
                                class ImageTask extends AsyncTask<String, Void, Bitmap> {
                                    @Override
                                    protected Bitmap doInBackground(String... strings) {
                                        try {
                                            java.net.URL url = new java.net.URL(strings[0]);
                                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                            con.setDoInput(true);
                                            con.connect();
                                            InputStream input = con.getInputStream();
                                            Bitmap bitmap = BitmapFactory.decodeStream(input);
                                            input.close();
                                            return bitmap;
                                        } catch (Exception e) {

                                        }
                                        return null;
                                    }
                                    @Override
                                    protected void onPostExecute(Bitmap result) {
                                        imageCharacter.setImageBitmap(result);
                                    }
                                }

                                new ImageTask().execute(character.getImage());

                                ((TextView) item.findViewById(R.id.labelName)).setText(character.getName());
                                ((TextView) item.findViewById(R.id.labelStatus)).setText(character.getStatus());
                                ((TextView) item.findViewById(R.id.labelSpecies)).setText(character.getSpecies());
                                ((TextView) item.findViewById(R.id.labelOrigin)).setText(character.getOrigin());
                                ((TextView) item.findViewById(R.id.labelLocation)).setText(character.getLocation());

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