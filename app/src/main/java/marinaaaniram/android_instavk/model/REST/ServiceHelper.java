package marinaaaniram.android_instavk.model.REST;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by My on 13.04.15.
 */
public class ServiceHelper {
    private final Context context;
    private final SharedPreferences sharedPreferences;
    private final String REQUEST_GET_ALBUMS_ALIAS = "GET_ALBUMS";

//    private final IntentFilter intentFilter;

    String URL_VK_API = "https://api.vk.com/method/";   // TODO write to strings.xml

    public ServiceHelper(Context applicationContext) {
        context = applicationContext;

        // TODO safekeeping
        // TODO Почему не получается инициализировать с помощью context.getSharedPreferences?!
        sharedPreferences = applicationContext.getSharedPreferences("access", Context.MODE_PRIVATE);


        BroadcastReceiver testReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String request_id = intent.getStringExtra("request_alias");
                String jsonString = intent.getStringExtra("responseJSON");
                ResponseHandler handler = new ResponseHandler();
                try {
                    JSONObject json = new JSONObject(jsonString); // TODO MAKE json PARSEBLE
                    switch (request_id){
                        case REQUEST_GET_ALBUMS_ALIAS:
                            handler.handleAlbumsResponse(json);
                    }
                } catch (JSONException e) {
                    Log.i("Can not convert to json", " ");
                }

                Log.i("VkWebViewClient broadcast", jsonString);
            }
        };

        //TODO unregistered!
        IntentFilter intentFilter = new IntentFilter(RestService.REST_RESPONSE_BROADCAST);
        context.registerReceiver(testReceiver, intentFilter);
    }


    public void getUserAlbumsLink() {
        // TODO safekeeping
        if (sharedPreferences.contains("access_token") && sharedPreferences.contains("user_id")) {
            String user_id = sharedPreferences.getString("user_id", " ");
            String access_token = sharedPreferences.getString("access_token", " ");

            String url = URL_VK_API + "photos.getAlbums?" +
                            "owner_id=" + user_id + "&" +
                            "v=5.29&" +
                            "need_system=1" +
                            "access_token=" + access_token;

            Intent intent = new Intent(context, RestService.class);
            intent.putExtra("url", url);
            intent.putExtra("request_alias", REQUEST_GET_ALBUMS_ALIAS);
            context.startService(intent);
        }
    }
}
