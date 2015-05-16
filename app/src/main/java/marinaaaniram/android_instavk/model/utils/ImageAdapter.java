package marinaaaniram.android_instavk.model.utils;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import marinaaaniram.android_instavk.R;


public class ImageAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private TextView textView;
    private ImageView imageView;
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> img_src = new ArrayList<String>();

    private int layout_resource;

//    public ImageAdapter(Context context) {
//        this.context = context;
//        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }

    public ImageAdapter(Context context, int resource) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout_resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(layout_resource, parent, false);

            textView = (TextView) view.findViewById(R.id.item_label);
            imageView = (ImageView) view.findViewById(R.id.item_image);
        }

        textView.setText(title.get(position));
        Picasso.with(context).load(img_src.get(position)).into(imageView);
//        imageView.setImageResource(R.drawable.ic_launcher);

        return view;
    }

    public void updateResults(ArrayList<String> title, ArrayList<String> img_src) {
        this.title = title;
        this.img_src = img_src;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return title.size();
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
