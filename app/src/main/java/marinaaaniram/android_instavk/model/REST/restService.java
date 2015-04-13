package marinaaaniram.android_instavk.model.REST;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class RestService extends IntentService {
    public RestService() {

        super("restService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String urlString = intent.getStringExtra("url");
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder out = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    out.append(line);
                }
                reader.close();

                Log.i("VkWebViewClient out", out.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




//        SharedPreferences pref = getSharedPreferences("access", Context.MODE_PRIVATE);
//
//        if (pref.contains("access_token") && pref.contains("user_id")) {
//            String user_id = pref.getString("user_id", " ");
//            String access_token = pref.getString("access_token", " ");
//            Log.i("VkWebViewClient ", user_id);
//            Log.i("VkWebViewClient ", access_token);
//
//            try {
//                URL url = new URL("https://api.vk.com/method/photos.getAlbums?" +
//                        "owner_id=" + user_id + "&" +
//                        "v=5.29&" +
//                        "access_token=" + access_token);
//
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//
////                    Log.i("VkWebViewClient url", url.toString());
//
//
//                    InputStream in = new BufferedInputStream(conn.getInputStream());
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//                    StringBuilder out = new StringBuilder();
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        out.append(line);
//                    }
//                    reader.close();
//
//                    JSONObject obj = new JSONObject(out.toString());
//
//                    JSONArray arr = obj.getJSONObject("response").getJSONArray("items");
//                    for (int i = 0; i < arr.length(); i++) {
//                        String title = arr.getJSONObject(i).getString("title");
//                        String thumb_id = arr.getJSONObject(i).getString("thumb_id");
//
//                        Log.i("VkWebViewClient JSON", title);
//                        Log.i("VkWebViewClient JSON", thumb_id);
//
//
//                        try {
//                            url = new URL("https://api.vk.com/method/photos.getById?" +
//                                    "photos=" + user_id + "_" + thumb_id + "&" +
//                                    "v=5.29&" +
//                                    "access_token=" + access_token);
//
//                            conn = (HttpURLConnection) url.openConnection();
//                            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//
//                                in = new BufferedInputStream(conn.getInputStream());
//                                reader = new BufferedReader(new InputStreamReader(in));
//                                out = new StringBuilder();
//                                while ((line = reader.readLine()) != null) {
//                                    out.append(line);
//                                }
//                                reader.close();
//
//
//                                Log.i("VkWebViewClient PHOTO", out.toString());
//
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//            } catch (JSONException | IOException e) {
//                e.printStackTrace();
//            }
//        }

