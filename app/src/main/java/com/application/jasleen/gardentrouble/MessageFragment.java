package com.application.jasleen.gardentrouble;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class MessageFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Create the view to show
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.congratulations_layout,null);


        //Create a button listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("MessageFragment", "You have won the game" );

                Intent intent = MenuActivity.makeMenuIntent(getActivity());
                startActivity(intent);
            }
        };

        //Build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Congratulations! You Won!")
                .setView(view)
                .setPositiveButton(android.R.string.ok,listener)
                .create();

    }
}
