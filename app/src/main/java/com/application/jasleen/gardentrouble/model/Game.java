package com.application.jasleen.gardentrouble.model;

import java.util.Random;
import static java.lang.Boolean.TRUE;

/**
 * Functions which implement the game logic
 * @author Jasleen Kaur
 */

public class Game {
        private int NUM_ROWS = OptionsData.getInstance().getRows();
        private int NUM_COLS = OptionsData.getInstance().getCols();
        private int NUM_RABBITS = OptionsData.getInstance().getNumberRabbits();
        private int numberScans;
        private int numberRabbitsFound;

        private Cell[][] cellCollection = new Cell[NUM_ROWS][NUM_COLS];

        //generate grid
        public void generateGrid(){

            //To generate random places for rabbits in the grid
            Random random = new Random();

            //make grid of initial cells with default values
            for(int row=0; row < NUM_ROWS; row++) {
                for (int col = 0; col < NUM_COLS; col++) {
                    cellCollection[row][col] = new Cell();
                }
            }

            //placing random rabbits in their cells
            while (NUM_RABBITS > 0 ){
                int row = random.nextInt(NUM_ROWS);
                int col = random.nextInt(NUM_COLS);

                if (cellCollection[row][col].getHasRabbit() != TRUE ){
                    cellCollection[row][col].setHasRabbit(TRUE);
                    NUM_RABBITS --;
                    initialScanGrid(col, row);

                }
                //initialScanGrid(col, row);
            }
        }

        //check if rabbit is present
        public boolean checkIfRabbit(int col, int row){
            //cell has been scanned before and has a rabbit
            return ( cellCollection[row][col].getHasRabbit());
        }

        //check if cell has been clicked before
        public boolean checkIfScanned(int col, int row){
            return (cellCollection[row][col].getIsScanned());
        }

        //Return true when cell with rabbit is scanned twice
        public boolean rabbitCellClickedAgain(int col, int row){
            return (cellCollection[row][col].getRabbitCheckedOnce());
        }

        //Returns the total current number of rabbits in the cell's row and column
        public int currentStateRabbits(int col, int row){
            return cellCollection[row][col].getRowColumnNumberRabbits();
        }

        public void scanCell(int col, int row){
            if (cellCollection[row][col].getIsScanned() != TRUE) {
                cellCollection[row][col].setIsScanned(TRUE);
            }
        }

        // updates the number of scans
        public int updateScan(int col, int row){
            if (!checkIfScanned(col, row)) {
                numberScans++;
                scanCell(col, row);
            }
            return numberScans;
        }

        // Update number of rabbits found
        public int updateRabbitsFound(){
            return numberRabbitsFound;
        }

        public void updateCells(int col, int row) {

            cellCollection[row][col].setRabbitCheckedOnce(TRUE);
            numberRabbitsFound++;
            for (int initialCol = 0; initialCol < NUM_COLS; initialCol++) {
                    if (cellCollection[row][initialCol] != cellCollection[row][col]) {
                        int initialColScannedRabbits = cellCollection[row][initialCol].getRowColumnNumberRabbits();
                        initialColScannedRabbits--;
                        cellCollection[row][initialCol].setRowColumnNumberRabbits(initialColScannedRabbits);
                    }
                }
            for (int initialRow = 0; initialRow < NUM_ROWS; initialRow++) {
                if (cellCollection[initialRow][col] != cellCollection[row][col]) {
                    int initialRowScannedRabbits = cellCollection[initialRow][col].getRowColumnNumberRabbits();
                    initialRowScannedRabbits--;
                    cellCollection[initialRow][col].setRowColumnNumberRabbits(initialRowScannedRabbits);

                }
            }

        }

        // Setting the scanning values for initial grid
        private void initialScanGrid(int col, int row){
            for(int initialCol=0; initialCol < NUM_COLS; initialCol++){
                if( cellCollection[row][initialCol]!= cellCollection[row][col]) {
                    int initialColScannedRabbits = cellCollection[row][initialCol].getRowColumnNumberRabbits();
                    initialColScannedRabbits++;
                    cellCollection[row][initialCol].setRowColumnNumberRabbits(initialColScannedRabbits);
                }
            }

            for(int initialRow = 0; initialRow < NUM_ROWS; initialRow++){
                if( cellCollection[initialRow][col]!= cellCollection[row][col]) {
                    int initialRowScannedRabbits = cellCollection[initialRow][col].getRowColumnNumberRabbits();
                    initialRowScannedRabbits++;
                    cellCollection[initialRow][col].setRowColumnNumberRabbits(initialRowScannedRabbits);
                }
            }
        }
    }


