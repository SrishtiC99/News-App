package com.example.newsapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    public static String LOG_TAG = NewsLoader.class.getSimpleName();
    private String queryUrl;
    public NewsLoader(@NonNull Context context,String queryUrl) {
        super(context);
        this.queryUrl = queryUrl;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Nullable
    @Override
    public List<News> loadInBackground() {
        if(queryUrl == null){
            Log.e(LOG_TAG, " queryUrl == null");
            return null;
        }
        List<News> result = NewsQuery.getAllData(queryUrl);
        return result;
    }
}
