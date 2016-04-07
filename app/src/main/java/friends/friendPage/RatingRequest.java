package friends.friendPage;

/**
 * Created by derekhsieh on 4/6/16.
 */
public class RatingRequest {
    private String username;
    private String friend;
    private Float topic;
    private long updateTime;

    public RatingRequest(String username, String friend, Float topic, long updateTime) {
        this.username = username;
        this.friend = friend;
        this.topic = topic;
        this.updateTime = updateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public Float getTopic() {
        return topic;
    }

    public void setTopic(Float topic) {
        this.topic = topic;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
