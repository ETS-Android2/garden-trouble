package com.application.jasleen.gardentrouble;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
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

import com.application.jasleen.gardentrouble.model.Game;
import com.application.jasleen.gardentrouble.model.OptionsData;


public class GameActivity extends AppCompatActivity {

    private int NUM_ROWS;
    private int NUM_COLS;
    private int NUM_RABBITS;
    private int numberRabbitsFound =0;
    private int numberScans;
    private TextView txtNumberFound;

    private MediaPlayer scanningCellsSound;
    MediaPlayer foundRabbitSound;

    private Game startGame;
    private OptionsData optionsData;
    Button buttons[][];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Holding a reference to the optionsData object created in Options Activity
        optionsData = OptionsData.getInstance();
        NUM_COLS = optionsData.getCols(this);
        NUM_ROWS = optionsData.getRows(this);
        NUM_RABBITS = optionsData.getNumberRabbits(this);

        updateUI();
        startGame = new Game();
        startGame.generateGrid(NUM_COLS, NUM_ROWS, NUM_RABBITS); //calling generate grid here so to create it before anything else
        buttons = new Button[NUM_ROWS][NUM_COLS];
        populateButtons();

    }
    //Game Activity can make itself
    public static Intent makeGameIntent(Context context) {
        return new Intent(context, GameActivity.class);
    }

    private void updateUI() {
        txtNumberFound = findViewById(R.id.txtNumberRabbits);
        txtNumberFound.setText(getString(R.string.Number_rabbits_found, numberRabbitsFound, NUM_RABBITS)) ;

        //START OFF PLAYING FIRST
        int numberGamesPlayed = optionsData.getNumberGamesPlayed(this) + 1;
        TextView txtNumberGamesPlayed = findViewById(R.id.txtNumberGames);
        txtNumberGamesPlayed.setText(getString(R.string.Times_played, numberGamesPlayed));
        optionsData.setNumberGamesPlayed(this, numberGamesPlayed);
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
                button.setTextSize(20);
                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                button.setTypeface(boldTypeface);
                button.setTextColor(Color.BLACK);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    pulseAnimation(finalCol, finalRow);

                    // Delay button reveal
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
        if(scanningCellsSound!=null) { scanningCellsSound.release(); }
        scanningCellsSound = MediaPlayer.create(this, R.raw.scan);
        scanningCellsSound.start();
        for(int i =0; i < NUM_COLS ; i++) {
            Animation colAnimation = new AlphaAnimation(1.0f, 0.0f);
            colAnimation.setDuration(150);
            colAnimation.setStartOffset(i * 75);
            buttons[row][i].startAnimation(colAnimation);
        }
        for(int j =0; j < NUM_ROWS ; j++) {
            Animation rowAnimation = new AlphaAnimation(1.0f, 0.0f);
            rowAnimation.setDuration(150);
            rowAnimation.setStartOffset(j * 75);
            buttons[j][col].startAnimation(rowAnimation);
        }
    }

    private void gridButtonClicked(int col, int row) {
        Button button = buttons[row][col];

        //Lock Button Sizes by walking through all buttons
        lockButtonSizes();
        if (startGame.checkIfRabbit(col, row)) {

            if (startGame.rabbitCellClickedAgain(col, row)) {
                numberScans = startGame.updateScan(col, row);
                button.setText(getString(R.string.Rabbit_cell_checked, startGame.currentStateRabbits(col, row) ));
            } else {
                foundRabbitSound();
                startGame.updateCells(col, row);
                numberRabbitsFound= startGame.updateRabbitsFound();
                txtNumberFound.setText(getString(R.string.Found_rabbit_update, numberRabbitsFound, NUM_RABBITS));
                //Scale image to button
                scaleImageToButton(button);

                for (int initCol = 0; initCol < NUM_COLS; initCol++) {
                    //rename game
                    if (startGame.checkIfScanned(initCol, row)) {
                        buttons[row][initCol].setText(getString(R.string.row_current_update, startGame.currentStateRabbits(initCol, row) ));
                    }
                }
                for (int initRow = 0; initRow < NUM_ROWS; initRow++) {
                    if (startGame.checkIfScanned(col, initRow)) {
                        buttons[initRow][col].setText(getString(R.string.col_current_update, startGame.currentStateRabbits(col, initRow)));
                    }
                }
            }
        } else {
            numberScans = startGame.updateScan(col, row);
            button.setText(getString(R.string.no_rabbit_found, startGame.currentStateRabbits(col, row)));
        }
        TextView txtNumberScans = findViewById(R.id.txtNumberScans);
        txtNumberScans.setText(getString(R.string.scans_used_txt, numberScans));

        winGameMessage();

    }

    private void foundRabbitSound(){
        if(scanningCellsSound!=null) { scanningCellsSound.release(); }
        foundRabbitSound = MediaPlayer.create(this, R.raw.found_rabbit);
        foundRabbitSound.start();
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
