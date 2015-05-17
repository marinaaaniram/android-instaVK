package marinaaaniram.android_instavk.UI.fragments;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.CursorLoader;
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


public class ListAlbums extends ListFragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOADER_ID = 1;
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
                "/", MyContentProvider.TABLE_ALBUMS);
        return new CursorLoader(getActivity(), Uri.parse(uri),
                new String[]{"_id", "title", "thumb_src"}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<String> title = new ArrayList<String>();
        ArrayList<String> thumb_src = new ArrayList<String>();

        while(data.moveToNext()) {
            title.add(data.getString(data.getColumnIndex("title")));
            thumb_src.add(data.getString(data.getColumnIndex("thumb_src")));
        }
        imageAdapter.updateResults(title, thumb_src);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
