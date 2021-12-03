package com.example.cis385finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cis385finalproject.models.FetchInfo;
import com.example.cis385finalproject.models.FetchInfoRandom;
import com.squareup.picasso.Picasso;

public class RandomCard extends AppCompatActivity {

    private static final String CARD_URL = "";
    private static String imageURL;
    private String mClassName = "Random Card";

    private TextView mNameText;
    private ImageView mCardImage;
    private TextView mCardLevel;
    private TextView mCardRace;
    private TextView mCardAttribute;
    private TextView mCardType;
    private TextView mDestText;
    private TextView mCardArchetype;
    private TextView mCardPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_card);

        mNameText = (TextView)findViewById(R.id.cardNameText);
        mCardImage = (ImageView)findViewById(R.id.cardImage);
        mCardLevel = (TextView)findViewById(R.id.cardLevel);
        mCardRace = (TextView)findViewById(R.id.cardRace);
        mCardAttribute = (TextView)findViewById(R.id.cardAttribute);
        mCardType = (TextView)findViewById(R.id.cardType);
        mDestText = (TextView)findViewById(R.id.cardDescription);
        mCardArchetype = (TextView)findViewById(R.id.cardArchetype);
        mCardPrice = (TextView)findViewById(R.id.cardPrice);

        if (savedInstanceState != null) {
            Picasso.get().load(imageURL).into(mCardImage);
        }
        else {
            new FetchInfoRandom(mNameText, mCardImage, mCardLevel, mCardRace, mCardAttribute, mCardType, mDestText, mCardArchetype, mCardPrice, mClassName).execute("");
        }
    }

    public static void setImageURL(String url){
        imageURL = url;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CARD_URL, imageURL);

    }
}