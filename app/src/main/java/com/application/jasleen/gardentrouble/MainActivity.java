package com.application.jasleen.gardentrouble;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageView gopherImage;
    long animationDuration = 4500;
    Timer timer;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = MenuActivity.makeIntent(MainActivity.this);
        gopherImage = findViewById(R.id.imageViewGopher);

        setUpSkipButton();
        moveRotateAnimation();

        timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                startActivity(intent);
            }
        },8500);

    }

    private void setUpSkipButton() {
        Button btnSkip = findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Clicked 'SKIP'", Toast.LENGTH_SHORT)
                        .show();
                startActivity(intent);
            }
        });
    }

    //Animation of Gopher in the beginning
    private void moveRotateAnimation() {
        //Moves horizontally
        ObjectAnimator animatorX =ObjectAnimator.ofFloat(gopherImage,"x", 600);
        animatorX.setDuration(animationDuration);

        //Rotates
        ObjectAnimator animatorRotate = ObjectAnimator.ofFloat(gopherImage, "rotation", 0f, 360f );
        animatorRotate.setDuration(animationDuration);

        //Rotates and moves together
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorRotate);
        animatorSet.start();


    }

}
