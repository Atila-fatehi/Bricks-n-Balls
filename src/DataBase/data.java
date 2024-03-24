package DataBase;

import java.io.Serializable;

public class data implements Serializable {
        String PlayerName;
        int score;
        String DateTime;

    public data(String playerName, int score, String dateTime) {
        PlayerName = playerName;
        this.score = score;
        DateTime = dateTime;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }
}
