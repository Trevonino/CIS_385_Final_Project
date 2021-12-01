package com.example.cis385finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cis385finalproject.models.FetchInfo;

public class SearchCard extends AppCompatActivity {

    private TextView mCardNameInput;
    private TextView mNameText;
    private ImageView mCardImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_card);

        mCardNameInput = (TextView)findViewById(R.id.cardNameInput);
        mNameText = (TextView)findViewById(R.id.cardNameText);
        mCardImage = (ImageView)findViewById(R.id.cardImage);
    }

    public void searchCard (View view){
        String queryString = mCardNameInput.getText().toString();
        new FetchInfo(mNameText, mCardImage).execute(queryString);
    }
}