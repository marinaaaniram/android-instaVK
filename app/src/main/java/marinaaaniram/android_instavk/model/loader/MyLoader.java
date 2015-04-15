package marinaaaniram.android_instavk.model.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;


/**
 * Created by My on 09.04.15.
 */
public class MyLoader extends AsyncTaskLoader<Cursor> {
    public MyLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        return null;
    }


    // Загружает данные для Activity из SQLite

    // AsyncTaskLoader<D>

    // loadInBackground()
    // onStartLoading()
    // onStopLoading()
    // onReset()
    // onCanceled()
    // deliverResult(D results)

    // OnLoadCompleteListener<D>
    // onLoadFinished(Loader<D> loader, D result)
}
