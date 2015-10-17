package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import derekhsieh.derekhsiehapp.R;

/**
 * Created by derekhsieh on 6/25/15.
 */
public class FriendListAdapter extends BaseAdapter {
    private List<String> friends;
    private Context context;

    public FriendListAdapter(Context context, List<String> friends) {
        this.context = context;
        this.friends = friends;
    }

    @Override
    public int getCount() {
        return this.friends.size();
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.friend_list, parent, false);
        TextView friend = (TextView) convertView.findViewById(R.id.FriendName);
        friend.setText(friends.get(position));
        return convertView;
    }
}
