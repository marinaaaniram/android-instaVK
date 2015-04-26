package marinaaaniram.android_instavk.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.TextView;


import java.util.ArrayList;

import marinaaaniram.android_instavk.R;

/**
 * Created by kic on 4/26/15.
 */
public class AlbumsList extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 1;
    private ListView listview;
    private TextView text_view_albums_src;
    //private ArrayList<String> list_of_links = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //list_of_links.add("empty list");
        //final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list_of_links);
        //listview.setAdapter(adapter);

        LoaderManager.LoaderCallbacks<Cursor> mCallbacks = this;
        getLoaderManager().initLoader(LOADER_ID, null, mCallbacks);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View albums_view = inflater.inflate(R.layout.albums, container, false);
        //listview = (ListView) albums_view.findViewById(R.id.albums_list_view);
        text_view_albums_src = (TextView) albums_view.findViewById(R.id.text_albums_src);
        return albums_view;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), Uri.parse("content://aaa/test_table"),
                new String[]{"title", "thumb_src"}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        String all = "";
        while (data.moveToNext()) {
            // TODO in adapter
            //list_of_links.add(data.getString(data.getColumnIndex("thumb_src")));
            all += data.getString(data.getColumnIndex("thumb_src")) + " *** ";
        }
        text_view_albums_src.setText(all);
        //listview.deferNotifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.i(getString(R.string.log_tag), "Reset Loader");
    }
}
