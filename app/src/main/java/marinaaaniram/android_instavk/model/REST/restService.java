package marinaaaniram.android_instavk.model.REST;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class restService extends IntentService {
//    // TODO: Rename actions, choose action names that describe tasks that this
//    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
//    private static final String ACTION_FOO = "marinaaaniram.android_instavk.model.REST.action.FOO";
//    private static final String ACTION_BAZ = "marinaaaniram.android_instavk.model.REST.action.BAZ";
//
//    // TODO: Rename parameters
//    private static final String EXTRA_PARAM1 = "marinaaaniram.android_instavk.model.REST.extra.PARAM1";
//    private static final String EXTRA_PARAM2 = "marinaaaniram.android_instavk.model.REST.extra.PARAM2";

//    /**
//     * Starts this service to perform action Foo with the given parameters. If
//     * the service is already performing a task this action will be queued.
//     *
//     * @see IntentService
//     */
//    // TODO: Customize helper method
//    public static void startActionFoo(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, restService.class);
//        intent.setAction(ACTION_FOO);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }

//    /**
//     * Starts this service to perform action Baz with the given parameters. If
//     * the service is already performing a task this action will be queued.
//     *
//     * @see IntentService
//     */
//    // TODO: Customize helper method
//    public static void startActionBaz(Context context, String param1, String param2) {
//        Intent intent = new Intent(context, restService.class);
//        intent.setAction(ACTION_BAZ);
//        intent.putExtra(EXTRA_PARAM1, param1);
//        intent.putExtra(EXTRA_PARAM2, param2);
//        context.startService(intent);
//    }

    public restService() {
        super("restService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        SharedPreferences pref = getSharedPreferences("access", Context.MODE_PRIVATE);

        if(pref.contains("access_token") && pref.contains("user_id")) {
            String user_id = pref.getString("user_id", " ");
            String access_token = pref.getString("access_token", " ");
            Log.i("VkWebViewClient ", user_id);
            Log.i("VkWebViewClient ", access_token);

            try {
                URL url = new URL("https://api.vk.com/method/users.get?" +
                        "user_id=" + user_id + "&" +
                        "v=5.29&" +
                        "access_token=" + access_token);




                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    Log.i("VkWebViewClient url", url.toString());



                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder out = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        out.append(line);
                    }
                    Log.i("VkWebViewClient out", out.toString());
                    reader.close();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }







}

//    /**
//     * Handle action Foo in the provided background thread with the provided
//     * parameters.
//     */
//    private void handleActionFoo(String param1, String param2) {
//        // TODO: Handle action Foo
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    /**
//     * Handle action Baz in the provided background thread with the provided
//     * parameters.
//     */
//    private void handleActionBaz(String param1, String param2) {
//        // TODO: Handle action Baz
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
}
