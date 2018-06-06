package com.application.jasleen.gardentrouble.model;

import static java.lang.Boolean.FALSE;

/**
 * Manage the state of a cell
 * @author Jasleen Kaur
 */
public class Cell {

    private boolean hasRabbit;
    private boolean isScanned;
    private int rowColumnNumberRabbits;
    //Set data for cell for specific values
    public Cell(boolean hasRabbit, boolean isScanned, int rowColumnNumberRabbits) {
        this.hasRabbit = hasRabbit;
        this.isScanned = isScanned;
        this.rowColumnNumberRabbits = rowColumnNumberRabbits;
    }
    //default constructor
    public Cell() {
        this.hasRabbit = FALSE;
        this.isScanned = FALSE;
        this.rowColumnNumberRabbits = 0; // think it should be 0
    }

    // Return the if cell has rabbit or not
    public boolean getHasRabbit() {
        return hasRabbit;
    }

    // Set the cell to have no rabbit
    public void setHasRabbit(boolean hasRabbit) {
        this.hasRabbit = hasRabbit;
    }

    // Return if the cell has been scanned or not
    public boolean getIsScanned() {
        return isScanned;
    }

    public void setIsScanned(boolean isScanned){
        this.isScanned = isScanned;
    }

    // Get the total number of rabbits that appear in the row and column of the cell. Throws IllegalArgumentException if weight is less than 0.
    public int getRowColumnNumberRabbits(){
        return rowColumnNumberRabbits;
    }

    // Set the total number of rabbits that appear in the rows and columns of the cell.
    // Throws IllegalArgumentException if weight is less than 0.
    public void setRowColumnNumberRabbits(int rowColumnNumberRabbits){
        if (rowColumnNumberRabbits < 0){
            throw new IllegalArgumentException("Number of rabbits in column and row of cell combined must be >= 0");
        }
        this.rowColumnNumberRabbits = rowColumnNumberRabbits;
    }

}
