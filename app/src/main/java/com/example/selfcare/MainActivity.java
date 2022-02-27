package com.example.selfcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView name=findViewById(R.id.name);
        TextView email=findViewById(R.id.email);
        ImageView happyinessmeterButton=findViewById(R.id.happyinessmeterButton);
        happyinessmeterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HappynessMeterActivity.class);
                startActivity(intent);
            }
        });
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.exercise);
    }

    FragmentExercise fragmentExercise=new FragmentExercise();
    FragmentMindfulness fragmentMindfulness=new FragmentMindfulness();
    FragmentFood fragmentFood=new FragmentFood();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exercise:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentExercise).commit();
                return true;

            case R.id.mindfulness:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentMindfulness).commit();
                return true;

            case R.id.food:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentFood).commit();
                return true;
        }

        return false;
    }
}