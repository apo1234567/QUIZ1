package com.example.apo.quizfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.widget.Toast;

public class StartUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

////all ok

        Intent myGotostartup = getIntent(); // gets the previously created intent
        final Boolean multiplayer = myGotostartup.getBooleanExtra("multiplayer", false);
        final Boolean atplayertwo = myGotostartup.getBooleanExtra("atplayertwo", false);
        final String[] userone = myGotostartup.getStringArrayExtra("userone");




        final User user = (User) myGotostartup.getSerializableExtra("user");


        Button start = (Button) findViewById(R.id.beginhavingfun);
        start.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                Intent gotoquestionactivity = new Intent(StartUp.this, Questions.class);

                if (multiplayer == true){
                    gotoquestionactivity.putExtra("multiplayer", true );
                }

                gotoquestionactivity.putExtra("user", user);
                gotoquestionactivity.putExtra("atplayertwo", atplayertwo);
                gotoquestionactivity.putExtra("userone", userone);

                startActivity(gotoquestionactivity);


            }
        });
    }





    }

