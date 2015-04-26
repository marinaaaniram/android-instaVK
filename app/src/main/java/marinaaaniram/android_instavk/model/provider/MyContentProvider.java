package marinaaaniram.android_instavk.model.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    DBHelper dbHelper;
    SQLiteDatabase db;

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        return db.delete("test_table", null, null);
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = dbHelper.getWritableDatabase();
        long rowID = db.insert("test_table", null, values);
        if (rowID > 0) {
            Uri resultUri = ContentUris.withAppendedId(Uri.parse("content://aaa/test_table"), rowID);
            getContext().getContentResolver().notifyChange(resultUri, null);
            return resultUri;
        } throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("test_table", projection, selection,
                                 selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                                  Uri.parse("content://aaa/test_table"));
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
