package mainPage;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Utils.Constants;
import derekhsieh.derekhsiehapp.R;
import friends.friendPage.FriendPageActivity;

/**
 * Created by derekhsieh on 4/23/16.
 */
public class CurrentHuntAdapter extends BaseAdapter {
    private List<Pair<String, String>> currentHunts;
    private Context context;
    private String username;

    public CurrentHuntAdapter(Context context, CurrentHuntResponse response, String username) {
        currentHunts = new ArrayList<>();
        populateCurrentHunts("Rate!", response.getToRate());
        populateCurrentHunts("Photo!", response.getToSend());
        populateCurrentHunts("Finished!", response.getToSee());
        this.context = context;
        this.username = username;
    }

    @Override
    public int getCount() {
        return currentHunts.size();
    }

    @Override
    public Object getItem(int position) {
        return currentHunts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.current_hunts_list, parent, false);
        final Pair<String, String> singleCurrentHunt = currentHunts.get(position);
        ((TextView) convertView.findViewById(R.id.HuntStatus)).setText(singleCurrentHunt.second);
        ((TextView) convertView.findViewById(R.id.FriendName)).setText(singleCurrentHunt.first);
        ((TextView) convertView.findViewById(R.id.FriendName)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToFriendPage = new Intent(context, FriendPageActivity.class);
                goToFriendPage.putExtra(Constants.username, username);
                goToFriendPage.putExtra("friend", singleCurrentHunt.first);
                context.startActivity(goToFriendPage);
            }
        });

        return convertView;
    }

    private void populateCurrentHunts(String status, List<String> list) {
        for (String friend : list) {
            currentHunts.add(new Pair<String, String>(friend, status));
        }
    }
}
