package com.example.prats.newyorktimer;

import android.app.Application;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import timber.log.Timber;

/**
 * Created by prats on 1/6/16.
 */
public class MyApplication extends Application {

    public static OkHttpClient client;
    public static Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();

        if (client == null) {
            client = new OkHttpClient();
        }

        if (picasso == null) {
            picasso = new Picasso.Builder(this).downloader(new OkHttp3Downloader(client)).build();
        }

        Timber.plant(new Timber.DebugTree());
    }
}
