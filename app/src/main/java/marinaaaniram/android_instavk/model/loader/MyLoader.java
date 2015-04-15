package marinaaaniram.android_instavk.model.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;


/**
 * Created by My on 09.04.15.
 */
public class MyLoader extends CursorLoader {
    Context context = null;

    public MyLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
        this.context = context;
    }


    @Override
    public Cursor loadInBackground() {

        Cursor cursor = context.getContentResolver().query(Uri.parse("content://aaa/test_table"),
                null, null, null, null);
        return cursor;
    }
}
