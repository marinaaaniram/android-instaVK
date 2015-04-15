package marinaaaniram.android_instavk.model.REST;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import java.net.URL;


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
        Log.d("VkWebViewClient service", "starting...");
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

                String[] jsonObjects = intent.getStringArrayExtra("interestedObjectFromJSONResponse");
                JSONObject obj = new JSONObject(out.toString());
                JSONArray arr = obj.getJSONObject("response").getJSONArray("items");
                for (int i = 0; i < arr.length(); i++) {
                    ContentValues cv = new ContentValues();
                    cv.put(jsonObjects[0], arr.getJSONObject(i).getString(jsonObjects[0]));
                    cv.put(jsonObjects[1], arr.getJSONObject(i).getString(jsonObjects[1]));
                    cv.put(jsonObjects[2], arr.getJSONObject(i).getString(jsonObjects[2]));
                    getContentResolver().insert(Uri.parse("content://aaa/test_table"), cv);
                }

                Cursor cursor = getContentResolver().query(Uri.parse("content://aaa/test_table"),
                        null, null, null, null);
                while (cursor.moveToNext()) {
                    Log.d("VkWebViewClient cursor", cursor.getString(cursor.getColumnIndex(jsonObjects[0])));
                    Log.d("VkWebViewClient cursor", cursor.getString(cursor.getColumnIndex(jsonObjects[1])));
                    Log.d("VkWebViewClient cursor", cursor.getString(cursor.getColumnIndex(jsonObjects[2])));
                }
                cursor.close();

            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}

