package marinaaaniram.android_instavk.UI;


import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import marinaaaniram.android_instavk.R;
import marinaaaniram.android_instavk.model.REST.ServiceHelper;
import marinaaaniram.android_instavk.model.loader.MyLoader;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 1;
    private TextView textView;
    private TableLayout albumLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        albumLayout = (TableLayout) findViewById(R.id.albumsLayout);
        textView = (TextView) findViewById(R.id.textView);

        LoaderManager.LoaderCallbacks<Cursor> mCallbacks = this;
        getLoaderManager().initLoader(LOADER_ID, null, mCallbacks);

        // JUST FOR CHECK DATABASE START

        Cursor cursor = getContentResolver().query(Uri.parse("content://aaa/test_table"), null, null, null, null);
        cursor.moveToFirst();
        String columns[] = cursor.getColumnNames();
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
                cv.put("title", "FAKE title");
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
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyLoader(MainActivity.this, Uri.parse("content://aaa/test_table"),
                new String[]{"title"}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        TextView[] albumsTextView = new TextView[data.getCount()];

        while (data.moveToNext()) {
            // TODO in adapter
            albumsTextView[data.getCount() - 1] = new TextView(this);
            albumsTextView[data.getCount() - 1].setText(data.getString(data.getColumnIndex("title")));
            albumLayout.addView(albumsTextView[data.getCount() - 1]);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
