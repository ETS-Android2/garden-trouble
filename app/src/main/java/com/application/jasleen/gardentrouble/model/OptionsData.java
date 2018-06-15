package com.application.jasleen.gardentrouble.model;

import android.content.Context;
import android.content.SharedPreferences;
import com.application.jasleen.gardentrouble.R;
import static android.content.Context.MODE_PRIVATE;

/**
 *  Singleton class for storing the options data
 *  Saves and loads values for each data
 */

public class OptionsData {
    private static final String NUM_RABBITS_PREF_NAME = "Num Rabbits Selected";
    private static final String COL_SIZE_PREF_NAME = "Col Size Selected";
    private static final String ROW_SIZE_PREF_NAME = "Row Size Selected";
    private static final String NUM_GAMES_PREF_NAME = "Number of Games Played";

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

    private void saveValue(Context context, String key, int value){
        SharedPreferences prefs = context.getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private int loadValue(Context context, String key, int id){
        SharedPreferences prefs = context.getSharedPreferences(key, MODE_PRIVATE);
        int defaultVal = context.getResources().getInteger(id);
        return (prefs.getInt(key, defaultVal));
    }

    public int getRows(Context context){
        return loadValue(context, ROW_SIZE_PREF_NAME, R.integer.default_row_size );
    }

    public void setRows( Context context, int numRows){
         saveValue(context, ROW_SIZE_PREF_NAME, numRows);
    }

    public int getCols(Context context){
        return loadValue(context, COL_SIZE_PREF_NAME, R.integer.default_col_size);
    }

    public void setCols(Context context, int numCols){
        saveValue(context, COL_SIZE_PREF_NAME, numCols);
    }

    public int getNumberRabbits(Context context){
        return loadValue(context, NUM_RABBITS_PREF_NAME, R.integer.default_num_rabbits);
    }

    public void setNumberRabbits(Context context, int numberRabbits){
        saveValue(context, NUM_RABBITS_PREF_NAME, numberRabbits);
    }

    public int getNumberGamesPlayed(Context context){
        return loadValue(context, NUM_GAMES_PREF_NAME, R.integer.default_num_games_played);
    }

    public void setNumberGamesPlayed(Context context, int numGamesPlayed){
        saveValue(context, NUM_GAMES_PREF_NAME, numGamesPlayed);
    }
}
