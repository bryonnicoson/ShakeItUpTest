package com.bryonnicoson.shakeituptest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yelp.clientlib.entities.SearchResponse;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    SearchResponse searchResponse;
    @BindView(R.id.iv_snippet) ImageView ivSnippet;
    @BindView(R.id.tv_name) TextView tvName;

    @BindView(R.id.button) Button yelpButton;

    @OnClick(R.id.button)
    public void onClick() {
        new Thread(new Runnable() {
           public void run() {
               searchResponse = YelpClient.getInstance().fetch();
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       Picasso.with(getBaseContext()).load(searchResponse.businesses().get(0).snippetImageUrl()).into(ivSnippet);
                       tvName.setText(searchResponse.businesses().get(0).name());
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
