package com.example.ispitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class EpisodesFilterActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String SHARED_PREFERENCES_PREFIX = "epFilterSharedPreferencesPrefix";

    private EditText inputEpFilterName;
    private Spinner spinnerFilterSeason;
    private Button buttonEpisodeFilterSearch;
    private Button buttonEpisodeFilterReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes_filter);

        initComponents();
    }

    private void initComponents() {
        inputEpFilterName = findViewById(R.id.inputEpFilterName);
        spinnerFilterSeason = findViewById(R.id.spinnerFilterSeason);
        buttonEpisodeFilterSearch = findViewById(R.id.buttonEpisodeFilterSearch);
        buttonEpisodeFilterSearch.setOnClickListener(this);

        buttonEpisodeFilterReset = findViewById(R.id.buttonEpFilterReset);
        buttonEpisodeFilterReset.setOnClickListener(this);

        ArrayList<String> seasonList = new ArrayList<>();
        seasonList.add("All seasons");
        seasonList.add("Season 1");
        seasonList.add("Season 2");
        seasonList.add("Season 3");
        seasonList.add("Season 4");

        ArrayAdapter<String> seasonAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_item, seasonList);
        spinnerFilterSeason.setAdapter(seasonAdapter);

        readInputData();
    }

    @Override
    protected void onStop() {
        super.onStop();

        saveInputData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonEpisodeFilterSearch:
                String name = inputEpFilterName.getText().toString().replaceAll("\\s+", "%20");
                String season = spinnerFilterSeason.getSelectedItem().toString();

                Bundle extras = new Bundle();
                extras.putString("name", name);
                extras.putString("season", season);

                startActivity(new Intent(EpisodesFilterActivity.this, EpisodesFilterResult.class)
                        .putExtras(extras));
                break;

            case R.id.buttonEpFilterReset:
                inputEpFilterName.setText("");
                spinnerFilterSeason.setSelection(0);
                break;
        }
    }

    private void saveInputData() {
        String savedName = inputEpFilterName.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_PREFIX, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("savedName", savedName);
        editor.commit();
    }

    private void readInputData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_PREFIX, 0);
        String savedName = sharedPreferences.getString("savedName", "");

        inputEpFilterName.setText(savedName);
    }
}