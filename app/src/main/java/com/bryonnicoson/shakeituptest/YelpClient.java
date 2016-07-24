package com.bryonnicoson.shakeituptest;

import android.os.AsyncTask;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.SearchResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by bryon on 7/23/16.
 *
 * access YelpClient instance via:
 *
 *      YelpClient yelpClient = YelpClient.getInstance();
 *      yelpClient.someMethod();
 *
 *      -OR-
 *
 *      YelpClient.getinstance().someMethod();
 */

public class YelpClient {

    private static YelpClient yelpInstance = null;
    public SearchResponse searchResponse;

    // TODO: keys to be secured *!*!*!*!
    private static final String CONSUMER_KEY = "p_Sow-jWWfC12oPsFePuCw";
    private static final String CONSUMER_SECRET = "XNlEA5HdGSdctoZ98OxkuZ_Yn90";
    private static final String TOKEN = "i6BBygoo65ahqBCI6ecGDsuQoQYHCuRB";
    private static final String TOKEN_SECRET = "g8Xn0ylAYm4UoqdoHoH96reP8Qo";

    // private empty constructor and public singleton instance access
    private YelpClient() {}

    public static YelpClient getInstance() {
        if (yelpInstance == null) {
            yelpInstance = new YelpClient();
        }
        return yelpInstance;
    }

    YelpAPIFactory yelpAPIFactory = new YelpAPIFactory(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
    YelpAPI yelpAPI = yelpAPIFactory.createAPI();

    public SearchResponse fetch() {

        Map<String, String> params = new HashMap<>();
        params.put("term", "food");
        params.put("limit", "10");

        Call<SearchResponse> call = yelpAPI.search("Chicago", params);
        try {
            searchResponse = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResponse;
    }

//    public SearchResponse fetch(String coordinates, Map<String, String> params) {
//
//        Call<SearchResponse> call = yelpAPI.search(coordinates, params);
//        try {
//            searchResponse = call.execute().body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return searchResponse;
//    }
}
