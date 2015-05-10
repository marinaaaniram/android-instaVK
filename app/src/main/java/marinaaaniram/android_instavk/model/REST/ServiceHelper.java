package marinaaaniram.android_instavk.model.REST;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import marinaaaniram.android_instavk.model.provider.MyContentProvider;


/**
 * Created by My on 13.04.15.
 */
public class ServiceHelper {
    private final Context context;
    private final SharedPreferences sharedPreferences;

    private final String REQUEST_GET_ALBUMS_ALIAS = "GET_ALBUMS";

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
                            "need_system=1&" +
                            "access_token=" + access_token;

            Intent intent = new Intent(context, RestService.class);
            intent.putExtra("url", url);
            intent.putExtra("table_name", MyContentProvider.TABLE_ALBUMS);
            intent.putExtra("interestedObjectFromJSONResponse",
                            new String[]{"title", "thumb_src", "id"});
            context.startService(intent);
        }
    }

    public void getPhotosFormAlbum(int album_id) {
        // TODO safekeeping
        if (sharedPreferences.contains("access_token") && sharedPreferences.contains("user_id")) {
            String user_id = sharedPreferences.getString("user_id", " ");
            String access_token = sharedPreferences.getString("access_token", " ");

            String url = URL_VK_API + "photos.get?" +
                    "owner_id=" + user_id + "&" +
                    "album_id=" + album_id + "&" +
                    "v=5.31&" +
                    "rev=1&" +
                    "access_token=" + access_token;

            Intent intent = new Intent(context, RestService.class);
            intent.putExtra("url", url);
            intent.putExtra("table_name", MyContentProvider.TABLE_PHOTOS);
            intent.putExtra("interestedObjectFromJSONResponse",
                    new String[]{"album_id", "id", "owner_id", "photo_75", "photo_604"});
            context.startService(intent);
        }
    }
}
