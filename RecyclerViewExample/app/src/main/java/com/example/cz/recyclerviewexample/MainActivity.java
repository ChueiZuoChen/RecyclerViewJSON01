package com.example.cz.recyclerviewexample;

import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity implements ExampleAdapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;

    public static final String EXTRA_URL = "imageUrl";
    public static final  String EXTRA_CREATOR = "creatorName";
    public static  final  String EXTRA_LIKES = "likeCount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //(1)先抓RecyclerView
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList = new ArrayList<>();
        
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {

        String url = "https://pixabay.com/api/?key=9425436-46872533f7b16f45ecdf42f8b&q=corgi&image_type=photo&pretty=true";

        final JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject hit = jsonArray.getJSONObject(i);

                        String createrName = hit.getString("user");
                        String imageUrl = hit.getString("webformatURL");
                        int likeCount = hit.getInt("likes");

                        mExampleList.add(new ExampleItem(imageUrl,createrName,likeCount));
                    }

                    mExampleAdapter = new ExampleAdapter(MainActivity.this,mExampleList);
                    mRecyclerView.setAdapter(mExampleAdapter);
                    mExampleAdapter.setOnItemClickListener(MainActivity.this);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }


    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this,DetailActivity.class);
        ExampleItem clickItem = mExampleList.get(position);

        detailIntent.putExtra(EXTRA_URL,clickItem.getImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR,clickItem.getCreator());
        detailIntent.putExtra(EXTRA_LIKES,clickItem.getLikes());

        startActivity(detailIntent);
    }
}
