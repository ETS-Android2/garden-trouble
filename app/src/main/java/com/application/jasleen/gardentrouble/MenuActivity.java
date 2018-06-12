package com.application.jasleen.gardentrouble;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setUpPlayGameButton();
        setUpHelpButton();
        setUpOptionsButton();
    }

    private void setUpOptionsButton() {
        Button btnOptions = findViewById(R.id.btnOptions);
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MenuActivity.this, "Clicked 'OPTIONS'", Toast.LENGTH_SHORT)
                        .show();
                Intent intent = OptionsActivity.makeOptionsIntent(MenuActivity.this);
                startActivity(intent);

            }
        });
    }

    private void setUpPlayGameButton() {
        Button btnPlayGame = findViewById(R.id.btnPlayGame);
        btnPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Game Activity", "Opened Game Activity");
                Toast.makeText(MenuActivity.this, "Clicked 'PLAY GAME'", Toast.LENGTH_SHORT)
                        .show();
                Intent intent = GameActivity.makeGameIntent(MenuActivity.this);
                startActivity(intent);

            }
        });

    }


    private void setUpHelpButton() {
        Button btnHelp = findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(MenuActivity.this, "Clicked 'HELP'", Toast.LENGTH_SHORT)
                        .show();
                Intent intent = HelpActivity.makeHelpIntent(MenuActivity.this);
                startActivity(intent);
            }
        });
    }

    //Menu Activity creates itself
    public static Intent makeIntent(Context context) {
        return new Intent(context, MenuActivity.class);
    }


}
