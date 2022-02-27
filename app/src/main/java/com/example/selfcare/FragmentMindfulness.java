package com.example.selfcare;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMindfulness#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMindfulness extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentMindfulness() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMindfulness.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMindfulness newInstance(String param1, String param2) {
        FragmentMindfulness fragment = new FragmentMindfulness();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mindfulness, container, false);
        CardView card=view.findViewById(R.id.card);
        CardView card1=view.findViewById(R.id.card1);
        CardView card2=view.findViewById(R.id.card2);
        CardView card3=view.findViewById(R.id.card3);
        CardView card4=view.findViewById(R.id.card4);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);

        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MeditationActivity.class);
                myEdit.putString("meditation", "0");
                myEdit.commit();
                startActivity(intent);
            }
        });
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MeditationActivity.class);
                myEdit.putString("meditation", "1");
                myEdit.commit();
                startActivity(intent);
            }
        });card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MeditationActivity.class);
                myEdit.putString("meditation", "2");
                myEdit.commit();
                startActivity(intent);
            }
        });card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MeditationActivity.class);
                myEdit.putString("meditation", "3");
                myEdit.commit();
                startActivity(intent);
            }
        });card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MeditationActivity.class);
                myEdit.putString("meditation", "4");
                myEdit.commit();
                startActivity(intent);
            }
        });



        return view;
    }
}