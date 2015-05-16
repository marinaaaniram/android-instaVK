package marinaaaniram.android_instavk.model.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

public class MyContentProvider extends ContentProvider {

    public final static String AUTHORITY = "ru.android_instavk";
    public final static String TABLE_ALBUMS = "albums";
    public final static String TABLE_PHOTOS = "photos";
    public final static String TABLE_USERS = "users";

    public static final int URI_GET_ALL_ALBUMS = 1;
    public static final int URI_GET_PHOTOS_FROM_ALBUM = 2;
    public static final int URI_GET_ALL_PHOTOS = 3;
    public static final int URI_GET_ALL_USERS = 4;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TABLE_ALBUMS, URI_GET_ALL_ALBUMS );
        uriMatcher.addURI(AUTHORITY, TABLE_ALBUMS + "/#", URI_GET_PHOTOS_FROM_ALBUM);
        uriMatcher.addURI(AUTHORITY, TABLE_PHOTOS, URI_GET_ALL_PHOTOS);
        uriMatcher.addURI(AUTHORITY, TABLE_USERS, URI_GET_ALL_USERS);
    }

    private static final Map<Integer, String> columnsMatcher;

    static {
        columnsMatcher = new HashMap<>();
        columnsMatcher.put(URI_GET_ALL_ALBUMS, TABLE_ALBUMS);
        columnsMatcher.put(URI_GET_PHOTOS_FROM_ALBUM, TABLE_PHOTOS);
        columnsMatcher.put(URI_GET_ALL_PHOTOS, TABLE_PHOTOS);
        columnsMatcher.put(URI_GET_ALL_USERS, TABLE_USERS);
    }

    DBHelper dbHelper;
    SQLiteDatabase db;

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        int rowNum = 0;
        int current_uri = uriMatcher.match(uri);
        String table_name = columnsMatcher.get(current_uri);
        rowNum = db.delete(table_name, null, null);

        String uri_query = concat_strings("content://", AUTHORITY, "/", table_name);
        getContext().getContentResolver().notifyChange(Uri.parse(uri_query), null);

        return rowNum;
    }

    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = dbHelper.getWritableDatabase();

        int current_uri = uriMatcher.match(uri);
        String table_name = columnsMatcher.get(current_uri);

        long rowID = db.insert(table_name, null, values);
        // TODO throw

        String uri_query = concat_strings("content://", AUTHORITY, "/", table_name);

        Uri resultUri = ContentUris.withAppendedId(Uri.parse(uri_query), rowID);
        getContext().getContentResolver().notifyChange(resultUri, null);

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
        db = dbHelper.getReadableDatabase();

        int current_uri = uriMatcher.match(uri);
        String table_name = columnsMatcher.get(current_uri);

        Cursor cursor = db.query(table_name, projection, selection,
                                 selectionArgs, null, null, sortOrder);

        String uri_query = concat_strings("content://", AUTHORITY, "/", table_name);
        cursor.setNotificationUri(getContext().getContentResolver(),
                                  Uri.parse(uri_query));
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static String concat_strings(String... strings_set){
        StringBuilder uri_query = new StringBuilder();
        for(String s: strings_set){
            uri_query.append(s);
        }
        return uri_query.toString();
    }
}