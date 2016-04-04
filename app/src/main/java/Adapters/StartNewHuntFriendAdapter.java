package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by derekhsieh on 3/8/16.
 */
public class StartNewHuntFriendAdapter extends BaseAdapter {

    private String user;
    private List<String> friends;
    private Context context;

    public StartNewHuntFriendAdapter(Context context, String user, List<String> friends){
        this.user = user;
        this.friends = friends;
        this.context = context;
    }
    @Override
    public int getCount() {
        return this.friends.size();
    }

    @Override
    public Object getItem(int i) {
        return friends.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(i,viewGroup);
        TextView friend = (TextView) view.findViewById(i);
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
        return null;
    }
}
