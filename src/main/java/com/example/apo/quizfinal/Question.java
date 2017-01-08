package com.example.apo.quizfinal;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Question.CommunicationChannel} interface
 * to handle interaction events.
 * Use the {@link Question#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Question extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "questionindex";
    private static final String ARG_PARAM2 = "quest";
    private static final String ARG_PARAM3 = "answer";


    private int questionindex;
    private String questiontext;
    private String answertext;

    private CommunicationChannel mListener;

    public Question() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param questionindex Id Intrebare.
     * @param quest Questions.
     * @return A new instance of fragment Question.
     */

    public static Question newInstance(int questionindex, String quest,String answer) {
        Question fragment = new Question();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, questionindex);
        args.putString(ARG_PARAM2, quest);
        args.putString(ARG_PARAM3, answer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionindex = getArguments().getInt(ARG_PARAM1);
            questiontext = getArguments().getString(ARG_PARAM2);
            answertext = getArguments().getString(ARG_PARAM3);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        TextView question = (TextView) view.findViewById(R.id.questiontxt);
        question.setText(questiontext);

        final EditText answerfield=(EditText)view.findViewById(R.id.inputanswer);

        Button nextbtn=(Button) view.findViewById(R.id.nextbtn);

        nextbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mListener.setNextButton(answerfield.getText().toString().equals(answertext),questionindex);
            }
        });
        Button cheatbnt=(Button) view.findViewById(R.id.cheatbtn);

        cheatbnt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                alertDialog.setTitle("Cheated");
                alertDialog.setMessage("The correct answer is " + answertext);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mListener.setNextButton(false,questionindex);
                            }
                        });
                alertDialog.show();
            }
        });


        Button skipButton=(Button) view.findViewById(R.id.skipbtn);
        skipButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mListener.setSkipButton(questionindex);
            }
        });

        return view;
    }


    interface CommunicationChannel
    {
        public void setCommunication(String choice,String ans);
        public void setNextButton(boolean correctornot,int qid);
        public void setCheatButton(int qid);
        public void setSkipButton(int qid);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof CommunicationChannel)
        {
            mListener = (CommunicationChannel)context;
        }
        else
        {
            throw new ClassCastException();
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
