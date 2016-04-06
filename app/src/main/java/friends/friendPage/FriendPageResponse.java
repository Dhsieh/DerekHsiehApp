package friends.friendPage;

/**
 * Created by derekhsieh on 4/4/16.
 * Information from server that is needed
 * to fill in information on Friend Page
 * activity
 */
public class FriendPageResponse {
    private double avgHuntScore;
    private int huntsPlayed;

    public FriendPageResponse(double avgHuntScore, int huntsPlayed) {
        this.avgHuntScore = avgHuntScore;
        this.huntsPlayed = huntsPlayed;
    }

    public double getAvgHuntScore() {
        return avgHuntScore;
    }

    public void setAvgHuntScore(double avgHuntScore) {
        this.avgHuntScore = avgHuntScore;
    }

    public int getHuntsPlayed() {
        return huntsPlayed;
    }

    public void setHuntsPlayed(int huntsPlayed) {
        this.huntsPlayed = huntsPlayed;
    }
}
