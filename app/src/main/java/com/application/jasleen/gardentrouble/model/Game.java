package com.application.jasleen.gardentrouble.model;

import com.application.jasleen.gardentrouble.GameActivity;

import java.util.Random;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Game {
        private static final int NUM_ROWS= GameActivity.NUM_ROWS;
        private static final int NUM_COLS= GameActivity.NUM_COLS;
        private static int NUM_RABBITS = GameActivity.NUM_RABBITS;

        private static Cell[][] cellCollection = new Cell[NUM_ROWS][NUM_COLS];

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
            return ( cellCollection[row][col].getHasRabbit() == TRUE);
        }

        //check if cell has been clicked before
        public boolean checkIfAlreadyScanned(int col, int row){
            return (cellCollection[row][col].getIsScanned() == TRUE);
        }

        //Setting cells with rabbits scanned twice when clicked twice
        public void setCellScannedTwice(int col, int row){
                cellCollection[row][col].setIsScannedTwice(TRUE);
        }

        //Return true when cell with rabbit is scanned twice
        public boolean rabbitCellScannedTwice(int col, int row){
            return (cellCollection[row][col].getIsScannedTwice() == TRUE);
        }

        //Returns the total current number of rabbits in the cell's row and column
        public int currentStateRabbits(int col, int row){
            return cellCollection[row][col].getRowColumnNumberRabbits();
        }

        public void updateCellScanned(int col, int row) {

            //Make scanned item true
            if (cellCollection[row][col].getIsScanned() != TRUE) {
                cellCollection[row][col].setIsScanned(TRUE);

                if (cellCollection[row][col].getHasRabbit() == TRUE) {
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
            }
        }

        // Setting the scanning values for initial grid
        private static void initialScanGrid(int col, int row){
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


