package com.application.jasleen.gardentrouble.model;

public class OptionsData {
    //make these actual values
    private int numRows = 4;
    private int numCols = 4;
    private int numberRabbits = 5;

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
    public int getRows(){
        return numRows;
    }

    public void setRows(int numRows){
        this.numRows = numRows;
    }

    public int getCols(){
        return numCols;
    }

    public void setCols(int numCols){
        this.numCols = numCols;
    }

    public int getNumberRabbits(){
        return numberRabbits;
    }

    public void setNumberRabbits(int numberRabbits){
        this.numberRabbits = numberRabbits;
    }
}
