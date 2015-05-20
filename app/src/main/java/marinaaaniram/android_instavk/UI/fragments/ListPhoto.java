package marinaaaniram.android_instavk.UI.fragments;

import android.app.FragmentTransaction;
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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

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

    private String currentUser;
    private String currentAlbum;
    private List<String> photosIdList;

    private static ArrayList<String> photo_lnk;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        photosIdList = new ArrayList<String>();

        currentUser = getArguments().get("user_id").toString();
        currentAlbum = getArguments().get("album_id").toString();

        return inflater.inflate(R.layout.fragment_list, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LoaderManager.LoaderCallbacks<Cursor> mCallbacks = this;
        getLoaderManager().initLoader(LOADER_ID, null, mCallbacks);
    }

    @Override
    public void onStart() {
        super.onStart();

        ServiceHelper serviceHelper = new ServiceHelper(getActivity().getApplicationContext());
        serviceHelper.getPhotosFormAlbum(currentAlbum, currentUser);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        photoAdapter = new PhotoAdapter(getActivity(), R.layout.photos_items);
        setListAdapter(photoAdapter);

        String uri = MyContentProvider.concat_strings("content://", MyContentProvider.AUTHORITY,
                "/", MyContentProvider.TABLE_PHOTOS);

        return new CursorLoader(getActivity(), Uri.parse(uri),
                new String[]{ "_id", "id", "owner_id", "album_id", "photo_75" }, "owner_id = ?  and album_id = ?",
                new String[] {currentUser, currentAlbum }, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            photo_lnk = new ArrayList<String>();

            do {
                photo_lnk.add(data.getString(data.getColumnIndex("photo_75")));
                photosIdList.add(data.getString(data.getColumnIndex("id")));
            } while (data.moveToNext());

        }
        photoAdapter.updateResults(photo_lnk);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        BigPhoto bigPhoto = new BigPhoto();

        Bundle args = new Bundle();

        args.putString("user_id", currentUser);
        args.putString("id", photosIdList.get(position));

        bigPhoto.setArguments(args);

        FragmentTransaction fragmentTransaction = null;
        fragmentTransaction = getActivity().getFragmentManager().beginTransaction().replace(R.id.container, bigPhoto);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
