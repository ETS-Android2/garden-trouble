package com.application.jasleen.gardentrouble.model;

import static java.lang.Boolean.FALSE;

/**
 * Manage the state of a cell
 * @author Jasleen Kaur
 */
public class Cell {

    private boolean hasRabbit;
    private boolean isScanned;
    private boolean isScannedTwice;
    private int rowColumnNumberRabbits;

    //Set data for cell for specific values
    public Cell(boolean hasRabbit, boolean isScanned, boolean isScannedTwice, int rowColumnNumberRabbits) {
        this.hasRabbit = hasRabbit;
        this.isScanned = isScanned;
        this.isScannedTwice = isScannedTwice;
        this.rowColumnNumberRabbits = rowColumnNumberRabbits;
    }
    //default constructor
    public Cell() {
        this.hasRabbit = FALSE;
        this.isScanned = FALSE;
        this.isScannedTwice = FALSE;
        this.rowColumnNumberRabbits = 0;
    }

    // Return true or false if cell has rabbit or not
    public boolean getHasRabbit() {
        return hasRabbit;
    }

    // Set the cell to have rabbit or not
    public void setHasRabbit(boolean hasRabbit) {
        this.hasRabbit = hasRabbit;
    }

    // Return if the cell has been scanned or not
    public boolean getIsScanned() {
        return isScanned;
    }

    //Set if the cell has been scanned
    public void setIsScanned(boolean isScanned){
        this.isScanned = isScanned;
    }

    // Return if the cell has been scanned twice, this is for cells with rabbits especially
    public boolean getIsScannedTwice() {
        return isScannedTwice;
    }

    //Set the cell to have been scanned twice
    public void setIsScannedTwice(boolean isScannedTwice){
        this.isScannedTwice = isScannedTwice;
    }

    // Return the total number of rabbits that appear in the row and column of the cell.
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
