package marinaaaniram.android_instavk.model.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

    DBHelper dbHelper;
    SQLiteDatabase db;

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = dbHelper.getWritableDatabase();
        long rowID = db.insert("test_table", null, values);
        Uri resultUri = ContentUris.withAppendedId(Uri.parse("content://aaa/test_table"), rowID);
        return resultUri;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
            db = dbHelper.getWritableDatabase();
            Cursor cursor = db.query("test_table", projection, selection,
                    selectionArgs, null, null, sortOrder);
            return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "test_DB", null, 1);
        }

        public void onCreate(SQLiteDatabase db) {

            Log.d("VkWebViewClient",  "new database");

            db.execSQL("CREATE TABLE test_table (_id integer primary key autoincrement," +
                                                "title text unique," +
                                                "size integer," +
                                                "thumb_src text);");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
