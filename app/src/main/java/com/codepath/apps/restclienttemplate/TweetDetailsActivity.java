package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailsActivity extends AppCompatActivity {

    // the movie to display
    Tweet tweet;
    TextView tvUsername;
    TextView tvBody;
    TextView tvTimestamp;
    ImageView ivProfile;
    TextView tvRtCount;
    TextView tvFavCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvBody = (TextView) findViewById(R.id.tvBody);
        tvTimestamp = (TextView) findViewById(R.id.tvTimestamp);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        tvRtCount = (TextView) findViewById(R.id.tvRtCount);
        tvFavCount = (TextView) findViewById(R.id.tvFavCount);


        // unwrap the movie passed in via intent, using its simple name as a key
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        // set all the tedxt attributes
        tvUsername.setText(tweet.user.name); ;
        tvBody.setText(tweet.body);
        tvTimestamp.setText(tweet.createdAt);

        tvRtCount.setText(Long.toString(tweet.num_retweets));
        tvFavCount.setText(Long.toString(tweet.num_likes));

        Glide.with(getApplicationContext())
                .load(tweet.user.profileImageUrl)
                .into(ivProfile);

    }
}
