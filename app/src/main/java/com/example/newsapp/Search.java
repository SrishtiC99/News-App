package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class Search extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{
    private String jsonRequest =  "https://content.guardianapis.com/search?q=";
    private NewsAdapter adapter;
    private ListView listView;
    private ProgressBar progressBar;
    private TextView emptyText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        String searchValue = intent.getStringExtra("msg_key");
        jsonRequest += searchValue;
        jsonRequest.replace(" ","%20");
        jsonRequest += "&show-tags=contributor&api-key=test";
        listView = (ListView)findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.VISIBLE);
        emptyText = (TextView)findViewById(R.id.no_news);
        LoaderManager loaderManager = getSupportLoaderManager();
        getSupportLoaderManager().initLoader(1, null, this).forceLoad();
    }
    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsLoader(this, jsonRequest);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> data) {
        progressBar.setVisibility(View.INVISIBLE);
        adapter = new NewsAdapter(this,data);
        if(adapter==null){
            emptyText.setText(R.string.no_news_found);
            return;
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                News currNews = adapter.getItem(position);
                Uri newsUri = Uri.parse(currNews.getWebUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        adapter.clear();
    }
}