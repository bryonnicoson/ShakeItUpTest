package com.bryonnicoson.shakeituptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yelp.clientlib.entities.Category;
import com.yelp.clientlib.entities.SearchResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    SearchResponse searchResponse;
    @BindView(R.id.iv_image) ImageView ivImage;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.iv_rating) ImageView ivRating;
    @BindView(R.id.tv_reviews) TextView tvReviewCount;
    @BindView(R.id.iv_snippet) ImageView ivSnippet;
    @BindView(R.id.tv_snippet) TextView tvSnippet;
    @BindView(R.id.tv_categories) TextView tvCategories;
    @BindView(R.id.tv_address) TextView tvAddress;
    @BindView(R.id.tv_phone) TextView tvPhone;

    @BindView(R.id.button) Button yelpButton;

    @OnClick(R.id.button)
    public void onClick() {
        new Thread(new Runnable() {
           public void run() {
               searchResponse = YelpClient.getInstance().fetch();
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {

                       int i = 5;  // manually flipping through 10 results (0-9)

                       tvPhone.setText(searchResponse.businesses().get(i).displayPhone());
                       Picasso.with(getBaseContext()).load(searchResponse.businesses().get(i).
                               imageUrl()).placeholder(R.mipmap.ic_launcher).resize(300,300).into(ivImage);
                       tvName.setText(searchResponse.businesses().get(i).name());
                       Picasso.with(getBaseContext()).load(searchResponse.businesses().get(i).
                               ratingImgUrlLarge()).resize(250,50).into(ivRating);
                       tvReviewCount.setText(String.format("%d reviews",searchResponse.businesses().get(i).reviewCount()));
                       Picasso.with(getBaseContext()).load(searchResponse.businesses().get(i).
                               snippetImageUrl()).resize(250,250).into(ivSnippet);
                       tvSnippet.setText(searchResponse.businesses().get(i).snippetText());

                       StringBuilder sb = new StringBuilder();
                       for (int j = 0; j < searchResponse.businesses().get(i).categories().size(); j++) {
                           sb.append(searchResponse.businesses().get(i).categories().get(j).name());
                           if (j+1 < searchResponse.businesses().get(i).categories().size())
                               sb.append(" \u00B7 ");
                       }
//                       for (Category category : searchResponse.businesses().get(i).categories()) {
//                           sb.append(category.name());
//                           sb.append(" ");
//                       }
                       tvCategories.setText(sb.toString());

                       sb.setLength(0);
                       for (String address : searchResponse.businesses().get(i).location().displayAddress()) {
                           sb.append(address);
                           sb.append("\n");
                       }
                       tvAddress.setText(sb.toString());
                   }
               });
           }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

}
