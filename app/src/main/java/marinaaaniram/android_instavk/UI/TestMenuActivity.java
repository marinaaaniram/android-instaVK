package marinaaaniram.android_instavk.UI;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import marinaaaniram.android_instavk.R;
import marinaaaniram.android_instavk.UI.fragments.ListAlbums;
import marinaaaniram.android_instavk.UI.fragments.ListFriends;
import marinaaaniram.android_instavk.model.REST.ServiceHelper;
import marinaaaniram.android_instavk.model.provider.MyContentProvider;

public class TestMenuActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private ServiceHelper serviceHelper;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_menu);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        mTitle = "Albums";

        serviceHelper = new ServiceHelper(getApplicationContext());
        serviceHelper.getUserAlbumsLink();      // TODO почему падает в onNavigationDrawerItemSelected?!

        // JUST FOR CHECK DATABASE
        // Info log with tag - dev_log
        show_database_data(MyContentProvider.TABLE_ALBUMS, MyContentProvider.TABLE_PHOTOS, MyContentProvider.TABLE_USERS);

        SharedPreferences pref = getSharedPreferences("access", Context.MODE_PRIVATE);
        if (pref.getString("access_token", "") .isEmpty()) {
            Intent intent = new Intent(TestMenuActivity.this, AuthorizationActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }




    @Override
    public void onNavigationDrawerItemSelected(int position) {

        FragmentTransaction fragmentTransaction = null;

        switch (position) {
            case 0:
                ListAlbums listAlbums = new ListAlbums();
                fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.container, listAlbums);
                fragmentTransaction.commit();
                mTitle = "Albums";
                break;
            case 1:
                ListFriends listFriends = new ListFriends();
                fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.container, listFriends);
                fragmentTransaction.commit();
                serviceHelper.getFriendsList();
                mTitle = "Friends";
                break;
        }

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.test_menu, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void show_database_data(String... tables){
        for(String table_name: tables){
            String path = MyContentProvider.concat_strings("content://", MyContentProvider.AUTHORITY, "/", table_name);

            Log.i(getString(R.string.log_tag), "********************************");
            Log.i(getString(R.string.log_tag), " -- TABLE : " + table_name);

            Cursor cursor = getContentResolver().query(Uri.parse(path), null, null, null, null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                do{
                    int col_count = cursor.getColumnCount();

                    for(int i = 0; i< col_count; ++i){
                        String column_name = cursor.getColumnName(i);
                        String data = cursor.getString(i);
                        Log.i(getString(R.string.log_tag), "--- " + column_name + " : " + data);
                    }
                    Log.i(getString(R.string.log_tag), "------------------------");

                }while (cursor.moveToNext());
            }
            cursor.close();
        }
    }

}


