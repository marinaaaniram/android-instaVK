package marinaaaniram.android_instavk.UI.fragments;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.CursorLoader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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


public class ListAlbums extends ListFragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOADER_ID = 1;

    private ImageAdapter imageAdapter;
    private ArrayList<String> albumsIdList;
    private String currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        albumsIdList = new ArrayList<String>();
        currentUser = getArguments().get("user_id").toString();
        ServiceHelper serviceHelper = new ServiceHelper(getActivity().getApplicationContext());
        serviceHelper.getUserAlbumsLink(currentUser);
    }

    @Override
    public void onResume() {
        super.onResume();
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
                new String[]{ "_id", "title", "thumb_src", "id" }, "owner_id = ?",
                new String[] {getArguments().get("user_id").toString()}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<String> title = new ArrayList<String>();
        ArrayList<String> thumb_src = new ArrayList<String>();

        while(data.moveToNext()) {
            title.add(data.getString(data.getColumnIndex("title")));
            thumb_src.add(data.getString(data.getColumnIndex("thumb_src")));
            albumsIdList.add(data.getString(data.getColumnIndex("id")));
        }
        imageAdapter.updateResults(title, thumb_src);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ListPhoto listPhoto = new ListPhoto();

        Bundle args = new Bundle();

        args.putString("user_id", currentUser);
        args.putString("album_id", albumsIdList.get(position));

        listPhoto.setArguments(args);

        FragmentTransaction fragmentTransaction = null;
        fragmentTransaction = getActivity().getFragmentManager().beginTransaction().replace(R.id.container, listPhoto);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
