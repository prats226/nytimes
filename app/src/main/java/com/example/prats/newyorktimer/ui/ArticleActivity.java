package com.example.prats.newyorktimer.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.prats.newyorktimer.R;

import timber.log.Timber;

public class ArticleActivity extends AppCompatActivity {

    public static final String EXTRA_URL = "extra_url";

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        String url = null;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            if (extras.containsKey(EXTRA_URL)) {
                Timber.d("Has COllection Id from intent");
                url = getIntent().getExtras().getString(EXTRA_URL);
            }
        }

        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
