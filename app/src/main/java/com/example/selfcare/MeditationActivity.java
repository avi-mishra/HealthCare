package com.example.selfcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class MeditationActivity extends AppCompatActivity {
YouTubePlayer youTube;
boolean play=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String meditation = sh.getString("meditation", "0");
        YouTubePlayerView youTubePlayerView =findViewById(R.id.videoView);
        getLifecycle().addObserver(youTubePlayerView);
        TextView benefit=findViewById(R.id.benefit);


        ArrayList<String> code= new ArrayList<>();
        code.add(0,"");
        code.add(1,"YqOqM79McYY");
        code.add(2,"wgXefCprLm0");
        code.add(3,"6qg3r4zZE_k");
        code.add(4,"Fr5kiIygm0c");

        ArrayList<String> benefitList= new ArrayList<>();
        benefitList.add(0,"");
        benefitList.add(1,"The pose has so many benefits. Downward Dog can work out any kink in the body and bring blood flow to the brain. It also strengthens, lengthens, and energizes every one of your muscles. It’s important to learn how to do Down Dog correctly so you can truly reap all of the rewards of the pose.");
        benefitList.add(2,"Opens the hips, legs, and chest\n" +
                "Strengthens the legs, calves, abs, and knees\n" +
                "Stimulates the uro-genital system and pelvic floor\n" +
                "Strengthens and stretches the shoulder joint");
        benefitList.add(3,"Plank Pose, or Phalakasana in Sanskrit, is a strength training pose that works all major abdominal muscles, while also strengthening your shoulder, chest, neck, glute, quadriceps, and back muscles. Also known as an isometric exercise, this pose works to contract the muscles by solely holding one steady, fixed position.");
        benefitList .add(4,"Stretches your feet. Tree pose can help stretch and strengthen the ligaments and tendons in your feet.\n" +
                "Improves balance. Tree pose requires proper weight distribution and posture, which can help provide stability to your groin, thighs, hips, and pelvis.\n" +
                "Strengthens your core. Balancing your total weight on one leg requires active engagement in your core, which can help strengthen it over time and provide increased steadiness.");


        if(!meditation.equals("0"))
        {                    benefit.setText(benefitList.get(Integer.parseInt(meditation)));

            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTube=youTubePlayer;
                    String videoId = code.get(Integer.parseInt(meditation));
                    youTubePlayer.loadVideo(videoId, 0);
                    youTubePlayer.pause();
                }
            });
        }
        else
        {
            int k=  (int)Math.floor(Math.random()*(4-1+1)+1);
            benefit.setText(benefitList.get(k));

            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTube=youTubePlayer;
                    String videoId = code.get((k));
                    youTubePlayer.loadVideo(videoId, 0);
                    youTubePlayer.pause();
                }
            });

        }
        Button start= findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(play==false) {


                    if (youTube != null)
                        youTube.play();
                    else
                        Toast.makeText(MeditationActivity.this, "Wait few seconds", Toast.LENGTH_SHORT).show();
               play=true;
               start.setText("PAUSE");
                }
                else
                {
                    if (youTube != null)
                        youTube.pause();
                    else
                        Toast.makeText(MeditationActivity.this, "Wait few seconds", Toast.LENGTH_SHORT).show();
                    play=false;
                    start.setText("Start");
                }
            }
        });


    }
}