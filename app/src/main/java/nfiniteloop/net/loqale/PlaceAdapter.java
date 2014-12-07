package nfiniteloop.net.loqale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaek on 12/6/14.
 */
public class PlaceAdapter extends ArrayAdapter<PlaceItem> {

    Context context;
    List<PlaceItem> rowItem;

    PlaceAdapter(Context context, List<PlaceItem> rowItem) {
        super(context,R.layout.loqale_places, rowItem);
        this.rowItem = new ArrayList<PlaceItem>();
        this.rowItem.addAll(rowItem);
    }

    @Override
    public int getCount() {

        return rowItem.size();
    }

    @Override
    public long getItemId(int position) {

        return rowItem.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.loqale_places, null);
        }

        ImageView picCategoryIcon = (ImageView) convertView.findViewById(R.id.pic_place_category);
        TextView txtPlaceName = (TextView) convertView.findViewById(R.id.place_name);
        TextView txtPlaceDistance = (TextView) convertView.findViewById(R.id.place_distance);

        PlaceItem item = getItem(position);
        picCategoryIcon.setImageDrawable(item.picCategory);
        txtPlaceName.setText(item.placeName);
        txtPlaceDistance.setText(item.placeDistance);

        return convertView;

    }

}
