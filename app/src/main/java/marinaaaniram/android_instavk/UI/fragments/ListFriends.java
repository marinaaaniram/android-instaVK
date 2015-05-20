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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

import marinaaaniram.android_instavk.R;
import marinaaaniram.android_instavk.model.REST.ServiceHelper;
import marinaaaniram.android_instavk.model.provider.MyContentProvider;
import marinaaaniram.android_instavk.model.utils.ImageAdapter;

/**
 * Created by kic on 5/16/15.
 */

public class ListFriends extends ListFragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOADER_ID = 2;
    private ImageAdapter imageAdapter;
    private Vector<String> idFriend = new Vector<String>();

    private static ArrayList<String> first_name;
    private static ArrayList<String> last_name;
    private static ArrayList<String> title;
    private static ArrayList<String> avatar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        serviceHelper.getFriendsList();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ListAlbums listAlbums = new ListAlbums();
        Bundle args = new Bundle();
        args.putString("user_id", idFriend.get(position));
        listAlbums.setArguments(args);

        FragmentTransaction fragmentTransaction = null;
        fragmentTransaction = getActivity().getFragmentManager().beginTransaction().replace(R.id.container, listAlbums);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        imageAdapter = new ImageAdapter(getActivity(), R.layout.fragment_items);
        setListAdapter(imageAdapter);

        String uri = MyContentProvider.concat_strings("content://", MyContentProvider.AUTHORITY,
                "/", MyContentProvider.TABLE_USERS);
        return new CursorLoader(getActivity(), Uri.parse(uri),
                new String[]{"_id", "id", "first_name", "last_name", "photo_50"}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {

            first_name  = new ArrayList<String>();
            last_name   = new ArrayList<String>();
            title       = new ArrayList<String>();
            avatar      = new ArrayList<String>();

            do {
                first_name.add(data.getString(data.getColumnIndex("first_name")));
                last_name.add(data.getString(data.getColumnIndex("last_name")));
                avatar.add(data.getString(data.getColumnIndex("photo_50")));
                idFriend.addElement(data.getString(data.getColumnIndex("id")));
            } while (data.moveToNext());

            for (int i = 0; i < first_name.size(); ++i) {
                title.add(MyContentProvider.concat_strings(first_name.get(i), " ", last_name.get(i)));
            }
        }
        imageAdapter.updateResults(title, avatar);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}

