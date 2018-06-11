package com.application.jasleen.gardentrouble;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.application.jasleen.gardentrouble.model.OptionsData;

public class OptionsActivity extends AppCompatActivity {
    private static final String NUM_RABBITS_PREF_NAME = "Num Rabbits Selected";
    private static final String PREFS_NAME = "AppRabbitPrefs";
    private static final String COL_SIZE_PREF_NAME = "Col Size Selected";
    private static final String COL_PREFS_NAME = "AppColSizePrefs";
    private static final String ROW_SIZE_PREF_NAME = "Row Size Selected";
    private static final String ROW_PREFS_NAME = "AppRowSizePrefs";


    private OptionsData optionsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //Instantiate OptionsData
        optionsData = OptionsData.getInstance();

        optionsData.getCols();
        optionsData.getRows();
        optionsData.getNumberRabbits();

        createSelectRabbitNumberRadioButtons();
        createSelectGridSizeRadioButtons();

//        int savedNumberRabbit = getNumRabbitsSelected(this);
//        Toast.makeText(this, "Saved Number of Rabbits: "+ savedNumberRabbit, Toast.LENGTH_SHORT)
//                .show();
    }

    //Options activity can make itself
    public static Intent makeOptionsIntent(Context context) {
        return new Intent(context, OptionsActivity.class);
    }

    private void createSelectGridSizeRadioButtons() {
        RadioGroup group = findViewById(R.id.radio_group_grid_size);

        //This will give access to the integer array or rows and cols
        int[] numRows = getResources().getIntArray(R.array.num_grid_size_row);
        int[] numCols = getResources().getIntArray(R.array.num_grid_size_col);

        //Create the buttons:
        for(int i = 0; i < numRows.length; i++){
            final int numRow = numRows[i];
            final int numCol = numCols[i];

            RadioButton gridButton = new RadioButton(this);
            gridButton.setText(numRow + " rows by " + numCol + " columns");

            //Set on click callbacks for grid size
            gridButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    optionsData.setRows(numRow);
                    optionsData.setCols(numCol);
                    Toast.makeText(OptionsActivity.this, "You clicked " + optionsData.getRows() +
                            " rows by " + optionsData.getCols() + " columns", Toast.LENGTH_SHORT)
                            .show();
                    saveColSelected(optionsData.getCols());
                    saveRowSelected(optionsData.getRows());
                }
            });

            //Add to grid size radio group:
            group.addView(gridButton);

            // Select default button:
            if(numCol == getColSizeSelected(this) && numRow == getRowSizeSelected(this)){
                gridButton.setChecked(true);
            }
        }
    }

    private void saveRowSelected(int row) {
        SharedPreferences prefs = this.getSharedPreferences(ROW_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(ROW_SIZE_PREF_NAME, row);//Act as key value
        editor.apply();
    }

    private void saveColSelected(int col) {
        SharedPreferences prefs = this.getSharedPreferences(COL_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(COL_SIZE_PREF_NAME, col);//Act as key value
        editor.apply();
    }
    static public int getColSizeSelected(Context context){
        SharedPreferences prefs = context.getSharedPreferences(COL_PREFS_NAME, MODE_PRIVATE);
        //TODO: Change default value
        int defaultColSize = context.getResources().getInteger(R.integer.default_col_size);
        return prefs.getInt(COL_SIZE_PREF_NAME, defaultColSize);

    }
    static public int getRowSizeSelected(Context context){
        SharedPreferences prefs = context.getSharedPreferences(ROW_PREFS_NAME, MODE_PRIVATE);
        //TODO: Change default value
        int defaultRowSize = context.getResources().getInteger(R.integer.default_row_size);
        return prefs.getInt(ROW_SIZE_PREF_NAME , defaultRowSize);

    }

    private void createSelectRabbitNumberRadioButtons() {
        RadioGroup group =  findViewById(R.id.radio_group_number_rabbits);

        //This will give access to the integer array
        int[] numRabbits = getResources().getIntArray(R.array.num_rabbits);

        //Create the buttons:
        for (int i=0; i < numRabbits.length; i++){
            final int numRabbit = numRabbits[i];

            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.rabbit_number, numRabbit));

            // Set on click callbacks for number of rabbits
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    optionsData.setNumberRabbits(numRabbit);
                    Toast.makeText(OptionsActivity.this, "You clicked " + optionsData.getNumberRabbits() + " rabbits", Toast.LENGTH_SHORT)
                            .show();
                    saveNumRabbitsSelected(optionsData.getNumberRabbits());
                    
                }
            });

            // Add to number of rabbits radio group:
            group.addView(button);

            // Select default button:
            if(numRabbit == getNumRabbitsSelected(this)){
                button.setChecked(true);
            }

        }
    }

    private void saveNumRabbitsSelected(int numRabbit) {
        SharedPreferences prefs = this.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NUM_RABBITS_PREF_NAME, numRabbit); //Act as key value
        editor.apply();
    }
    //use static to not create an instance
    static public int getNumRabbitsSelected(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        //TODO: Change default value
        int defaultRabbitNumber = context.getResources().getInteger(R.integer.default_num_rabbits);
        return prefs.getInt(NUM_RABBITS_PREF_NAME , defaultRabbitNumber);

    }

}
