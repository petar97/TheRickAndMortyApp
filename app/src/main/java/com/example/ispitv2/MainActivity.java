package com.example.ispitv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonCharAll;
    private Button buttonCharFilter;
    private Button buttonLocationAll;
    private Button buttonLocationFilter;
    private Button buttonEpAll;
    private Button buttonEpFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        buttonCharAll = findViewById(R.id.buttonCharAll);
        buttonCharFilter = findViewById(R.id.buttonCharFilter);
        buttonCharAll.setOnClickListener(this);
        buttonCharFilter.setOnClickListener(this);
        buttonLocationAll = findViewById(R.id.buttonLocAll);
        buttonLocationFilter = findViewById(R.id.buttonLocFilter);
        buttonLocationAll.setOnClickListener(this);
        buttonLocationFilter.setOnClickListener(this);
        buttonEpAll = findViewById(R.id.buttonEpAll);
        buttonEpFilter = findViewById(R.id.buttonEpFilter);
        buttonEpAll.setOnClickListener(this);
        buttonEpFilter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCharAll:
                startActivity(new Intent(MainActivity.this, CharactersActivity.class));
                break;

            case R.id.buttonCharFilter:
                startActivity(new Intent(MainActivity.this, CharactersFilterActivity.class));
                break;

            case R.id.buttonLocAll:
                startActivity(new Intent(MainActivity.this, LocationsActivity.class));
                break;

            case R.id.buttonLocFilter:
                startActivity(new Intent(MainActivity.this, LocationsFilterActivity.class));
                break;

            case R.id.buttonEpAll:
                startActivity(new Intent(MainActivity.this, EpisodesActivity.class));
                break;

            case R.id.buttonEpFilter:
                startActivity(new Intent(MainActivity.this, EpisodesFilterActivity.class));
                break;
        }
    }
}
