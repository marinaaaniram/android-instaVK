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
import marinaaaniram.android_instavk.model.REST.ServiceHelper;
import marinaaaniram.android_instavk.model.provider.MyContentProvider;
import marinaaaniram.android_instavk.model.utils.ImageAdapter;
import marinaaaniram.android_instavk.model.utils.PhotoAdapter;

/**
 * Created by kic on 5/19/15.
 */
public class ListPhoto extends ListFragment implements android.app.LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 3;
    private PhotoAdapter photoAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        photoAdapter = new PhotoAdapter(getActivity(), R.layout.photos_items);
        setListAdapter(photoAdapter);

        LoaderManager.LoaderCallbacks<Cursor> mCallbacks = this;
        getLoaderManager().initLoader(LOADER_ID, null, mCallbacks);
    }

    @Override
    public void onStart() {
        super.onStart();

        String currentUser = getArguments().get("user_id").toString();
        String currentAlbum = getArguments().get("album_id").toString();

        ServiceHelper serviceHelper = new ServiceHelper(getActivity().getApplicationContext());
        serviceHelper.getPhotosFormAlbum(currentAlbum, currentUser);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String uri = MyContentProvider.concat_strings("content://", MyContentProvider.AUTHORITY,
                "/", MyContentProvider.TABLE_PHOTOS);

        String currentUser = getArguments().get("user_id").toString();
        String currentAlbum = getArguments().get("album_id").toString();

        return new CursorLoader(getActivity(), Uri.parse(uri),
                new String[]{ "_id", "id", "owner_id", "album_id", "photo_75" }, "owner_id = ?  and album_id = ?",
                new String[] {currentUser, currentAlbum }, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<String> photo_lnk = new ArrayList<String>();

        while(data.moveToNext()) {
            photo_lnk.add(data.getString(data.getColumnIndex("photo_75")));
        }

        setListAdapter(photoAdapter);
        photoAdapter.updateResults(photo_lnk);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
