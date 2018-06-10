package com.application.jasleen.gardentrouble;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        setUpHelpText();
    }

    public static Intent makeHelpIntent(Context context) {
        return new Intent(context, HelpActivity.class);
    }

    private void setUpHelpText() {

        TextView t2 = findViewById(R.id.txtAbout);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
