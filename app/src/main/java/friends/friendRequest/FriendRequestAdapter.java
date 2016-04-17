package friends.friendRequest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Utils.Constants;
import Utils.RetroFit.RetroFitInterface;
import Utils.RetroFit.ToPost;
import derekhsieh.derekhsiehapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by derekhsieh on 6/21/15.
 */
public class FriendRequestAdapter extends BaseAdapter {
    private List<String> friendRequests;
    private final Context context;
    private final String username;
    private int friendRequestCount;
    private ButtonClickListener listener;

    public FriendRequestAdapter(Context context, List<String> requests, String username, ButtonClickListener listener) {
        this.friendRequests = requests;
        this.friendRequestCount = friendRequests.size();
        this.context = context;
        this.username = username;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return friendRequests.size();
    }

    @Override
    public Object getItem(int position) {
        return friendRequests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.friend_request_list, parent, false);
        TextView name = (TextView) convertView.findViewById(R.id.RequesteeName);
        name.setText(friendRequests.get(position));
        final Button reject = (Button) convertView.findViewById(R.id.reject);
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String friend = (String) getItem(position);
                Retrofit retrofit = RetroFitInterface.createRetroFit();
                ToPost toPost = retrofit.create(ToPost.class);
                Call<Boolean> call = toPost.postFriendRequestResponse("FriendRequestResponse",
                        new FriendRequestRequest(username, friend, false));
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful()) {
                            friendRequestCount--;
                            listener.onButtonClick(friend);
                        } else {
                            Toast.makeText(v.getContext(), Constants.errorMessage, Toast.LENGTH_SHORT);
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Toast.makeText(v.getContext(), Constants.errorMessage, Toast.LENGTH_SHORT);
                    }
                });
            }
        });

        Button accept = ((Button) convertView.findViewById(R.id.accept));
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String friend = (String) getItem(position);
                Retrofit retrofit = RetroFitInterface.createRetroFit();
                ToPost toPost = retrofit.create(ToPost.class);
                Call<Boolean> call = toPost.postFriendRequestResponse("FriendRequestResponse",
                        new FriendRequestRequest(username, friend, true));
                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful()) {
                            friendRequestCount--;
                            listener.onButtonClick(friend);
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });
            }
        });
        return convertView;
    }

}
