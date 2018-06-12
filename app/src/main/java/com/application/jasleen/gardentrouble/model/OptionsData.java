package com.application.jasleen.gardentrouble.model;

/*
Singleton class for storing options data
Saves and retrieves values
 */
import android.content.Context;
import android.content.SharedPreferences;

import com.application.jasleen.gardentrouble.OptionsActivity;
import com.application.jasleen.gardentrouble.R;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Boolean.FALSE;

public class OptionsData {
    private static final String NUM_RABBITS_PREF_NAME = "Num Rabbits";
    private static final String PREFS_NAME = "AppRabbitPref";
    private static final String COL_SIZE_PREF_NAME = "Col Size";
    private static final String COL_PREFS_NAME = "AppColSizePref";
    private static final String ROW_SIZE_PREF_NAME = "Row Size";
    private static final String ROW_PREFS_NAME = "AppRowSizePref";
    //make these actual values
    //Context context;
    private int numRows;
    private int numCols;
    private int numberRabbits;
    private boolean eraseGamesPlayed = FALSE;

    /*
    Singleton support
     */
    private static OptionsData instance;

    private OptionsData(){
        //private to prevent anyone else from instantiating
    }
    //OptionsData hand back a reference to an object
    public static OptionsData getInstance(){
        if (instance == null){
            instance = new OptionsData();
        }
        return instance;
    }

    /*
     Normal object code

     */
    public int getRows(Context context){
        SharedPreferences prefs = context.getSharedPreferences(ROW_PREFS_NAME, MODE_PRIVATE);
        int defaultRowSize = context.getResources().getInteger(R.integer.default_row_size);
        return prefs.getInt(ROW_SIZE_PREF_NAME , defaultRowSize);
        //return numRows;
    }

    public void setRows( Context context, int numRows){
        SharedPreferences prefs = context.getSharedPreferences(ROW_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(ROW_SIZE_PREF_NAME, numRows);//Act as key value
        editor.apply();
        //this.numRows = numRows;
    }

    public int getCols(Context context){
        SharedPreferences prefs = context.getSharedPreferences(COL_PREFS_NAME, MODE_PRIVATE);
        int defaultColSize = context.getResources().getInteger(R.integer.default_col_size);
        return prefs.getInt(COL_SIZE_PREF_NAME, defaultColSize);
        //return numCols;
    }

    public void setCols(Context context, int numCols){
        SharedPreferences prefs = context.getSharedPreferences(COL_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(COL_SIZE_PREF_NAME, numCols);//Act as key value
        editor.apply();
        //this.numCols = numCols;
    }

    public int getNumberRabbits(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int defaultRabbitNumber = context.getResources().getInteger(R.integer.default_num_rabbits);
        return prefs.getInt(NUM_RABBITS_PREF_NAME , defaultRabbitNumber);
        //return numberRabbits;
    }

    public void setNumberRabbits(Context context, int numberRabbits){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NUM_RABBITS_PREF_NAME, numberRabbits); //Act as key value
        editor.apply();
        //this.numberRabbits = numberRabbits;
    }

    public boolean getEraseGamesPlayed(){
        return eraseGamesPlayed;
    }

    public void setEraseGamesPlayed(boolean eraseGamesPlayed){
        this.eraseGamesPlayed = eraseGamesPlayed;
    }
}
