package marinaaaniram.android_instavk.model.REST;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import marinaaaniram.android_instavk.model.provider.MyContentProvider;


/**
 * Created by My on 13.04.15.
 */
public class ServiceHelper {
    private final Context context;
    private final SharedPreferences sharedPreferences;

    String URL_VK_API = "https://api.vk.com/method/";   // TODO write to strings.xml

    public ServiceHelper(Context applicationContext) {
        context = applicationContext;

        // TODO Почему не получается инициализировать с помощью context.getSharedPreferences?!
        sharedPreferences = applicationContext.getSharedPreferences("access", Context.MODE_PRIVATE);

    }


    public void getUserAlbumsLink(String user_id) {
        // TODO safekeeping
        if (sharedPreferences.contains("access_token") && sharedPreferences.contains("user_id")) {
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
                            new String[]{"title", "thumb_src", "id", "owner_id"});
            context.startService(intent);
        }
    }

    public void getPhotosFormAlbum(String album_id, String user_id) {
        // TODO safekeeping
        if (sharedPreferences.contains("access_token") && sharedPreferences.contains("user_id")) {
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

    public void getFriendsList() {
        // TODO safekeeping
        if (sharedPreferences.contains("access_token") && sharedPreferences.contains("user_id")) {
            String access_token = sharedPreferences.getString("access_token", " ");
            String user_id = sharedPreferences.getString("user_id", " ");
            String url = URL_VK_API + "friends.get?" +
                    "user_id=" + user_id + "&" +
                    "fields=photo_50,user_id&" +
                    "count=20&" +
                    "order=hints&" +
                    "v=5.31&" +
                    "access_token=" + access_token;

            Intent intent = new Intent(context, RestService.class);
            intent.putExtra("url", url);
            intent.putExtra("table_name", MyContentProvider.TABLE_USERS);
            intent.putExtra("interestedObjectFromJSONResponse",
                    new String[]{"first_name", "last_name", "photo_50", "id"});
            context.startService(intent);
        }
    }

    public void getOwnerInfo() {
        // TODO safekeeping
        if (sharedPreferences.contains("access_token") && sharedPreferences.contains("user_id")) {
            String access_token = sharedPreferences.getString("access_token", " ");
            String user_id = sharedPreferences.getString("user_id", " ");
            String url = URL_VK_API + "users.get?" +
                    "user_id=" + user_id + "&" +
                    "fields=photo_50&" +
                    "v=5.31&" +
                    "access_token=" + access_token;

            Intent intent = new Intent(context, RestService.class);
            intent.putExtra("url", url);
            intent.putExtra("no_items", "yes");
            intent.putExtra("table_name", MyContentProvider.TABLE_USERS);
            intent.putExtra("interestedObjectFromJSONResponse",
                    new String[]{"first_name", "last_name", "photo_50", "id"});
            context.startService(intent);
        }
    }
}
