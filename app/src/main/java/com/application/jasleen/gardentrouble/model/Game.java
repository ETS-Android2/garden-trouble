package com.application.jasleen.gardentrouble.model;

import com.application.jasleen.gardentrouble.GameActivity;

import java.util.Random;

import static java.lang.Boolean.TRUE;

public class Game {
        private static final int NUM_ROWS= GameActivity.NUM_ROWS;
        private static final int NUM_COLS= GameActivity.NUM_COLS;
        private static int NUM_RABBITS = GameActivity.NUM_RABBITS;

        private static Cell[][] cellCollection = new Cell[NUM_ROWS][NUM_COLS];

        //generate grid
        public void generateGrid(){
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

                }
                initialScanGrid(row, col);
            }

        }

        public boolean checkIfRabbit(int col, int row){
            if ( cellCollection[row][col].getHasRabbit() == TRUE){
                updateCellScanned(col, row);
                return true;
            }
            else{
                return false;
            }
        }

        public int currentStateRabbits(int col, int row){
            return cellCollection[row][col].getRowColumnNumberRabbits();
        }

        public void updateCellScanned(int col, int row){

            //Make scanned item true
            if (cellCollection[row][col].getIsScanned() != TRUE){
                cellCollection[row][col].setIsScanned(TRUE);
            }

            if ( cellCollection[row][col].getHasRabbit() == TRUE){
                for(int initialCol=0; initialCol < NUM_COLS; initialCol++){
                    int initialColScannedRabbits = cellCollection[row][initialCol].getRowColumnNumberRabbits();
                    initialColScannedRabbits--;
                    cellCollection[row][initialCol].setRowColumnNumberRabbits(initialColScannedRabbits);
                }

                for(int initialRow = 0; initialRow < NUM_ROWS; initialRow++){
                    int initialRowScannedRabbits = cellCollection[initialRow][col].getRowColumnNumberRabbits();
                    initialRowScannedRabbits--;
                    cellCollection[initialRow][col].setRowColumnNumberRabbits(initialRowScannedRabbits);
                }
            }
        }

        // Setting the scanning values for initial grid
        public static void initialScanGrid(int row, int col){
            for(int initialCol=0; initialCol < NUM_COLS; initialCol++){
                int initialColScannedRabbits = cellCollection[row][initialCol].getRowColumnNumberRabbits();
                initialColScannedRabbits++;
                cellCollection[row][initialCol].setRowColumnNumberRabbits(initialColScannedRabbits);
            }

            for(int initialRow = 0; initialRow < NUM_ROWS; initialRow++){
                int initialRowScannedRabbits = cellCollection[initialRow][col].getRowColumnNumberRabbits();
                initialRowScannedRabbits++;
                cellCollection[initialRow][col].setRowColumnNumberRabbits(initialRowScannedRabbits);
            }

            }
    }


