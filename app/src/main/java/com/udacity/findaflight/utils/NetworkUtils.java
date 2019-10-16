package com.udacity.findaflight.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static URL buildUrl(String urlString, String returnDate, String[] arguments) {
        Uri.Builder builder = new Uri.Builder();
//        builder.scheme("https")
//                .authority("skyscanner-skyscanner-flight-search-v1.p.rapidapi.com")
//                .appendPath("apiservices")
//                .appendPath("browsedates")
//                .appendPath("v1.0");


//        for (String args : arguments) {
//            builder.appendPath(args);
//        }
//
//        if (returnDate != null) {
//            builder.appendQueryParameter("inboundpartialdate", returnDate);
//        }

        builder.scheme("https")
                .authority("api.skypicker.com")
                .appendPath("flights");

        builder
                .appendQueryParameter("fly_from", "airport:SIN")    // origin
                .appendQueryParameter("fly_to", "airport:LAX")      // destination
                .appendQueryParameter("v", "3")
                .appendQueryParameter("date_from", "01/11/2019")    // departDate
                .appendQueryParameter("date_to", "01/11/2019")      // departDate
                .appendQueryParameter("return_from", "01/12/2019")  // returnDate
                .appendQueryParameter("return_to", "01/12/2019")    // returnDate
                .appendQueryParameter("flight_type", "round") // oneway; if no returnDate
                .appendQueryParameter("selected_cabins", "M") // economy
                .appendQueryParameter("adult_hold_bag", "1")
                .appendQueryParameter("adult_hand_bag", "1")
                .appendQueryParameter("partner", "picky")
                .appendQueryParameter("partner_market", "sg")       // comment out?
                .appendQueryParameter("curr", "SGD")                // change to USD?
                .appendQueryParameter("locale", "en")
                .appendQueryParameter("max_stopovers", "0")         // comment out?
                .appendQueryParameter("select_airlines", "SQ")      // comment out?
                .appendQueryParameter("vehicle_type", "aircraft")
                .appendQueryParameter("limit", "5")                 // comment out? change limit?
                .appendQueryParameter("sort", "price")              // give options?
                .appendQueryParameter("asc", "0");                  // 0: descending order

        Uri builtUri = builder.build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI: " + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
