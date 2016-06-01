package com.example.prats.newyorktimer.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.prats.newyorktimer.MyApplication;
import com.example.prats.newyorktimer.R;
import com.example.prats.newyorktimer.data.Values;
import com.example.prats.newyorktimer.io.Doc;
import com.example.prats.newyorktimer.io.MainResponse;
import com.example.prats.newyorktimer.util.ArticleAdapter;
import com.google.gson.Gson;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    protected MyApplication app;

    static ArrayList<Doc> newsArrayList;
    static ArticleAdapter arrayAdapter;
    static int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.d("Inside main activity");

        app = (MyApplication) getApplication();

        newsArrayList = new ArrayList<>();
        arrayAdapter = new ArticleAdapter(this, newsArrayList);

        //add the view via xml or programmatically
        final SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);

        populateArticles();

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Timber.d("removed object!");
                newsArrayList.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
//                makeToast(MainActivity.this, "Disliked!");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
//                makeToast(MainActivity.this, "Liked!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                populateArticles();
                Timber.d("notified");
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
//                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
//                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
//                makeToast(MainActivity.this, "Clicked!");
            }
        });
    }

//    static void makeToast(Context ctx, String s) {
//        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
//    }

    public void populateArticles() {
        currentPage++;

        HttpUrl.Builder urlBuilder = HttpUrl
                .parse("http://api.nytimes.com/svc/search/v2/articlesearch.json")
                .newBuilder();
        urlBuilder.addQueryParameter("api-key", Values.NYTIMES_API_KEY);
        urlBuilder.addQueryParameter("sort", "newest");
        urlBuilder.addQueryParameter("page", Integer.toString(currentPage));
        String url = urlBuilder.build().toString();

        Timber.d("Url built: " + url);

        final Request request = new Request.Builder()
                .url(url)
                .build();

        // Create new gson object
        final Gson gson = new Gson();

        MyApplication.client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Timber.e(e, "Api call failed");
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }

                        MainResponse response1 = gson.fromJson(response.body().charStream(), MainResponse.class);
                        Timber.d("Response in json: " + new Gson().toJson(response1));

                        if (response1.response.docs.length > 0) {
                            for (int i = 0; i < response1.response.docs.length; i++) {
                                newsArrayList.add(response1.response.docs[i]);
                            }
                        }

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    arrayAdapter.notifyDataSetChanged();
                                } catch (Exception e) {
                                    Timber.e(e, "Cant reload");
                                }
                            }
                        });
                    }
                });
    }
}