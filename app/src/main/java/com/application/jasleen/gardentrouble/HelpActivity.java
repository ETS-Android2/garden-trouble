package com.application.jasleen.gardentrouble;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 * Help Activity supports the Help UI.
 * Displays About Author, How to Play the Game and resources used
 */
public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        setUpHelpText();
    }

    // Help Activity can make itself
    public static Intent makeHelpIntent(Context context) {
        return new Intent(context, HelpActivity.class);
    }

    // Sets up help text with hyperlinks
    private void setUpHelpText() {
        TextView t2 = findViewById(R.id.txtAbout);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
