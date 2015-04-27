package marinaaaniram.android_instavk.UI.fragments;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;


public class ListAlbums extends ListFragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>{

    private static final int LOADER_ID = 1;
    SimpleCursorAdapter simpleCursorAdapter;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
////        return inflater.inflate(R.layout.fragment_list_albums, null);
//    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        simpleCursorAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null,
                                        new String[] {"title"}, new int[] {android.R.id.text1}, 0);
        setListAdapter(simpleCursorAdapter);


        LoaderManager.LoaderCallbacks<Cursor> mCallbacks = this;
        getLoaderManager().initLoader(LOADER_ID, null, mCallbacks);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), Uri.parse("content://aaa/test_table"),
                new String[]{"_id", "title"}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        simpleCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        simpleCursorAdapter.swapCursor(null);
    }
}
