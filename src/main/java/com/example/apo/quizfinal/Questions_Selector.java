package com.example.apo.quizfinal;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Questions_Selector.Communictation} interface
 * to handle interaction events.
 * Use the {@link Questions_Selector#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Questions_Selector extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "questions";

    // TODO: Rename and change types of parameters
    private String[] questions;

    private Communictation mListener;

    public Questions_Selector() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param questions Parameter 1.
     * @return A new instance of fragment Questions_Selector.
     */
    // TODO: Rename and change types and number of parameters
    public static Questions_Selector newInstance(String[] questions) {
        Questions_Selector fragment = new Questions_Selector();
        Bundle args = new Bundle();
        args.putStringArray(ARG_PARAM1, questions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questions = getArguments().getStringArray(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_questions_selector, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listmata);
        ArrayList<String> questionarr = new ArrayList<String>(Arrays.asList(questions));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1, questionarr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mListener.setChoice(i);
    }

    interface Communictation{
        public void setChoice(int id);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Communictation)
        {
            mListener = (Communictation)context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
