package login;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by derekhsieh on 4/3/16.
 */
public class LoginResponse{
    private boolean success;
    private int friendRequests;

    public LoginResponse() {
        this.success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(int friendRequests) {
        this.friendRequests = friendRequests;
    }

}
