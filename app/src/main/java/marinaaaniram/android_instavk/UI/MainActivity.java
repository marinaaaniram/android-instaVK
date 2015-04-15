package marinaaaniram.android_instavk.UI;


import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.nio.BufferUnderflowException;

import marinaaaniram.android_instavk.R;
import marinaaaniram.android_instavk.model.REST.ServiceHelper;
import marinaaaniram.android_instavk.model.loader.MyLoader;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 1;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        LoaderManager.LoaderCallbacks<Cursor> mCallbacks = this;
        getLoaderManager().initLoader(LOADER_ID, null, mCallbacks);

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
        Log.d("VkWebViewClient onCreateLoader", Integer.toString(id));
        return new MyLoader(MainActivity.this, Uri.parse("content://aaa/test_table"),
                new String[]{"title"}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        while (data.moveToNext()) {
            Log.d("VkWebViewClient onLoadFinished", data.getString(data.getColumnIndex("title")));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
