package com.application.jasleen.gardentrouble.model;

/*
Singleton class for storing options data
Saves and retrieves values
 */
import android.content.Context;
import android.content.SharedPreferences;

import com.application.jasleen.gardentrouble.R;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Boolean.FALSE;

public class OptionsData {
    private static final String NUM_RABBITS_PREF_NAME = "Num Rabbits Selected";
    private static final String COL_SIZE_PREF_NAME = "Col Size Selected";
    private static final String ROW_SIZE_PREF_NAME = "Row Size Selected";
    private static final String NUM_GAMES_PREF_NAME = "Number of Games Played";

    private static final String FOUR_SIX_PREF_NAME = "4 rows by 6 cols with 6 rabbits";
    private static final String FOUR_TEN_PREF_NAME = "4 rows by 6 cols with 10 rabbits";
    private static final String FOUR_FIFTEEN_PREF_NAME = "4 rows by 6 cols with 15 rabbits";
    private static final String FOUR_TWENTY_PREF_NAME = "4 rows by 6 cols with 20 rabbits";

    private static final String FIVE_SIX_PREF_NAME = "5 rows by 10 cols with 6 rabbits";
    private static final String FIVE_TEN_PREF_NAME = "5 rows by 10 cols with 10 rabbits";
    private static final String FIVE_FIFTEEN_PREF_NAME = "5 rows by 10 cols with 15 rabbits";
    private static final String FIVE_TWENTY_PREF_NAME = "5 rows by 10 cols with 20 rabbits";

    private static final String SIX_SIX_PREF_NAME = "6 rows by 15 cols with 6 rabbits";
    private static final String SIX_TEN_PREF_NAME = "6 rows by 15 cols with 10 rabbits";
    private static final String SIX_FIFTEEN_PREF_NAME = "6 rows by 15 cols with 15 rabbits";
    private static final String SIX_TWENTY_PREF_NAME = "6 rows by 15 cols with 20 rabbits";


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
