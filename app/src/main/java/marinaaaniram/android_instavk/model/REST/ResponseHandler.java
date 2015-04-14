package marinaaaniram.android_instavk.model.REST;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by kic on 4/14/15.
 */
public class ResponseHandler {
    ResponseHandler(){

    }

    String handleAlbumsResponse(JSONObject response) throws JSONException {
        JSONArray album_items = response.getJSONArray("items");
        StringBuffer thumb_buffer = new StringBuffer();
        for (int i = 0; i<album_items.length(); ++i){
            JSONObject album_item = album_items.getJSONObject(i);
            thumb_buffer.append(album_item.getString("thumb_id"));
            thumb_buffer.append(",");
            Log.i(album_item.getString("thumb_id"), "");
        }
        return thumb_buffer.toString();
    }

}
