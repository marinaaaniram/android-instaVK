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

            db.execSQL("CREATE TABLE test_table (" +
                    "_id integer primary key autoincrement," +
                    "title text unique," +
                    "thumb_src text);");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
}
