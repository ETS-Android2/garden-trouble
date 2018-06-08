package com.application.jasleen.gardentrouble;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.application.jasleen.gardentrouble.model.Game;
import com.application.jasleen.gardentrouble.model.OptionsData;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class GameActivity extends AppCompatActivity {
    //How many mines there should be come from options

    private int NUM_ROWS;
    private int NUM_COLS;
    private int NUM_RABBITS;

    private int numberScans =0;
    private Game startGame;
    Button buttons[][];

    private OptionsData optionsData;

    //private TextView txtNumberFound;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        startGame = new Game();

        //Holding a reference to the optionsData object created in Options Activity
        optionsData = OptionsData.getInstance();
        NUM_ROWS = optionsData.getRows();
        NUM_COLS = optionsData.getCols();
        buttons = new Button[NUM_ROWS][NUM_COLS];

        NUM_RABBITS = optionsData.getNumberRabbits();

        startGame.generateGrid(); //calling generate grid here so to create it before anything else
        populateButtons();

        //txtNumberFound.setText("Found "+ numberRabbitsFound + " of " + NUM_RABBITS);
    }
    public static Intent makeGameIntent(Context context) {
        return new Intent(context, GameActivity.class);
    }

    private void populateButtons() {
        //Table of Buttons we are working with in grid game
        TableLayout table = findViewById(R.id.tableForButtons);
        for (int row=0; row < NUM_ROWS; row++){
            //for every row add a new table
            TableRow tableRow = new TableRow(this); //for the activity I'm working in

            //Setting the table layout to fill out in the screen
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));  //weight of how to scale it
            table.addView(tableRow);

            //for each column to go through add a button
            for(int col=0; col <NUM_COLS; col++){
                final int finalRow = row;
                final int finalCol = col;
                Button button = new Button(this);

                //Setting the buttons to fill out in the screen
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));  //weight of how to scale it

                //Make text not cut off on small buttons
                //button.setText(col + "," + row); // FOR NOW
                button.setPadding(0,0,0,0);
                //Wire the button to do something
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Put changing code here
                        gridButtonClicked(finalCol, finalRow);
                    }

                });
                tableRow.addView(button); //adding button to the rows
                //Having access to the button
                buttons[row][col] = button;

            }
        }
    }
    private void gridButtonClicked(int col, int row) {
        //FOR NOW WITH X AND Y
        Toast.makeText(this, "Button Clicked: " + col + "," + row, Toast.LENGTH_SHORT)
                .show();
        Button button = buttons[row][col];

        //Lock Button Sizes by walking through all buttons
        lockButtonSizes();
        if (startGame.checkIfRabbit(col, row) == TRUE) {
            //This does not scale image in button
            //button.setBackgroundResource(R.drawable.gopher_welcome);
            if (startGame.checkIfAlreadyScanned(col, row) == TRUE){
                numberScans++;
                startGame.setCellScannedTwice(col, row);
                button.setText(""+ startGame.currentStateRabbits(col, row));
            }
            else {

                startGame.updateCellScanned(col, row);
                //Scale image to button
                int newWidth = button.getWidth();
                int newHeight = button.getHeight();
                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gopher_welcome);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                Resources resource = getResources();
                button.setBackground(new BitmapDrawable(resource, scaledBitmap));

                for (int initCol = 0; initCol < NUM_COLS; initCol++) {
                    //rename game
                    if ((startGame.checkIfAlreadyScanned(initCol, row) == TRUE && startGame.checkIfRabbit(initCol, row) == FALSE)||
                            startGame.rabbitCellScannedTwice(initCol, row)== TRUE) {
                        buttons[row][initCol].setText("" + startGame.currentStateRabbits(initCol, row));
                    }
//                    if (startGame.rabbitCellScannedTwice(initCol, row)== TRUE){
//                        buttons[row][initCol].setText("" + startGame.currentStateRabbits(initCol, row));
//                    }
                }
                for (int initRow = 0; initRow < NUM_ROWS; initRow++) {
                    if ((startGame.checkIfAlreadyScanned(col, initRow) == TRUE && startGame.checkIfRabbit(col, initRow) == FALSE) ||
                            startGame.rabbitCellScannedTwice(col, initRow) == TRUE){
                        buttons[initRow][col].setText("" + startGame.currentStateRabbits(col, initRow));
                    }
//                    if (startGame.rabbitCellScannedTwice(col, initRow)== TRUE){
//                        buttons[initRow][col].setText("" + startGame.currentStateRabbits(col, initRow));
//                    }
                }
            }
        }
        else{
            numberScans ++;
            startGame.updateCellScanned(col, row);
            button.setText(""+ startGame.currentStateRabbits(col, row));
        }
        TextView txtNumberScans = findViewById(R.id.txtNumberScans);
        txtNumberScans.setText("# Scans Used: "+numberScans);
    }

    private void lockButtonSizes(){
        for ( int row=0; row < NUM_ROWS; row++){
            for (int col = 0; col < NUM_COLS; col++){
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


}
