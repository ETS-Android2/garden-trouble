package com.application.jasleen.gardentrouble;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        createSelectRabbitNumberRadioButtons();
        createSelectGridSizeRadioButtons();
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
                    Toast.makeText(OptionsActivity.this, "You clicked " + numRow + " rows by " + numCol + " columns", Toast.LENGTH_SHORT)
                            .show();
                }
            });

            //Add to grid size radio group:
            group.addView(gridButton);
        }
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
                    Toast.makeText(OptionsActivity.this, "You clicked " + numRabbit + " rabbits", Toast.LENGTH_SHORT)
                            .show();
                }
            });

            // Add to number of rabbits radio group:
            group.addView(button);
        }
    }
}
