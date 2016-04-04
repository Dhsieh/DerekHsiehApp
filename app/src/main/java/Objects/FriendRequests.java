package Objects;

/**
 * Created by derekhsieh on 6/18/15.
 */

//used to populate list view of friend reqeusts
public class FriendRequests {
    String friend;

    public FriendRequests() {
    }

    public FriendRequests(String friend) {
        this.friend = friend;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }
}