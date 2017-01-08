package com.example.apo.quizfinal;

import android.content.Intent;
import android.os.Bundle;
import android.preference.TwoStatePreference;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.List;

public class FinalScreen extends Activity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);


        final Button retrybtn = (Button) findViewById(R.id.retrybtn);


        Intent myGotofinalscreen = getIntent();


        final Boolean multiplayer = myGotofinalscreen.getBooleanExtra("multiplayer", false);
        final Boolean atplayertwo = myGotofinalscreen.getBooleanExtra("atplayertwo", false);
        final String[] userdta = myGotofinalscreen.getStringArrayExtra("userone");

        String[] userone = new String[3];


        final int score = myGotofinalscreen.getIntExtra("score", 0);

        final User user = (User) myGotofinalscreen.getSerializableExtra("user");


        final TextView scrollview = (TextView) findViewById(R.id.scrollview);
        final TextView prevscore = (TextView) findViewById(R.id.prevscore);
        final TextView winner = (TextView) findViewById(R.id.winner);
        final TextView prevscoreone = (TextView) findViewById(R.id.prevscoreone);
        final TextView prevscoretwo = (TextView) findViewById(R.id.prevscoretwo);
        final TextView prev = (TextView) findViewById(R.id.prev);


        scrollview.setMovementMethod(new ScrollingMovementMethod());
        prevscoreone.setMovementMethod(new ScrollingMovementMethod());
        prevscoretwo.setMovementMethod(new ScrollingMovementMethod());

        final DatabaseHandler db = new DatabaseHandler(this);

        final TextView scoreview = (TextView) findViewById(R.id.scoreview);


        db.addScore(user, score);
        Log.d("Insert: ", "Inserting ..");

        if(multiplayer==true){


            userone[0] = user.getName();
            userone[1] = Integer.toString(score);
            userone[2] = String.valueOf(user.getID());


            if(atplayertwo == false){

            Intent gotoentrypoint = new Intent(FinalScreen.this, EntryPoint.class);
            gotoentrypoint.putExtra("multiplayer", true);
            gotoentrypoint.putExtra("atplayertwo", true);
            gotoentrypoint.putExtra("userone", userone);

            startActivity(gotoentrypoint);}

            if(atplayertwo ==true){
            scoreview.setText("Score for "+ userdta[0] + " is "+ userdta[1] + "\nScore for "+ user.getName()+" is "+ score);

                String win;

                if (Integer.parseInt(userdta[1]) > score){
                    win = userdta[0];
                }
                else if (Integer.parseInt(userdta[1]) == score) {
                    win = "nobody. It's a tie";

                } else {
                    win = user.getName();
                }

                List<Score> scoresforplayer = db.getScore(user.getID());
                scrollview.setText("");
                prevscoretwo.setText("");


                for (int i = 0; i < scoresforplayer.size(); i++) {
                    Score sc = scoresforplayer.get(i);

                    String log = sc.getScore() + " ,at time " + sc.getDatetime() + " \n\n";
                    // Writing to TextView;
                    prevscoretwo.append(log);
                }

                List<Score> scoresforplaye = db.getScore(Integer.parseInt(userdta[2]));

                prevscoreone.setText("");


                for (int i = 0; i < scoresforplaye.size(); i++) {
                    Score sc = scoresforplaye.get(i);

                    String log = sc.getScore() + " ,at time " + sc.getDatetime() + " \n\n";
                    // Writing to TextView;
                    prevscoreone.append(log);
                }
                prev.setText("Past scores for \t\t\t\tPast scores for \n" + userdta[0]+"\t\t\t\t\t\t\t\t\t\t\t"+ user.getName());
                winner.setText("The winner is " + win + "!");

            }

        }

        if (multiplayer == false){

            List<Score> scoresforplayer = db.getScore(user.getID());
            scrollview.setText("");

            winner.setText("");
            prevscoreone.setText("");
            prevscoretwo.setText("");
            prev.setText("");


            scoreview.setText("Score for you is: " + score);
            prevscore.setText("Your previous scores are:");


            for (int i = 0; i < scoresforplayer.size(); i++) {
                Score sc = scoresforplayer.get(i);

                String log = sc.getScore() + " ,at time " + sc.getDatetime() + " \n\n";
                // Writing to TextView;
                scrollview.append(log);

            }
        }

        retrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gotoentrypoint = new Intent(FinalScreen.this, PlayerSelect.class);

                startActivity(gotoentrypoint);
            }
        });
    }




}
