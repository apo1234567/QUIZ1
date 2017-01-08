package com.example.apo.quizfinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import java.util.List;

public class EntryPoint extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_point);



        Intent myGotoentrypoint = getIntent(); // gets the previously created intent
        final Boolean multiplayer = myGotoentrypoint.getBooleanExtra("multiplayer", false);
        final Boolean atplayertwo = myGotoentrypoint.getBooleanExtra("atplayertwo", false);
        final String[] userone = myGotoentrypoint.getStringArrayExtra("userone");

        final TextView PLAYER_MESSAGE = (TextView) findViewById(R.id.PLAYER_MESSAGE);

        final EditText USER_NAME = (EditText) findViewById(R.id.user_name);
        final EditText USER_PASS = (EditText) findViewById(R.id.user_pass);

        final Button registerbtn = (Button) findViewById(R.id.registerbtn);
        final Button loginbtn = (Button) findViewById(R.id.loginbtn);

        final DatabaseHandler db = new DatabaseHandler(this);

        if (multiplayer ==true){

            if(atplayertwo == true){
                PLAYER_MESSAGE.setText("Please enter username and password for player 2");
            }
            else {
                PLAYER_MESSAGE.setText("Please enter username and password for player 1");
            }
        }
        else{
            PLAYER_MESSAGE.setText("Please enter username and password");
        }





        registerbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)

            {

                String user_name = USER_NAME.getText().toString();
                String user_pass = USER_PASS.getText().toString();

                Log.d("Insert: ", "Inserting bla bla");
                boolean added = db.addContact(new User(user_name, user_pass));

                if (added == false){
                    Toast.makeText(getBaseContext(), "User already exists. Try to login." , Toast.LENGTH_SHORT).show();
                }

               // Toast.makeText(getBaseContext(), String.valueOf(added) , Toast.LENGTH_SHORT).show();

                Log.d("Reading: ", "Reading all users bla bla");
                List<User> users = db.getAllContacts();

                for (User cn : users)
                {
                    String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Password: " + cn.getPassword();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                }

                if (added == true) {
                    Toast.makeText(getBaseContext(), "Register Successful. Please login.", Toast.LENGTH_SHORT).show();
                }
            }


        });

        loginbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)

            {


                final String user_name = USER_NAME.getText().toString();
                String user_pass = USER_PASS.getText().toString();
                Toast.makeText(getBaseContext(), "Please wait...", Toast.LENGTH_SHORT).show();

                final User user = db.login(user_name, user_pass);


                if(user!=null)

                {
                    Toast.makeText(getBaseContext(), "Login Success\nWelcome "+user_name, Toast.LENGTH_SHORT).show();

                    Intent gotostartup = new Intent(EntryPoint.this, StartUp.class);
                    gotostartup.putExtra("user", user);

                    gotostartup.putExtra("atplayertwo", atplayertwo);
                    gotostartup.putExtra("multiplayer", multiplayer );
                    gotostartup.putExtra("userone", userone);




                    startActivity(gotostartup);
                }

                else
                {
                    Toast.makeText(getBaseContext(), "Username or password are not correct.\nPlease check your data or register.", Toast.LENGTH_SHORT).show();
                }

            }

        });





    }
}

