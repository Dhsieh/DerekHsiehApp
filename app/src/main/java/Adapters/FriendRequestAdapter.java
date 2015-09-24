package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import AsyncTaskRunners.FriendRequestAsyncTaskRunner;
import Objects.FriendRequests;
import derekhsieh.derekhsiehapp.R;

/**
 * Created by derekhsieh on 6/21/15.
 */
public class FriendRequestAdapter extends BaseAdapter {
    private List<FriendRequests> dataSource;
    private final Context context;
    private final String username;
    private final FriendRequestAsyncTaskRunner taskRunner;

    public FriendRequestAdapter(Context context, List<String> requests, String username) {
        this.dataSource = getDataForView(requests);
        this.context = context;
        this.username = username;
        taskRunner = new FriendRequestAsyncTaskRunner();
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public FriendRequests getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.friend_request_list, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.FriendName);
        name.setText(dataSource.get(position).getFriend());
        Button reject = (Button) convertView.findViewById(R.id.reject);
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String friend = ((TextView) v.findViewById(R.id.FriendName)).getText().toString();
                taskRunner.execute(username, "reject", friend);

            }
        });

        Button accept = ((Button) convertView.findViewById(R.id.accept));
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String friend = ((TextView) v.findViewById(R.id.FriendName)).getText().toString();
                taskRunner.execute(username, "accept", friend);
            }
        });
        return convertView;
    }

    private List<FriendRequests> getDataForView(List<String> requests) {
        ArrayList<FriendRequests> newList = new ArrayList<>();
        for (int i = 0; i < requests.size(); i++) {
            newList.add(new FriendRequests(requests.get(i)));
        }
        return newList;
    }
}
