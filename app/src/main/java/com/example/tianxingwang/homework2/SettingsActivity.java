package com.example.tianxingwang.homework2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private Button mSettingsButton;

    private static final String EXTRA_SETTINGS_RESET_CLICKED =
            "com.example.tianxingwang.homework2.settings_reset_clicked";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mSettingsButton = (Button) findViewById(R.id.buttonReset);

        //When clicked, create an Intent with the data to pass back
        // to the calling Activity (i.e., ContactsActivity)
        mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //We are not launching an Activity, so no arguments
                Intent i = new Intent();

                //Attach a Boolean extra to the Intent (true, the second argument)
                // indicating the button was clicked.  The extra is identified with
                // the key (first argument) -- this will let us look-up the extra,
                // which is especially important if we send > 1 extra back.
                i.putExtra(EXTRA_SETTINGS_RESET_CLICKED, true);

                //The result code will be RESULT_OK (everything is under control,
                // situation normal).
                setResult(RESULT_OK, i);

                //Let the user know we are resetting the data in ContactsActivity,
                // despite the fact that doesn't really happen until we back out of
                // this Activity.
                Toast.makeText(SettingsActivity.this, "Resetting all the things!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Used to ask SettingsActivity if the reset button was clicked
    // (since implementation details for the Intent are part of this class)
    public static boolean wasSettingsResetClicked(Intent result) {

        //Use the key (first argument) to lookup the Boolean stored in the Intent,
        // this Boolean is returned (default is false, second argument).
        return result.getBooleanExtra(EXTRA_SETTINGS_RESET_CLICKED, false);
    }


}
