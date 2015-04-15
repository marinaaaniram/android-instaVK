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


/**
 * Created by My on 13.04.15.
 */
public class ServiceHelper {
    private final Context context;
    private final SharedPreferences sharedPreferences;

    String URL_VK_API = "https://api.vk.com/method/";   // TODO write to strings.xml

    public ServiceHelper(Context applicationContext) {
        context = applicationContext;

        // TODO safekeeping
        // TODO Почему не получается инициализировать с помощью context.getSharedPreferences?!
        sharedPreferences = applicationContext.getSharedPreferences("access", Context.MODE_PRIVATE);
    }


    public void getUserAlbumsLink() {
        // TODO safekeeping
        if (sharedPreferences.contains("access_token") && sharedPreferences.contains("user_id")) {
            String user_id = sharedPreferences.getString("user_id", " ");
            String access_token = sharedPreferences.getString("access_token", " ");

            String url = URL_VK_API + "photos.getAlbums?" +
                            "owner_id=" + user_id + "&" +
                            "need_covers=1&" +
                            "v=5.30&" +
                            "access_token=" + access_token;

            Intent intent = new Intent(context, RestService.class);
            intent.putExtra("url", url);
            intent.putExtra("interestedObjectFromJSONResponse",
                            new String[]{"title", "size", "thumb_src"});
            context.startService(intent);

        }
    }
}
