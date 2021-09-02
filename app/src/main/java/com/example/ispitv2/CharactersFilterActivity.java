package com.example.ispitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;

public class CharactersFilterActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String SHARED_PREFERENCES_PREFIX = "charFilterSharedPreferencesPrefix";

    private EditText inputFilterName;
    private EditText inputFilterSpecies;
    private Spinner spinnerFilterStatus;
    private Spinner spinnerFilterGender;
    private Button buttonCharacterFilterSearch;
    private Button buttonCharacterFilterReset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_filter);

        initComponents();
    }

    private void initComponents() {
        inputFilterName = findViewById(R.id.inputFilterName);
        inputFilterSpecies = findViewById(R.id.inputFilterSpecies);
        spinnerFilterStatus = findViewById(R.id.spinnerFilterStatus);
        spinnerFilterGender = findViewById(R.id.spinnerFilterGender);
        buttonCharacterFilterSearch = findViewById(R.id.buttonCharacterFilterSearch);
        buttonCharacterFilterSearch.setOnClickListener(this);

        buttonCharacterFilterReset = findViewById(R.id.buttonCharFilterReset);
        buttonCharacterFilterReset.setOnClickListener(this);

        ArrayList<String> statusList = new ArrayList<>();
        statusList.add("All");
        statusList.add("Alive");
        statusList.add("Dead");
        statusList.add("Unknown");

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_item, statusList);
        spinnerFilterStatus.setAdapter(statusAdapter);

        ArrayList<String> genderList = new ArrayList<>();
        genderList.add("All");
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Genderless");
        genderList.add("Unknown");

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this, R.layout.custom_spinner_item, genderList);
        spinnerFilterGender.setAdapter(genderAdapter);

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
            case R.id.buttonCharacterFilterSearch:
                String name = inputFilterName.getText().toString();
                String species = inputFilterSpecies.getText().toString().replaceAll("\\s+", "%20");
                String status = spinnerFilterStatus.getSelectedItem().toString();
                String gender = spinnerFilterGender.getSelectedItem().toString();

                Bundle extras = new Bundle();
                extras.putString("name", name);
                extras.putString("species", species);
                extras.putString("status", status);
                extras.putString("gender", gender);

                startActivity(new Intent(CharactersFilterActivity.this, CharactersFilterResult.class)
                        .putExtras(extras));
                break;

            case R.id.buttonCharFilterReset:
                inputFilterName.setText("");
                inputFilterSpecies.setText("");
                spinnerFilterStatus.setSelection(0);
                spinnerFilterGender.setSelection(0);
                break;
        }
    }

    private void saveInputData() {
        String savedName = inputFilterName.getText().toString();
        String savedSpecies = inputFilterSpecies.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_PREFIX, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("savedName", savedName);
        editor.putString("savedSpecies", savedSpecies);
        editor.commit();
    }

    private void readInputData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_PREFIX, 0);
        String savedName = sharedPreferences.getString("savedName", "");
        String savedSpecies = sharedPreferences.getString("savedSpecies", "");

        inputFilterName.setText(savedName);
        inputFilterSpecies.setText(savedSpecies);
    }
}