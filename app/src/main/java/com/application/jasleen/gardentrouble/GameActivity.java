package com.application.jasleen.gardentrouble;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.application.jasleen.gardentrouble.model.Game;
import com.application.jasleen.gardentrouble.model.OptionsData;


public class GameActivity extends AppCompatActivity {
    private int numberGamesPlayed=0;
    private static final String NUM_GAMES_PREF_NAME = "Num Games Played";
    private static final String GAMES_PREFS_NAME = "AppGamesPrefs";

    private int NUM_ROWS;
    private int NUM_COLS;
    private int NUM_RABBITS;
    private int numberRabbitsFound =0;
    private int numberScans;
    private TextView txtNumberFound;

    private Game startGame;
    private OptionsData optionsData;
    Button buttons[][];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Holding a reference to the optionsData object created in Options Activity
        optionsData = OptionsData.getInstance();
/*      //THIS IS NOT NEEDED THEN? Not using Singleton here but kind of using it later?
        NUM_ROWS = optionsData.getRows();
        NUM_COLS = optionsData.getCols();
*/
        refreshScreen();
        startGame = new Game();
        startGame.generateGrid(NUM_COLS, NUM_ROWS, NUM_RABBITS); //calling generate grid here so to create it before anything else
        buttons = new Button[NUM_ROWS][NUM_COLS];
        populateButtons();

    }

    private void refreshScreen() {

        //Refresh col size
        NUM_COLS = optionsData.getCols(this);
        //optionsData.setCols(NUM_COLS);

        //Refresh row size
        NUM_ROWS = optionsData.getRows(this);
        //optionsData.setRows(NUM_ROWS);

        //Refresh number of mines display
        txtNumberFound = findViewById(R.id.txtNumberRabbits);
        NUM_RABBITS = optionsData.getNumberRabbits(this);
        //optionsData.setNumberRabbits(NUM_RABBITS);
        txtNumberFound.setText("Found " + numberRabbitsFound + " of " + NUM_RABBITS);

        updateUI();
    }


    private void updateUI(){
        //START OFF PLAYING FIRST
        if (optionsData.getEraseGamesPlayed()){
            numberGamesPlayed =0;
        }
        else{
            numberGamesPlayed = getNumberGamesPlayed(this);
        }
        numberGamesPlayed++;
        TextView txtNumberGamesPlayed = findViewById(R.id.txtNumberGames);
        txtNumberGamesPlayed.setText("Times Played: " + numberGamesPlayed);
        saveNumberGamesPlayed(numberGamesPlayed);

    }
    private void saveNumberGamesPlayed(int numberGamesPlayed){
        //int totalNumberGamesPlayed = numberGamesPlayed + getNumberGamesPlayed(this);
        SharedPreferences prefs = this.getSharedPreferences(GAMES_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NUM_GAMES_PREF_NAME, numberGamesPlayed);//Act as key value
        editor.apply();
    }

    static public int getNumberGamesPlayed(Context context){
        SharedPreferences prefs = context.getSharedPreferences(GAMES_PREFS_NAME, MODE_PRIVATE);
        return prefs.getInt(NUM_GAMES_PREF_NAME, 0);

    }
    public static Intent makeGameIntent(Context context) {
        return new Intent(context, GameActivity.class);
    }

    private void populateButtons() {
        //Table of Buttons we are working with in grid game
        TableLayout table = findViewById(R.id.tableForButtons);
        for (int row = 0; row < NUM_ROWS; row++) {
            //for every row add a new table
            TableRow tableRow = new TableRow(this); //for the activity I'm working in

            //Setting the table layout to fill out in the screen
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));  //weight of how to scale it
            table.addView(tableRow);

            //for each column to go through add a button
            for (int col = 0; col < NUM_COLS; col++) {
                final int finalRow = row;
                final int finalCol = col;
                Button button = new Button(this);

                //Setting the buttons to fill out in the screen
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));  //weight of how to scale it

                //Make text not cut off on small buttons
                button.setPadding(0, 0, 0, 0);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pulseAnimation(finalCol, finalRow);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                gridButtonClicked(finalCol, finalRow);
                            }
                        }, 1300);
                    }
                });
                tableRow.addView(button); //adding button to the rows
                //Having access to the button
                buttons[row][col] = button;

            }
        }
    }

    private void pulseAnimation(int col, int row) {
        final MediaPlayer scanningCellsSound = MediaPlayer.create(this, R.raw.scan);
        scanningCellsSound.start();
        Animation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(150);
        for(int i =0; i < NUM_COLS ; i++) {
            animation.setStartOffset(i * 75);
            buttons[row][i].startAnimation(animation);
        }
        for(int j =0; j < NUM_ROWS ; j++) {
            animation.setStartOffset(j * 75);
            buttons[j][col].startAnimation(animation);
        }
    }

    private void gridButtonClicked(int col, int row) {

        final MediaPlayer foundRabbitSound = MediaPlayer.create(this, R.raw.found_rabbit);
        Button button = buttons[row][col];

        //Lock Button Sizes by walking through all buttons
        lockButtonSizes();
        if (startGame.checkIfRabbit(col, row)) {

            if (startGame.rabbitCellClickedAgain(col, row)) {
                numberScans = startGame.updateScan(col, row);
                button.setText("" + startGame.currentStateRabbits(col, row));
            } else {
                foundRabbitSound.start();
                startGame.updateCells(col, row);
                numberRabbitsFound= startGame.updateRabbitsFound();
                txtNumberFound.setText("Found " + numberRabbitsFound + " of " + NUM_RABBITS);
                //Scale image to button
                scaleImageToButton(button);

                for (int initCol = 0; initCol < NUM_COLS; initCol++) {
                    //rename game
                    if (startGame.checkIfScanned(initCol, row)) {
                        buttons[row][initCol].setText("" + startGame.currentStateRabbits(initCol, row));
                    }
                }
                for (int initRow = 0; initRow < NUM_ROWS; initRow++) {
                    if (startGame.checkIfScanned(col, initRow)) {
                        buttons[initRow][col].setText("" + startGame.currentStateRabbits(col, initRow));
                    }
                }
            }
        } else {
            numberScans = startGame.updateScan(col, row);
            //startGame.scanCell(col, row);
            button.setText("" + startGame.currentStateRabbits(col, row));
        }
        TextView txtNumberScans = findViewById(R.id.txtNumberScans);
        txtNumberScans.setText("# Scans Used: " + numberScans);

        winGameMessage();

    }

    private void winGameMessage(){
        if(numberRabbitsFound == NUM_RABBITS){
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            MessageFragment dialog = new MessageFragment();
            dialog.show(manager, "MessageDialog");

            Log.i("GameActivity", "Just show the dialog");
        }
    }

    private void lockButtonSizes() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Button button = buttons[row][col];

                //These prevent the button from deforming after clicking others
                int width = button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height = button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    private void scaleImageToButton(Button button) {
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hello_bunny);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitmap));

    }
}
