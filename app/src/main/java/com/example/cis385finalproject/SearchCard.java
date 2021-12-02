package com.example.cis385finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cis385finalproject.models.FetchInfo;

import org.w3c.dom.Text;

public class SearchCard extends AppCompatActivity {

    private String mClassName = "Search Card";

    private TextView mCardNameInput;

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
        setContentView(R.layout.activity_search_card);

        mCardNameInput = (TextView)findViewById(R.id.cardNameInput);
        mNameText = (TextView)findViewById(R.id.cardNameText);
        mCardImage = (ImageView)findViewById(R.id.cardImage);
        mCardLevel = (TextView)findViewById(R.id.cardLevel);
        mCardRace = (TextView)findViewById(R.id.cardRace);
        mCardAttribute = (TextView)findViewById(R.id.cardAttribute);
        mCardType = (TextView)findViewById(R.id.cardType);
        mDestText = (TextView)findViewById(R.id.cardDescription);
        mCardArchetype = (TextView)findViewById(R.id.cardArchetype);
        mCardPrice = (TextView)findViewById(R.id.cardPrice);

    }

    public void searchCard (View view){
        String queryString = mCardNameInput.getText().toString();
        new FetchInfo(mNameText, mCardImage, mCardLevel, mCardRace, mCardAttribute, mCardType, mDestText, mCardArchetype, mCardPrice,mClassName).execute(queryString);
    }
}