package marinaaaniram.android_instavk.UI;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
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


        // JUST FOR CHECK DATABASE
        Cursor cursor = getContentResolver().query(Uri.parse("content://aaa/test_table"), null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("_id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String thumb = cursor.getString(cursor.getColumnIndex("thumb_src"));
                if (thumb != null) {
                    Log.i(getString(R.string.log_tag), "_id: " + id);
                    Log.i(getString(R.string.log_tag), "title: " + title);
                    Log.i(getString(R.string.log_tag), "thumb_src" + thumb);
                    Log.i(getString(R.string.log_tag), "********************************");
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        Button requestButton = (Button) findViewById(R.id.requestButton);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("ServiceHelper calling...");
                ServiceHelper serviceHelper = new ServiceHelper(getApplicationContext());
                serviceHelper.getUserAlbumsLink();
            }
        });

        Button fakeButton = (Button) findViewById(R.id.fakeButton);
        fakeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                textView.setText("fakeButton calling...");
                ContentValues cv = new ContentValues();
                cv.put("title", "FAKE_title3");
                cv.put("thumb_src", "FAKE_thumb_src3");
                getContentResolver().insert(Uri.parse("content://aaa/test_table"), cv);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, AuthorizationActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.delte_test_table){
            getContentResolver().delete(Uri.parse("content://aaa/test_table"), null, null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
