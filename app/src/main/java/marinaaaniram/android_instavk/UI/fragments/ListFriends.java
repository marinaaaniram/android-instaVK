package marinaaaniram.android_instavk.UI.fragments;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import marinaaaniram.android_instavk.R;
import marinaaaniram.android_instavk.model.provider.MyContentProvider;
import marinaaaniram.android_instavk.model.utils.ImageAdapter;

/**
 * Created by kic on 5/16/15.
 */

public class ListFriends extends ListFragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOADER_ID = 2;
    private ImageAdapter imageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageAdapter = new ImageAdapter(getActivity(), R.layout.fragment_items);
        setListAdapter(imageAdapter);

        LoaderManager.LoaderCallbacks<Cursor> mCallbacks = this;
        getLoaderManager().initLoader(LOADER_ID, null, mCallbacks);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String uri = MyContentProvider.concat_strings("content://", MyContentProvider.AUTHORITY,
                "/", MyContentProvider.TABLE_USERS);
        return new CursorLoader(getActivity(), Uri.parse(uri),
                new String[]{"_id", "first_name", "last_name", "photo_50"}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<String> first_name = new ArrayList<String>();
        ArrayList<String> last_name = new ArrayList<String>();
        ArrayList<String> title = new ArrayList<String>();

        ArrayList<String> avatar = new ArrayList<String>();

        while(data.moveToNext()) {
            first_name.add(data.getString(data.getColumnIndex("first_name")));
            last_name.add(data.getString(data.getColumnIndex("last_name")));
            avatar.add(data.getString(data.getColumnIndex("photo_50")));
        }

        for(int i = 0; i<first_name.size(); ++i){
            title.add(MyContentProvider.concat_strings(first_name.get(i), " ", last_name.get(i)));
        }
        setListAdapter(null);
        setListAdapter(imageAdapter);
        imageAdapter.updateResults(title, avatar);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}

