package marinaaaniram.android_instavk.model.REST;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import marinaaaniram.android_instavk.R;
import marinaaaniram.android_instavk.model.utils.JsonParser;


public class RestService extends IntentService {
    public RestService() {
        super("restService");
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getString(R.string.log_tag), "starting...");
        String urlString = intent.getStringExtra("url");
        JSONObject response_json = null;

        try{
            response_json = JsonParser.getJsonFromUrl(urlString);

        } catch (IOException | JSONException e) {
            Log.e(JsonParser.log_json, "Can not load json");
        }

        if (response_json != null)
            try {
                //JSONArray jsonArray = json.getJSONArray("text");
                //return jsonArray.get(0).toString();

                String[] interestedObjects = intent.getStringArrayExtra("interestedObjectFromJSONResponse");
                JSONArray arr = response_json.getJSONObject("response").getJSONArray("items");

                for(int i = 0; i<arr.length();i++){

                    ContentValues cv = new ContentValues();
                    JSONObject current = arr.getJSONObject(i);

                    for(String s: interestedObjects){
                        cv.put(s, current.getString(s));
                    }
                    getContentResolver().insert(Uri.parse("content://aaa/test_table"), cv);
                }


            } catch (JSONException e) {
                Log.e(JsonParser.log_json, "Can not parse json");
            }
    }
}

