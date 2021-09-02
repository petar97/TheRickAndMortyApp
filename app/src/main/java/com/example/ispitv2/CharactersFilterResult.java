package com.example.ispitv2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
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

public class CharactersFilterResult extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLoadMoreFilter;
    private LinearLayout mainScrollView;
    private LayoutInflater inflater;
    private int counter;
    private String filterURLString;
    private TextView labelLoadingFilter;

    private static final String URL = "https://rickandmortyapi.com/api/character?page=";

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
        String species = extras.getString("species");
        String status = extras.getString("status");
        String gender = extras.getString("gender");
        if (status.equals("All")) status = "";
        if (gender.equals("All")) gender = "";

        filterURLString = "&name=" + name + "&status=" + status + "&gender=" + gender + "&species=" + species;


        API.getJSON(URL + filterURLString, new ReadDataHandler() {
            @Override
            public void handleMessage(Message msg) {
                labelLoadingFilter.setVisibility(View.GONE);
                String response = getJson();
                if (response == "[]") {
                    new AlertDialog.Builder(CharactersFilterResult.this)
                            .setTitle("Nothing found for input parameters.")
                            .setMessage("Go back to filter screen.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(CharactersFilterResult.this, CharactersFilterActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                }
                            })
                            .show();
                }
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
                        buttonLoadMoreFilter.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}