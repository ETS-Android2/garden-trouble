package com.application.jasleen.gardentrouble;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.application.jasleen.gardentrouble.model.OptionsData;

/**
 * Options Activity supports the Options UI.
 * Displays radio buttons to choose the size of grid/board and how many rabbits
 * Supports erasing the total number of games played
 */
public class OptionsActivity extends AppCompatActivity {

    private OptionsData optionsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //Instantiate OptionsData
        optionsData = OptionsData.getInstance();

        createSelectRabbitNumberRadioButtons();
        createSelectGridSizeRadioButtons();
        setUpEraseGamesPlayedButton();
    }

    private void setUpEraseGamesPlayedButton() {
        Button btnEraseTimesPlayed = findViewById(R.id.btnEraseGamesPlayed);
        btnEraseTimesPlayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionsData.setNumberGamesPlayed(getApplicationContext(), 0);
            }
        });
    }

    //Options activity can make itself
    public static Intent makeOptionsIntent(Context context) {
        return new Intent(context, OptionsActivity.class);
    }

    private void createSelectGridSizeRadioButtons() {
        RadioGroup group = findViewById(R.id.radio_group_grid_size);

        // Gives access to the integer array or rows and cols
        int[] numRows = getResources().getIntArray(R.array.num_grid_size_row);
        int[] numCols = getResources().getIntArray(R.array.num_grid_size_col);

        //Create the buttons:
        for(int i = 0; i < numRows.length; i++){
            final int numRow = numRows[i];
            final int numCol = numCols[i];

            RadioButton gridButton = new RadioButton(this);
            gridButton.setTextColor(Color.RED);
            gridButton.setTextSize(18);
            Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
            gridButton.setTypeface(boldTypeface);
            gridButton.setText(getString(R.string.grid_size, numRow, numCol));

            //Set on click callbacks for grid size
            gridButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    optionsData.setRows(getApplicationContext(),numRow);
                    optionsData.setCols(getApplicationContext(),numCol);
                    Toast.makeText(OptionsActivity.this, "You clicked " + optionsData.getRows(getApplicationContext()) +
                            " rows by " + optionsData.getCols(getApplicationContext()) + " columns", Toast.LENGTH_SHORT)
                            .show();

                }
            });
            //Add to grid size radio group:
            group.addView(gridButton);
            // Select default button:
            if(numCol == optionsData.getCols(this) && numRow == optionsData.getRows(this)){
                gridButton.setChecked(true);
            }
        }
    }

    private void createSelectRabbitNumberRadioButtons() {
        RadioGroup group =  findViewById(R.id.radio_group_number_rabbits);

        //This will give access to the integer array
        int[] numRabbits = getResources().getIntArray(R.array.num_rabbits);

        //Create the buttons:
        for (int i=0; i < numRabbits.length; i++){
            final int numRabbit = numRabbits[i];

            RadioButton rabbitNumberButton = new RadioButton(this);
            rabbitNumberButton.setTextColor(Color.RED);
            rabbitNumberButton.setTextSize(18);
            Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
            rabbitNumberButton.setTypeface(boldTypeface);
            rabbitNumberButton.setText(getString(R.string.rabbit_number, numRabbit));

            // Set on click callbacks for number of rabbits
            rabbitNumberButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    optionsData.setNumberRabbits(getApplicationContext(),numRabbit);
                    Toast.makeText(OptionsActivity.this, "You clicked " + optionsData.getNumberRabbits(getApplicationContext()) + " rabbits", Toast.LENGTH_SHORT)
                            .show();
                }
            });

            // Add to number of rabbits radio group:
            group.addView(rabbitNumberButton);
            // Select default button:
            if(numRabbit == optionsData.getNumberRabbits(this)){
                rabbitNumberButton.setChecked(true);
            }
        }
    }
}
