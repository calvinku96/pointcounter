package com.example.calvin.pointcounter;

/**
 * Created by roycesanto on 6/7/2015.
 */
public class Player
{
    //Object Player
    String playername;
    int score;
    int maxscore;
    boolean usedeuce;

    public Player (String name, int initialscore, int maxscore, boolean usedeuce)
    {
        this.playername = name;
        this.score = initialscore;
        this.maxscore = maxscore;
        this.usedeuce = usedeuce;
    }
    public void resetScore()
    {
        score = 0;
    }
    public void addScore(int value)
    {
        score += value;
    }
    public void deuce(int value)
    {
        maxscore += value;
    }
    public String getName()
    {
        return playername;
    }
    public int getScore()
    {
        return score;
    }
    public int getMaxScore()
    {
        return score;
    }
    public boolean isScoreMax()
    {
        if (score == maxscore)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean matchPoint()
    {
        if (maxscore-score == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
