package nfiniteloop.net.loqale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vaek on 12/1/14.
 */
public class FeedAdapter extends ArrayAdapter<FeedRowItem> {

    Context context;
    List<FeedRowItem> rowItem;

    FeedAdapter(Context context, List<FeedRowItem> rowItem) {
        super(context,R.layout.loqale_feed, rowItem);
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
            convertView = inflater.inflate(R.layout.loqale_feed, null);
        }

        ImageView picIcon = (ImageView) convertView.findViewById(R.id.list_pic);
        TextView txtUsername = (TextView) convertView.findViewById(R.id.list_username);
        TextView txtMsg = (TextView) convertView.findViewById(R.id.list_msg);

        FeedRowItem item = getItem(position);
        picIcon.setImageDrawable(item.pic);
        txtUsername.setText(item.username);
        txtMsg.setText(item.message);

        return convertView;

    }

}
