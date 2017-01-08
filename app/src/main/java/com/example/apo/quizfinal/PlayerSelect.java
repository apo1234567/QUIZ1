package com.example.apo.quizfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PlayerSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);


        final Button singleplayerbtn = (Button) findViewById(R.id.singleplayerbtn);
        final Button multiplayerbtn = (Button) findViewById(R.id.multiplayerbtn);

        singleplayerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoentrypoint = new Intent(PlayerSelect.this, EntryPoint.class);
                startActivity(gotoentrypoint);
            }
        });

        multiplayerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoentrypoint = new Intent(PlayerSelect.this, EntryPoint.class);
                gotoentrypoint.putExtra("multiplayer", true );

                startActivity(gotoentrypoint);
            }
        });




    }
}
