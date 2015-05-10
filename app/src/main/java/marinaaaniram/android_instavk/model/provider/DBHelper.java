package marinaaaniram.android_instavk.model.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by My on 15.04.15.
 */
public class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "test_DB", null, 1);
        }

        public void onCreate(SQLiteDatabase db) {

            Log.d("VkWebViewClient", "new database");

            db.execSQL("CREATE TABLE albums (" +
                    "_id integer primary key autoincrement," +
                    "title text," +
                    "thumb_src text," +
                    "id integer unique);"); // ALBUB ID IN VK

            db.execSQL("CREATE TABLE photos (" +
                    "_id integer primary key autoincrement," +
                    "img_src_small text," +
                    "img_src_big text," +
                    "FK_ALBUM_ID integer," +
                    "FOREIGN KEY(FK_ALBUM_ID) REFERENCES albums(id));");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
}
