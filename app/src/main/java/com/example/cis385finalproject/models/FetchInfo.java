package com.example.cis385finalproject.models;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cis385finalproject.R;

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
            JSONArray itemsArray = jsonObject.getJSONArray("data");
            int i = 0;
            String name = null;
            String imageURL = null;
            while (i < itemsArray.length() &&
                    (imageURL == null && name == null)) {
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("data");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    name = volumeInfo.getString("name");
                   /* imageURL = volumeInfo.getString("image_url");*/
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Move to the next item.
                i++;
            }
            if (name != null && imageURL != null) {
                mCardNameText.get().setText(name);
                try {
                    InputStream is = (InputStream) new URL(imageURL).getContent();
                    Drawable d = Drawable.createFromStream(is, "card_image");
                    mCardImage.get().setImageDrawable(d);
                } catch (Exception e) {

                }
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
