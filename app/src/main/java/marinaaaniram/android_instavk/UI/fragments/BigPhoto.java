package marinaaaniram.android_instavk.UI.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import marinaaaniram.android_instavk.R;
import marinaaaniram.android_instavk.model.provider.MyContentProvider;
import marinaaaniram.android_instavk.model.utils.PhotoAdapter;

/**
 * Created by kic on 5/19/15.
 */
public class BigPhoto extends Fragment {

    private static final String action_bar_title = "Увеличенная фотография";

    private String currentUser;
    private String currentPhoto;
    private ImageView photoView;
    public Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();

        currentUser = getArguments().get("user_id").toString();
        currentPhoto = getArguments().get("id").toString();

        android.support.v7.app.ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle(action_bar_title);
        }

        return inflater.inflate(R.layout.fragment_big_photo, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart(){
        super.onStart();
        View view = this.getView();
        assert view != null;
        photoView = (ImageView)view.findViewById(R.id.big_photo);

        String id = getArguments().get("id").toString();
        String path = MyContentProvider.concat_strings("content://", MyContentProvider.AUTHORITY, "/", MyContentProvider.TABLE_PHOTOS, "/", id);

        Cursor cursor = context.getContentResolver().query(Uri.parse(path), null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do{
                int photo_src_idx = cursor.getColumnIndex("photo_604");
                String photo_src = cursor.getString(photo_src_idx);

                Picasso.with(context).load(photo_src).into(photoView);

            }while (cursor.moveToNext());
        }
        cursor.close();
    }
}