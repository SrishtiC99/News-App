package com.example.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class NewsQuery {
    public static String LOG_TAG = NewsQuery.class.getSimpleName();
    private NewsQuery() {}
    // Step 1. Form HTTP Request
    // Step 2. Send the request
    // Step 3. Receive the request and make sense of it
    // Step 4. Update the UI
    public static List<News> getAllData(String request){
        // Create an URL from the given request
        URL url = createUrl(request);
        // make HTTP request
        String jsonResponse = null;
        try {
           jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        List<News> data = getDataFromJSON(jsonResponse);
        return data;
    }
    private static URL createUrl(String request){
        URL url = null;
        try {
            url = new URL(request);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if(url == null) return jsonResponse;
        HttpURLConnection urlConnection = null;
        InputStream inputStream= null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the Book JSON results.", e);
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream)throws IOException{
        StringBuilder outputResponse = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line!=null){
                outputResponse.append(line);
                line = bufferedReader.readLine();
            }
        }
        return outputResponse.toString();
    }
    private static List<News> getDataFromJSON(String jsonResponse){
        List<News> data = new ArrayList<>();
        if(TextUtils.isEmpty(jsonResponse)) return null;
        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONObject response = root.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for(int i=0;i<results.length();i++){
                JSONObject currentObj = results.getJSONObject(i);
                String title = currentObj.getString("webTitle");
                String sectionName = currentObj.getString("sectionName");
                String webUrl = currentObj.getString("webUrl");
                String date = "";
                if(currentObj.has("webPublicationDate")){
                    date += currentObj.getString(("webPublicationDate"));
                }
                JSONArray tags = currentObj.getJSONArray("tags");
                String author = "";
                try {
                    author += tags.getJSONObject(0).getString("firstName");
                }catch (JSONException e){

                }
                author += " ";
                try {
                    author += tags.getJSONObject(0).getString("lastName");
                }catch (JSONException e){

                }
                author.replace(":"," ");
                data.add(new News(title,sectionName,webUrl,author,date));
            }
            return data;
        }catch (JSONException e){
            Log.e(LOG_TAG,"Problem parsing the JSON results",e);
        }
        return null;
    }
}
