package com.application.jasleen.gardentrouble;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public static Intent makeOptionsIntent(Context context) {
        return new Intent(context, OptionsActivity.class);
    }
}
