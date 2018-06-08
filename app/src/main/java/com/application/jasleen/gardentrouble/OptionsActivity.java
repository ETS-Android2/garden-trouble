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
    }

    //Options activity can make itself
    public static Intent makeOptionsIntent(Context context) {
        return new Intent(context, OptionsActivity.class);
    }

    private void createSelectRabbitNumberRadioButtons() {
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_group_number_rabbits);

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

            // Add to radio group:
            group.addView(button);
        }
    }
}
