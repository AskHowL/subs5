package com.example.subs5.ViewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.subs5.Model.TV;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TVViewModel extends ViewModel {
    private static final String API_KEY = "7c8c3f8be5ec74fa991e0486d694392e";
    private MutableLiveData<ArrayList<TV>> listTV = new MutableLiveData<>();

    public static String getApiKey() {
        return API_KEY;
    }

    public MutableLiveData<ArrayList<TV>> getListTV() {
        return listTV;
    }

    public void setListTV() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TV> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/popular?api_key="+API_KEY+"&language=en-US&page=1";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tv = list.getJSONObject(i);
                        TV tvList = new TV(tv);
                        listItems.add(tvList);
                    }
                    listTV.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void searchTV(String string){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TV> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?api_key="+API_KEY+"&language=en-US&query="+string+"&page=1&include_adult=false";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tv = list.getJSONObject(i);
                        TV tvList = new TV(tv);
                        listItems.add(tvList);
                    }
                    listTV.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }
}
