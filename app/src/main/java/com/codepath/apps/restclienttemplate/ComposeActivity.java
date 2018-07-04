package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {


    EditText etTweetText;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        etTweetText = (EditText) findViewById(R.id.etTweetText);
        client = TwitterApp.getRestClient(this);
    }

    public void onTweetItem(View v)
    {
        // Prepare intent to pass back to MainActivity
        Intent data = new Intent();
        // Pass updated item text and original position
        String text = etTweetText.getText().toString();

        // send network request
        sendNetworkRequest(text);
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the edit activity, passes intent back to main
    }

    public void sendNetworkRequest(String tweet)
    {
        client.sendTweet(tweet, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Tweet tweet = new Tweet();
                try {
                    tweet = Tweet.fromJSON(response);
                    Intent intent = new Intent(ComposeActivity.this, TimelineActivity.class);
                    intent.putExtra("tweet", Parcels.wrap(tweet));
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("TwitterClient", responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("TwitterClient", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                throwable.printStackTrace();
            }
        });

    }
}
