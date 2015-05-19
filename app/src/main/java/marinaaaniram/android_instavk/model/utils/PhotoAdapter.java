package marinaaaniram.android_instavk.model.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import marinaaaniram.android_instavk.R;

/**
 * Created by kic on 5/19/15.
 */
public class PhotoAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ImageView imageView;
    private ArrayList<String> photo_src = new ArrayList<String>();

    private int layout_resource;

//    public ImageAdapter(Context context) {
//        this.context = context;
//        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }

    public PhotoAdapter(Context context, int resource) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout_resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(layout_resource, parent, false);

            imageView = (ImageView) view.findViewById(R.id.photo_item);
        }

        Picasso.with(context).load(photo_src.get(position)).into(imageView);
//        imageView.setImageResource(R.drawable.ic_launcher);

        return view;
    }

    public void updateResults(ArrayList<String> photo_src) {
        this.photo_src = photo_src;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return photo_src.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
