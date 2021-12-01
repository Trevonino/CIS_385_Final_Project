package com.example.cis385finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startRandomCard(View view) {
        Intent intent = new Intent(this, RandomCard.class);
        startActivity(intent);
    }

    public void startSearchCard(View view) {
        Intent intent = new Intent(this, SearchCard.class);
        startActivity(intent);
    }
}