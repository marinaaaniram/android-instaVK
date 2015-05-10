package marinaaaniram.android_instavk.UI;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import marinaaaniram.android_instavk.R;
import marinaaaniram.android_instavk.model.REST.ServiceHelper;


public class MainActivity extends ActionBarActivity{
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);


        SharedPreferences pref = getSharedPreferences("access", Context.MODE_PRIVATE);
        if (pref.getString("access_token", "") .isEmpty()) {
            Intent intent = new Intent(MainActivity.this, AuthorizationActivity.class);
            startActivity(intent);
        }
        textView.setText("ServiceHelper calling...");
        ServiceHelper serviceHelper = new ServiceHelper(getApplicationContext());
        serviceHelper.getUserAlbumsLink();


//        Button requestButton = (Button) findViewById(R.id.requestButton);
//        requestButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textView.setText("ServiceHelper calling...");
//                ServiceHelper serviceHelper = new ServiceHelper(getApplicationContext());
//                serviceHelper.getUserAlbumsLink();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu
        (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_oauth) {
            Intent intent = new Intent(MainActivity.this, AuthorizationActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_delete_table){
            getContentResolver().delete(Uri.parse("content://aaa/albums"), null, null);
            return true;
        }
        if (id == R.id.action_fake_title_to_db){
            textView.setText("fakeButton calling...");
            ContentValues cv = new ContentValues();
            cv.put("title", "FAKE_title3");
            cv.put("thumb_src", "FAKE_thumb_src3");
            getContentResolver().insert(Uri.parse("content://aaa/albums"), cv);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
