package com.example.a4501assignment;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Network {
    private static final String TAG = "NetworkUtils";

    public interface ApiCallback {
        void onSuccess(int left, int right, int guess);
        void onError(String errorMessage);
    }

    public static void fetchData(String url, final ApiCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL apiUrl = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                    connection.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));

                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // 解析JSON数据
                    JSONObject jsonObject = new JSONObject(response.toString());
                    int left = jsonObject.getInt("left");
                    int right = jsonObject.getInt("right");
                    int guess = jsonObject.getInt("guess");

                    // 通过回调返回数据
                    callback.onSuccess(left, right, guess);

                } catch (Exception e) {
                    Log.e(TAG, "Error fetching data: " + e.getMessage());
                    callback.onError("Failed to fetch data: " + e.getMessage());
                }
            }
        }).start();
    }
}
