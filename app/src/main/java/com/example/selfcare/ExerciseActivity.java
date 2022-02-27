package com.example.selfcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Random;

public class ExerciseActivity extends AppCompatActivity  {
    SensorManager sensorManager;
    Sensor sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        ConstraintLayout screen=findViewById(R.id.screen);
        TextView instruction=findViewById(R.id.instruction);
        LottieAnimationView gif=findViewById(R.id.gif);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String excerise = sh.getString("exercise", "0");
        ArrayList<String> url=new ArrayList<>();
        url.add(0,"");
        url.add(1,"https://media.istockphoto.com/videos/sportsman-animation-of-a-sports-exercise-pushup-from-the-floor-video-id1310615786");
        url.add(2,"https://media.istockphoto.com/videos/woman-doing-toe-touch-crunches-workout-on-white-background-video-id1132962059");
        url.add(3,"https://media.istockphoto.com/videos/woman-doing-lunges-exercise-on-white-background-video-id1132957137");
        url.add(4,"https://ak.picdn.net/shutterstock/videos/1041914665/preview/stock-footage--d-flat-cartoon-colorful-woman-character-animation-female-workout-exercise-jumping-jacks-front.webm");
        ArrayList<String> instructions=new ArrayList<>();
        instructions .add(0,"");
        instructions.add(1,"Instruction : Please put your phone on ground in frond of your face.Touching your face to the screen would be counted as one set");
        instructions.add(2,"Instruction : Please put your phone in your hands. Starting from a position and coming back to your position will be counted as one set");
        instructions.add(3,"Instruction : Please put your phone in your hands. Starting from a position and coming back to your position will be counted as one set");
        instructions.add(4,"Instruction : Please put your phone in your hands. Starting from a position and coming back to your position will be counted as one set");



        VideoView videoView=findViewById(R.id.videoView);
        Button start=findViewById(R.id.start);

        CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished / 1000==2)
                    start.setText("Get");
                else  if(millisUntilFinished / 1000==1)
                    start.setText("Set");
                else
                    start.setText("Go!!!");

            }

            public void onFinish() {
                start.setText("PAUSE");
            }
        };

        TextView counter =findViewById(R.id.counter);
        ImageView addCout=findViewById(R.id.addCount);
        ImageView minusCount=findViewById(R.id.minusCount);
        if(!excerise.equals("0")){
            Uri uri = Uri.parse(url.get(Integer.parseInt(excerise)));
            videoView.setVideoURI(uri);
            instruction.setText(instructions.get(Integer.parseInt(excerise)));
        }
        else
        {

          int k=  (int)Math.floor(Math.random()*(4-1+1)+1);
            Uri uri = Uri.parse(url.get(k));
            videoView.setVideoURI(uri);
            instruction.setText(instructions.get(k));
        }
        videoView.setOnPreparedListener(mp -> mp.setLooping(true));
        videoView.start();
        final String[] s = {counter.getText().toString()};
        start.setOnClickListener(view -> {
            if(start.getText().equals("START"))
            {
            countDownTimer.start();
                counter.setText("0");
                addCout.setVisibility(View.INVISIBLE);
                minusCount.setVisibility(View.INVISIBLE);


                if(excerise.equals("1"))
                {  screen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int count= java.lang.Integer.parseInt(counter.getText().toString());
                        if(count==Integer.parseInt(s[0])){
                            counter.setText(s[0]);
                            screen.setAlpha(0.5f);
                            gif.setAlpha(1.0f);
                            gif.setVisibility(View.VISIBLE);
                            gif.playAnimation();
                            gif.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    gif.setVisibility(View.GONE);
                                    screen.setAlpha(1.0f);
                                    gif.setAlpha(1.0f);
                                    addCout.setVisibility(View.VISIBLE);
                                    minusCount.setVisibility(View.VISIBLE);
                                    start.setText("START");

                                }
                            });
                        }
                        else
                        counter.setText(String.valueOf(count+1));
                    }
                });
                }
                else
                {
                    Log.i("ticking",""+Long.parseLong(s[0]) * 1000);
                    new CountDownTimer((Long.parseLong(s[0]) * 1000)+3000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            counter.setText(""+millisUntilFinished / 1000);
                        }

                        public void onFinish() {
                            counter.setText(s[0]);
                            screen.setAlpha(0.5f);
                            gif.setAlpha(1.0f);
                            gif.setVisibility(View.VISIBLE);
                            gif.playAnimation();
                            gif.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    gif.setVisibility(View.GONE);
                                    screen.setAlpha(1.0f);
                                    gif.setAlpha(1.0f);
                                    addCout.setVisibility(View.VISIBLE);
                                    minusCount.setVisibility(View.VISIBLE);
                                    start.setText("START");

                                }
                            });
                        }
                    }.start();
                }



            }
            else {
                countDownTimer.cancel();
                start.setText("START");
                counter.setText(s[0]);
                addCout.setVisibility(View.VISIBLE);
                minusCount.setVisibility(View.VISIBLE);
            }
        });

        addCout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s[0] =String.valueOf(Integer.parseInt(counter.getText().toString())+1);
                counter.setText(s[0]);

            }
        });
        minusCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s[0] =String.valueOf(Integer.parseInt(counter.getText().toString())-1);
                counter.setText(s[0]);
            }
        });

//       sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
//        sensorManager.registerListener(new SensorEventListener() {
//            @Override
//            public void onSensorChanged(SensorEvent sensorEvent) {
//                float[] values= sensorEvent.values;
//                if(values[0]<0.5&&values[2]<0.5&&values[0]>-0.5&&values[1]>-0.5){
//              if((values[1]>0.5&&values[1]<1.5)||(values[1]<-0.5&&values[1]>-1.5))
//              {
//                  Log.i("sensor",""+values[0]+" : "+values[1]+" : "+values[2]);
//              }}
//
//            }
//
//            @Override
//            public void onAccuracyChanged(Sensor sensor, int i) {
//
//
//            }
//        },sensor,1000);


    }



    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
//        sensorManager.registerListener(this,
//                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//                SensorManager.SENSOR_DELAY_FASTEST);
    }





}