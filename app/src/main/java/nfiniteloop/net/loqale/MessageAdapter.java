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

    Context context;
    List<MessageItem> rowItem;

    MessageAdapter(Context context, List<MessageItem> rowItem) {
        super(context,R.layout.loqale_messages, rowItem);
        this.rowItem = new ArrayList<MessageItem>();
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
            convertView = inflater.inflate(R.layout.loqale_messages, null);
        }

        ImageView picIcon = (ImageView) convertView.findViewById(R.id.list_pic);
        TextView txtUsername = (TextView) convertView.findViewById(R.id.list_username);
        TextView txtMsg = (TextView) convertView.findViewById(R.id.list_msg);

        MessageItem item = getItem(position);
        picIcon.setImageDrawable(item.pic);
        txtUsername.setText(item.username);
        txtMsg.setText(item.message);

        return convertView;

    }

}
