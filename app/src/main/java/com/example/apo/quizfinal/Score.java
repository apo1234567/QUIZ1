package com.example.apo.quizfinal;

/**
 * Created by apo on 23/12/2016.
 */

public class Score{
    int score;
    String datetime;
    public Score(int score,String datetime){
        this.score=score;
        this.datetime=datetime;
    }
    public int getScore(){return score;}
    public String getDatetime(){return datetime;}

}