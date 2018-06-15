package com.application.jasleen.gardentrouble;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Main Activity supports the Main Screen.
 * Has 2 animations on one rabbit and can skip to Main Menu after 4.5 extra seconds
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ImageView rabbitImage;
    long animationDuration = 4500;
    Timer timer;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = MenuActivity.makeIntent(MainActivity.this);
        rabbitImage = findViewById(R.id.imageViewRabbit);

        setUpSkipButton();
        moveRotateAnimation();

        // Timer to move to the next activity by itself
        timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                startActivity(intent);

                //kill Welcome Activity
                finish();
            }
        },8500);

    }

    private void setUpSkipButton() {
        Button btnSkip = findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i( TAG, "Clicked 'SKIP'");
                Toast.makeText(MainActivity.this, "Clicked 'SKIP'", Toast.LENGTH_SHORT)
                        .show();
                //kill timer to move to the menu activity
                if (timer!=null){
                    timer.cancel();
                }
                startActivity(intent);
                finish();
            }
        });
    }

    //Animation of Rabbit in the beginning
    private void moveRotateAnimation() {

        Log.i( TAG, "Started animations");

        //Moves horizontally
        ObjectAnimator animatorX =ObjectAnimator.ofFloat(rabbitImage,"x", 600);
        animatorX.setDuration(animationDuration);

        //Rotates
        ObjectAnimator animatorRotate = ObjectAnimator.ofFloat(rabbitImage, "rotation", 0f, 360f );
        animatorRotate.setDuration(animationDuration);

        //Rotates and moves together
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorRotate);
        animatorSet.start();

    }
}
