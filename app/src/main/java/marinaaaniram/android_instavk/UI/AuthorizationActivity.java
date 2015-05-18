package marinaaaniram.android_instavk.UI;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.net.CookieManager;

import marinaaaniram.android_instavk.R;
import marinaaaniram.android_instavk.model.REST.RestService;
import marinaaaniram.android_instavk.model.REST.ServiceHelper;
import marinaaaniram.android_instavk.model.provider.MyContentProvider;

public class AuthorizationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        WebView wv = (WebView) this.findViewById(R.id.webView);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.setHorizontalScrollBarEnabled(false);
        wv.setVerticalScrollBarEnabled(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        class VkWebViewClient extends WebViewClient {
            public VkWebViewClient() {}

            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains("oauth.vk.com/blank.html#")) {
                    if (url.contains("error")) {
                        Log.i("VkWebViewClient", "Error to authorization!");
                    } else {
                        String access_token = url.split("#")[1].split("&")[0].split("=")[1];
                        String user_id = url.split("#")[1].split("&")[2].split("=")[1];

                        SharedPreferences pref = getSharedPreferences("access", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("access_token", access_token);
                        editor.putString("user_id", user_id);
                        editor.apply();

                        ServiceHelper serviceHelper = new ServiceHelper(getApplicationContext());
                        serviceHelper.getOwnerInfo();

                        finish();
                    }
                }
            }
        }
        wv.setWebViewClient(new VkWebViewClient());
            wv.loadUrl("https://oauth.vk.com/authorize?" +
                    "client_id=4861205&" +
                    "scope=photos&" +
                    "redirect_uri=https://oauth.vk.com/blank.html&" +
                    "display=mobile&" +
                    "v=5.29&" +
                    "response_type=token&" +
                    "revoke=1");
    }
}

