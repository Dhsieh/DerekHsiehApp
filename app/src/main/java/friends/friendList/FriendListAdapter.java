package friends.friendList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import friends.friendPage.FriendPageActivity;
import derekhsieh.derekhsiehapp.R;

/**
 * Created by derekhsieh on 6/25/15.
 */
public class FriendListAdapter extends BaseAdapter {
    private List<String> friends;
    private Context context;
    private String user;

    public FriendListAdapter(Context context, String user, List<String> friends) {
        this.context = context;
        this.user = user;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.friend_list, parent, false);
        TextView friend = (TextView) convertView.findViewById(R.id.FriendName);
        friend.setText(friends.get(position));
        friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goToFriendPage = new Intent(context, FriendPageActivity.class);
                    goToFriendPage.putExtra("friend", friends.get(position));
                    goToFriendPage.putExtra("username", user);
                    context.startActivity(goToFriendPage);
                }
            }
        );
        return convertView;
    }
}
