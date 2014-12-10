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
 * Created by vaek on 12/1/14.
 */
public class MessageAdapter extends ArrayAdapter<MessageItem> {

    private Context mContext;
    private List<MessageItem> mRowItem;

    MessageAdapter(Context context, List<MessageItem> rowItem) {
        super(context,R.layout.loqale_messages, rowItem);
        this.mRowItem = new ArrayList<MessageItem>();
        this.mRowItem.addAll(rowItem);
        this.mContext = context;
    }

    @Override
    public int getCount() {

        return mRowItem.size();
    }

    @Override
    public long getItemId(int position) {

        return mRowItem.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.loqale_messages, null);
        }

        MessageItem item = getItem(position);
        ((ImageView) convertView.findViewById(R.id.list_pic)).setImageDrawable(item.getPicture());
        ((TextView) convertView.findViewById(R.id.list_username)).setText(item.getUsername());
        ((TextView) convertView.findViewById(R.id.list_msg)).setText(item.getMessage());
        return convertView;

    }

}
