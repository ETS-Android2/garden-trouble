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
import android.widget.Toast;

import com.application.jasleen.gardentrouble.model.Game;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class GameActivity extends AppCompatActivity {
    //How many mines there should be come from options
    //MAKE THESE COME FROM OPTIONS CLASS/ ACTIVTIY
    public static final int NUM_ROWS = 4 ; //ACCESSSIBLE BY OTHER CLASSES NOW
    public static final int NUM_COLS = 4;
    public static final int NUM_RABBITS = 3;

    private Game cellChosen;

    Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        cellChosen = new Game();
        cellChosen.generateGrid(); //calling generate grid here so to create it before anything else
        populateButtons();
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

                //Make text not cut off on small buttons (FOR NOW)
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
        if (cellChosen.checkIfRabbit(col, row) == TRUE) {
            //This does not scale image in button
            //button.setBackgroundResource(R.drawable.gopher_welcome);
            if (cellChosen.checkIfAlreadyScanned(col, row) == TRUE){
                cellChosen.setCellScannedTwice(col, row);
                button.setText(""+ cellChosen.currentStateRabbits(col, row));
            }
            else {

                cellChosen.updateCellScanned(col, row);
                //Scale image to button
                int newWidth = button.getWidth();
                int newHeight = button.getHeight();
                Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gopher_welcome);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
                Resources resource = getResources();
                button.setBackground(new BitmapDrawable(resource, scaledBitmap));

                for (int initCol = 0; initCol < NUM_COLS; initCol++) {
                    if (cellChosen.checkIfAlreadyScanned(initCol, row) == TRUE && cellChosen.checkIfRabbit(initCol, row) == FALSE) {
                        buttons[row][initCol].setText("" + cellChosen.currentStateRabbits(initCol, row));
                    }
                    if (cellChosen.rabbitCellScannedTwice(initCol, row)== TRUE){
                        buttons[row][initCol].setText("" + cellChosen.currentStateRabbits(initCol, row));
                    }
                }
                for (int initRow = 0; initRow < NUM_ROWS; initRow++) {
                    if (cellChosen.checkIfAlreadyScanned(col, initRow) == TRUE && cellChosen.checkIfRabbit(col, initRow) == FALSE){
                        buttons[initRow][col].setText("" + cellChosen.currentStateRabbits(col, initRow));
                    }
                    if (cellChosen.rabbitCellScannedTwice(col, initRow)== TRUE){
                        buttons[initRow][col].setText("" + cellChosen.currentStateRabbits(col, initRow));
                    }
                }
            }
        }
        else{
            cellChosen.updateCellScanned(col, row);
            button.setText(""+cellChosen.currentStateRabbits(col, row));
        }
/*
        if(cellChosen.checkIfAlreadyScanned(col, row)== TRUE){
            for(int initCol=0; initCol < NUM_COLS; initCol++) {
                buttons[row][initCol].setText("" + cellChosen.currentStateRabbits(col, row));
            }
            for(int initRow = 0; initRow < NUM_ROWS; initRow++){
                buttons[initRow][col].setText(""+cellChosen.currentStateRabbits(col, initRow));
            }
        }

        else if ( cellChosen.checkIfAlreadyScanned(col, row) == FALSE){
            for(int initCol=0; initCol < NUM_COLS; initCol++){
                buttons[row][initCol].setText(""+cellChosen.currentStateRabbits(initCol, row));
            }

            for(int initRow = 0; initRow < NUM_ROWS; initRow++){
                buttons[initRow][col].setText(""+cellChosen.currentStateRabbits(col, initRow));
            }

        }
        */
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
