package com.example.ispitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class LocationsFilterActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String SHARED_PREFERENCES_PREFIX = "locFilterSharedPreferencesPrefix";

    private EditText inputLocFilterName;
    private EditText inputLocFilterType;
    private EditText inputLocFilterDimension;
    private Button buttonLocationFilterSearch;
    private Button buttonLocationFilterReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_filter);

        initComponents();
    }

    private void initComponents() {
        inputLocFilterName = findViewById(R.id.inputLocFilterName);
        inputLocFilterType= findViewById(R.id.inputLocFilterType);
        inputLocFilterDimension = findViewById(R.id.inputLocFilterDimension);
        buttonLocationFilterSearch = findViewById(R.id.buttonLocationFilterSearch);
        buttonLocationFilterSearch.setOnClickListener(this);

        buttonLocationFilterReset = findViewById(R.id.buttonLocFilterReset);
        buttonLocationFilterReset.setOnClickListener(this);

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
            case R.id.buttonLocationFilterSearch:
                String name = inputLocFilterName.getText().toString().replaceAll("\\s+", "%20");
                String type = inputLocFilterType.getText().toString().replaceAll("\\s+", "%20");
                String dimension = inputLocFilterDimension.getText().toString().replaceAll("\\s+", "%20");

                Bundle extras = new Bundle();
                extras.putString("name", name);
                extras.putString("type", type);
                extras.putString("dimension", dimension);

                startActivity(new Intent(LocationsFilterActivity.this, LocationsFilterResult.class)
                        .putExtras(extras));
                break;

            case R.id.buttonLocFilterReset:
                inputLocFilterName.setText("");
                inputLocFilterType.setText("");
                inputLocFilterDimension.setText("");
                break;
        }
    }

    private void saveInputData() {
        String savedName = inputLocFilterName.getText().toString();
        String savedType = inputLocFilterType.getText().toString();
        String savedDimension = inputLocFilterDimension.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_PREFIX, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("savedName", savedName);
        editor.putString("savedType", savedType);
        editor.putString("savedDimension", savedDimension);
        editor.commit();
    }

    private void readInputData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_PREFIX, 0);
        String savedName = sharedPreferences.getString("savedName", "");
        String savedType = sharedPreferences.getString("savedType", "");
        String savedDimension = sharedPreferences.getString("savedDimension", "");

        inputLocFilterName.setText(savedName);
        inputLocFilterType.setText(savedType);
        inputLocFilterDimension.setText(savedDimension);
    }
}