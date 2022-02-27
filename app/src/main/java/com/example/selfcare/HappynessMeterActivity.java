package com.example.selfcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class HappynessMeterActivity extends AppCompatActivity {
    String answer1,answer2,answer3,answer4,answer5,answer6,answer7,answer8,answer9,answer10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happyness_meter);

        AutoCompleteTextView act1=findViewById(R.id.act1);
        AutoCompleteTextView act2=findViewById(R.id.act2);
        AutoCompleteTextView act3=findViewById(R.id.act3);
        AutoCompleteTextView act4=findViewById(R.id.act4);
        AutoCompleteTextView act5=findViewById(R.id.act5);
        AutoCompleteTextView act6=findViewById(R.id.act6);
        AutoCompleteTextView act7=findViewById(R.id.act7);
        AutoCompleteTextView act8=findViewById(R.id.act8);
        AutoCompleteTextView act9=findViewById(R.id.act9);
        AutoCompleteTextView act10=findViewById(R.id.act10);
        AutoCompleteTextView act11=findViewById(R.id.act11);
        Button button=findViewById(R.id.button);

        String[] array1=getResources().getStringArray(R.array.optionSet1);
        String[] array2=getResources().getStringArray(R.array.optionSet2);
        String[] array3=getResources().getStringArray(R.array.optionSet3);

        ArrayAdapter adapter1=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,array1);
        ArrayAdapter adapter2=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,array2);
        ArrayAdapter adapter3=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,array3);

        act1.setAdapter(adapter1);
        act2.setAdapter(adapter1);
        act3.setAdapter(adapter1);
        act4.setAdapter(adapter1);
        act5.setAdapter(adapter2);
        act6.setAdapter(adapter2);
        act7.setAdapter(adapter2);
        act8.setAdapter(adapter1);
        act9.setAdapter(adapter3);
        act10.setAdapter(adapter1);
        act11.setAdapter(adapter1);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1=act1.getText().toString();
                answer2=act2.getText().toString();
                answer3=act3.getText().toString();
                answer4=act4.getText().toString();
                answer5=act5.getText().toString();
                answer6=act7.getText().toString();
                answer7=act8.getText().toString();
                answer8=act9.getText().toString();
                answer9=act10.getText().toString();
                answer10=act11.getText().toString();
                if(answer1.equals("")||answer2.equals("")||answer3.equals("")||answer4.equals("")||answer5.equals("")||answer6.equals("")||answer7.equals("")||answer8.equals("")||answer9.equals("")||answer10.equals(""))
                {
                    Toast.makeText(HappynessMeterActivity.this, "Please answer all the questions", Toast.LENGTH_SHORT).show();
                }
                else{

                    //TODO HERE DO REST OF THE STUFF
                }

            }
        });










    }
}