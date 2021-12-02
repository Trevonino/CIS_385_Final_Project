package com.example.cis385finalproject.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cis385finalproject.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

public class FetchInfo extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> mCardNameText;
    private WeakReference<ImageView> mCardImage;

    public FetchInfo(TextView nameText, ImageView cardImage) {
        this.mCardNameText = new WeakReference<>(nameText);
        this.mCardImage = new WeakReference<>(cardImage);
    }


    @Override
    protected String doInBackground(String... strings) {
        return CardSearchUtil.getCardInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray dataArray = jsonObject.getJSONArray("data");
            int i = 0;
            String name = null;
            String imageURL = null;
            while (i < dataArray.length() &&
                    (imageURL == null && name == null)) {
                // Get the current item information.
                JSONObject cardInfo = dataArray.getJSONObject(i);
                JSONArray cardImage = cardInfo.getJSONArray("card_images");
                JSONObject imageURLTest = cardImage.getJSONObject(i);

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    name = cardInfo.getString("name");
                    imageURL = imageURLTest.getString("image_url");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Move to the next item.
                i++;
            }
            if (name != null && imageURL != null) {
                mCardNameText.get().setText(name);

                Picasso.get().load(imageURL).into(mCardImage.get());

            } else {
                // If none are found, update the UI to
                // show failed results.
                Log.d("test","item not found?");
                mCardNameText.get().setText(R.string.no_results);
            }
        } catch (JSONException e) {
            Log.d("test","found nothing");
            e.printStackTrace();
            mCardNameText.get().setText(R.string.no_results);
            e.printStackTrace();
        }
    }
}
