package com.application.jasleen.gardentrouble;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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

                // Closes Game Activity and goes back to the Main Menu Activity
                getActivity().finish();
            }
        };
        TextView title = new TextView(getActivity());
// You Can Customise your Title here
        title.setText(R.string.congratulations_message_title);
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);

        //Build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setCustomTitle(title)
                .setView(view)
                .setPositiveButton(android.R.string.ok,listener)
                .create();

    }
}
