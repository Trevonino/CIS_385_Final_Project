package com.example.cis385finalproject.models;

import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cis385finalproject.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchInfoRandom extends AsyncTask<String, Void, String> {

    private String mClassName;

    private WeakReference<TextView> mCardNameText;
    private WeakReference<ImageView> mCardImage;
    private WeakReference<TextView> mCardLevel;
    private WeakReference<TextView> mCardRace;
    private WeakReference<TextView> mCardAttribute;
    private WeakReference<TextView> mCardTypeText;
    private WeakReference<TextView> mCardDescText;
    private WeakReference<TextView> mCardArchetype;
    private WeakReference<TextView> mCardPrice;

    public FetchInfoRandom(TextView nameText, ImageView cardImage, TextView cardLevel, TextView cardRace, TextView cardAttribute,TextView cardType, TextView cardDesc, TextView cardArch, TextView cardPrice, String className) {
        this.mCardNameText = new WeakReference<>(nameText);
        this.mCardImage = new WeakReference<>(cardImage);
        this.mCardLevel = new WeakReference<>(cardLevel);
        this.mCardRace = new WeakReference<>(cardRace);
        this.mCardAttribute = new WeakReference<>(cardAttribute);
        this.mCardTypeText = new WeakReference<>(cardType);
        this.mCardDescText = new WeakReference<>(cardDesc);
        this.mCardArchetype = new WeakReference<>(cardArch);
        this.mCardPrice = new WeakReference<>(cardPrice);
        this.mClassName = className;
    }


    @Override
    protected String doInBackground(String... strings) {
        if (mClassName.equals("Search Card")) {
            return CardSearchUtil.getCardInfo(strings[0]);
        }

        if (mClassName.equals("Random Card")){
            return RandomCardUtil.getCardInfo();
        }
        else return null;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            if (s != null){
                JSONObject jsonObject = new JSONObject(s);;
                int i = 0;
                String name = null;
                String imageURL = null;
                String cardLevel = null;
                String cardLinkVal = null;
                String cardRace = null;
                String cardAttribute = null;
                String cardType = null;
                String cardDesc = null;
                String cardArch = null;
                String cardPrice = null;
                while (i < jsonObject.length() &&
                        (imageURL == null && name == null && cardDesc == null && cardType == null)) {
                    // Get the current item information.
                    JSONArray cardImage = jsonObject.getJSONArray("card_images");
                    JSONArray cardPrices = jsonObject.getJSONArray("card_prices");
                    JSONObject imageURLObject = cardImage.getJSONObject(i);
                    JSONObject cardPriceObject = cardPrices.getJSONObject(i);

                    // Try to get the author and title from the current item,
                    // catch if either field is empty and move on.
                    try {
                        name = jsonObject.getString("name");
                        imageURL = imageURLObject.getString("image_url");
                        try {
                            cardLevel = jsonObject.getString("level");
                        }
                        catch (Exception e){
                            cardLevel = null;
                        }
                        try {
                            cardLinkVal = jsonObject.getString("linkval");
                        }
                        catch (Exception e){
                            cardLinkVal = null;
                        }
                        try {
                            cardAttribute = jsonObject.getString("attribute");
                        }
                        catch (Exception e){
                            cardAttribute = null;
                        }
                        cardRace = jsonObject.getString("race");
                        cardType = jsonObject.getString("type");
                        cardDesc = jsonObject.getString("desc");
                        try {
                            cardArch = jsonObject.getString("archetype");
                        }
                        catch (Exception e){
                            cardArch = null;
                        }

                        try {
                            cardPrice = cardPriceObject.getString("tcgplayer_price");
                        }
                        catch (Exception e){
                            cardPrice = null;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Move to the next item.
                    i++;
                }
                if (name != null && imageURL != null && cardType != null && cardDesc != null) {
                    mCardNameText.get().setText(name);
                    Picasso.get().load(imageURL).into(mCardImage.get());
                    if (cardLevel != null){
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0,10,0,0);
                        params.gravity = Gravity.CENTER;

                        mCardLevel.get().setLayoutParams(params);
                        mCardRace.get().setLayoutParams(params);
                        mCardAttribute.get().setLayoutParams(params);

                        String tempLevelString = "Level: ";
                        String tempRaceString = "Race: ";
                        String tempAttributeString = "Attribute: ";

                        tempLevelString = tempLevelString.concat(cardLevel);
                        tempRaceString = tempRaceString.concat(cardRace);
                        tempAttributeString = tempAttributeString.concat(cardAttribute);

                        mCardLevel.get().setText(tempLevelString);
                        mCardRace.get().setText(tempRaceString);
                        mCardAttribute.get().setText(tempAttributeString);
                    }

                    if (cardLinkVal != null){
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0,10,0,0);
                        params.gravity = Gravity.CENTER;

                        mCardLevel.get().setLayoutParams(params);
                        mCardRace.get().setLayoutParams(params);
                        mCardAttribute.get().setLayoutParams(params);

                        String tempLinkString = "Link Value: ";
                        String tempRaceString = "Race: ";
                        String tempAttributeString = "Attribute: ";

                        tempLinkString = tempLinkString.concat(cardLinkVal);
                        tempRaceString = tempRaceString.concat(cardRace);
                        tempAttributeString = tempAttributeString.concat(cardAttribute);

                        mCardLevel.get().setText(tempLinkString);
                        mCardRace.get().setText(tempRaceString);
                        mCardAttribute.get().setText(tempAttributeString);
                    }

                    if (cardLinkVal == null && cardLevel == null) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0,0,0,0);
                        params.height = 0;
                        params.width = 0;

                        mCardLevel.get().setLayoutParams(params);
                        mCardRace.get().setLayoutParams(params);
                        mCardAttribute.get().setLayoutParams(params);
                    }
                    mCardTypeText.get().setText(cardType);
                    mCardDescText.get().setText(cardDesc);
                    if (cardArch != null) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0,10,0,0);
                        params.gravity = Gravity.CENTER;

                        mCardArchetype.get().setLayoutParams(params);

                        String tempCardArch = "Card Archetype: ";
                        tempCardArch = tempCardArch.concat(cardArch);

                        mCardArchetype.get().setText(tempCardArch);
                    }
                    else{
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0,0,0,0);
                        params.height = 0;
                        params.width = 0;

                        mCardArchetype.get().setLayoutParams(params);
                    }

                    if (cardPrice != null) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0,10,0,0);
                        params.gravity = Gravity.CENTER;

                        mCardPrice.get().setLayoutParams(params);

                        String tempCardPrice = "Card Price: $";
                        tempCardPrice = tempCardPrice.concat(cardPrice);

                        mCardPrice.get().setText(tempCardPrice);
                    }
                    else{
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0,0,0,0);
                        params.height = 0;
                        params.width = 0;

                        mCardPrice.get().setLayoutParams(params);
                    }

                }
            } else {
                // If none are found, update the UI to
                // show failed results.
                Log.d("test","item not found?");
                mCardNameText.get().setText(R.string.no_results);
                Picasso.get().load(R.drawable.card_blank).into(mCardImage.get());
                mCardLevel.get().setText("");
                mCardRace.get().setText("");
                mCardTypeText.get().setText("");
                mCardDescText.get().setText("");
                mCardArchetype.get().setText("");
                mCardPrice.get().setText("");
            }
        } catch (JSONException e) {
            Log.d("test","found nothing");
            e.printStackTrace();
            mCardNameText.get().setText(R.string.no_results);
            mCardAttribute.get().setText("");
            mCardLevel.get().setText("");
            mCardRace.get().setText("");
            mCardTypeText.get().setText("");
            mCardDescText.get().setText("");
            mCardArchetype.get().setText("");
            mCardPrice.get().setText("");
            e.printStackTrace();
        }
    }
}