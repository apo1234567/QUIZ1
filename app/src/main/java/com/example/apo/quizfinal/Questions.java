package com.example.apo.quizfinal;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class Questions extends FragmentActivity implements Question.CommunicationChannel,Questions_Selector.Communictation{

    Boolean multiplayer;
    Boolean atplayertwo;
    String[] userone = new String[2];


    int score = 0;

    boolean cheated = false;

    String[] questions = {"What is 0+1?", "What is 1+1?", "What is 2+1?", "What is 2+2?", "What is 7+1?", "What is 4+1?", "What is 8+1?"};
    final String[] answers = {"1", "2", "3", "4", "8", "5", "9"};
    final List<Integer> questionsids=new ArrayList<Integer>();
    User user=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Intent myGotoquestionactivity = getIntent(); // gets the previously created intent

        multiplayer = myGotoquestionactivity.getBooleanExtra("multiplayer", false);
        atplayertwo = myGotoquestionactivity.getBooleanExtra("atplayertwo", false);
        userone = myGotoquestionactivity.getStringArrayExtra("userone");


        user = (User) myGotoquestionactivity.getSerializableExtra("user");


        final EditText inputanswer = (EditText) findViewById(R.id.inputanswer);

        final Button viewbtn = (Button) findViewById((R.id.viewbtn));
        viewbtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           changeShowQuestions();
                                       }
                                   }
        );

        questionsids.add(1);
        questionsids.add(2);
        questionsids.add(3);
        questionsids.add(4);
        questionsids.add(5);
        questionsids.add(6);
        questionsids.add(7);

        changeQuestionById(2);


    }

    public void changeQuestionById(int qid){
        if(questionsids.size()==0){
            Intent gotofinalscreen = new Intent(Questions.this, FinalScreen.class);
            gotofinalscreen.putExtra("score", score);
            gotofinalscreen.putExtra("user", user);

            gotofinalscreen.putExtra("multiplayer", multiplayer );
            gotofinalscreen.putExtra("userone", userone);

            gotofinalscreen.putExtra("atplayertwo", atplayertwo);

            startActivity(gotofinalscreen);
        }else {
            qid -= 1;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Question questionone = Question.newInstance(questionsids.indexOf(qid+1), questions[qid], answers[qid]);
            ft.replace(R.id.question_holder, questionone);
            ft.commit();
        }

    }
    public void changeShowQuestions(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        String[] arr=new String[questionsids.size()];
        for(int i=0;i<questionsids.size();i++){
            arr[i]=questions[questionsids.get(i)-1];
        }
        Questions_Selector qs=Questions_Selector.newInstance(arr);
        ft.replace(R.id.question_holder, qs);
        ft.commit();
    }
    @Override
    public void setCommunication(String choice,String answer) {
        Toast.makeText(getApplicationContext(), "Your score so far is: " + choice, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNextButton(boolean correctornot,int qid) {
        score+= correctornot ? 1:0;
        questionsids.remove(qid);
        changeQuestionById(questionsids.size()>0 ? questionsids.get(0):0);
    }

    @Override
    public void setCheatButton(int qid) {
        questionsids.remove(qid);
        changeQuestionById(questionsids.size()>0 ? questionsids.get(0):0);

    }

    @Override
    public void setSkipButton(int qid) {
        if (questionsids.size()>1) {
            if(qid==1){
                changeQuestionById(questionsids.get(0));
            }else{
                changeQuestionById(questionsids.get(1));

            }
        }else{
            changeQuestionById(questionsids.get(0));
        }
    }

    @Override
    public void setChoice(int id) {
        changeQuestionById(questionsids.get(id));
    }


}

