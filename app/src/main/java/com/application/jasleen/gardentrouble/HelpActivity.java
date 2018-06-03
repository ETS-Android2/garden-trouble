package com.application.jasleen.gardentrouble;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public static Intent makeHelpIntent(Context context) {
        return new Intent(context, HelpActivity.class);
    }
}
