package User;

import java.io.Serializable;

public class User implements Serializable {

    private String login;
    private String password;
    private int score;
    private int bestScore;
    private double bestEfficiency;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBestScore() { return bestScore; }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public double getBestEfficiency() {
        return bestEfficiency;
    }

    public void setBestEfficiency(double bestEfficiency) {
        this.bestEfficiency = bestEfficiency;
    }

}

