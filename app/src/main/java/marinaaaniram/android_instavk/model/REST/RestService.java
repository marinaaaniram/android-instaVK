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

                    for (int j = 0; j < jsonObjects.length; ++j) {
                        Log.d("VkWebViewClient rest", arr.getJSONObject(i).getString(jsonObjects[j]));
                        cv.put(jsonObjects[j], arr.getJSONObject(i).getString(jsonObjects[j]));
                        getContentResolver().insert(Uri.parse("content://aaa/test_table"), cv);
                    }
                }
            }
        } catch (JSONException | IOException e1) {
            e1.printStackTrace();
        }
    }
}

