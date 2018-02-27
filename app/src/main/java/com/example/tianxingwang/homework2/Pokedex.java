package com.example.tianxingwang.homework2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import static android.view.View.*;

public class Pokedex extends AppCompatActivity {



    private final String TAG = "Pokedex";

    private final int NUM_POKEMONS = 4;

    private final int REQUEST_CODE_SETTINGS = 0;


    private CheckBox mPCheckBox;
    private ImageButton mPSettingsButton;
    private ImageView mPImage;
    private TextView mPName;
    private Button mPNextButton;
    private Button mPPreviousButton;

    private EditText mPCustomName; //editable field to set contact's location
    private TextView mPCategory; //to display contact's current location
    private TextView mPType; //to display contact's current location

    private MediaPlayer mSound;




    private final mPokedex[] PokedexClass = new mPokedex[]{
            new mPokedex(R.string.namePikachu, R.drawable.pikachu, R.string.typeElectric,R.string.categoryMouse,R.color.yellow),
            new mPokedex(R.string.nameBulbasaur, R.drawable.bulbasaur,R.string.typeGrass,R.string.categorySeed,R.color.green),
            new mPokedex(R.string.nameCharmander, R.drawable.charmander,R.string.typeFire,R.string.categoryLizard,R.color.orange),
            new mPokedex(R.string.nameSquirtle, R.drawable.squirtle,R.string.typeWater,R.string.categoryTurtle,R.color.blue)
    };

    private int mCurrentIndex = 0;

    private static final String KEY_POKEMON_INDEX = "pokemon_index";


 /*   public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "onConfigurationChanged called.");
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                setContentView(R.layout.layout_pokedex);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                setContentView(R.layout.layout_pokedex);
                break;
        }
    }

*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pokedex);

/*        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.layout_pokedex);
            Log.i("info", "landscape");
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.layout_pokedex);
            Log.i("info", "portrait");
        }
*/
        mPImage = (ImageView) findViewById(R.id.imageProfile);
        mPName = (TextView) findViewById(R.id.name);
        mPNextButton = (Button) findViewById(R.id.next);
        mPPreviousButton = (Button) findViewById(R.id.previous);
        mPCustomName = (EditText) findViewById(R.id.customName);
        mPCategory = (TextView) findViewById(R.id.category);
        mPType = (TextView) findViewById(R.id.type);
        mPCheckBox = (CheckBox) findViewById(R.id.collect);
        mPSettingsButton = (ImageButton) findViewById(R.id.setting);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        for(int i = 0; i < NUM_POKEMONS; i++) {

            //Restore the current contact's location
            //key: getString will obtain actual string the resource points ID to, append "_location"
            //value: the contact's current location (string)
            PokedexClass[i].setPokemonCustomName(sharedPref.getString(getString(PokedexClass[i].getmNameResId())+"Custom Name", "Give It A Name!"));

            //Save the bool indicating whether we have found the contact (in the game)
            //key: getString will obtain actual string the resource ID points to, append "_found"
            //value: whether contact has been found (boolean)
            PokedexClass[i].setmCollect(sharedPref.getBoolean(getString(PokedexClass[i].getmNameResId())+"Collect", false));

        }

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_POKEMON_INDEX, 0);

        }



        mPCustomName.addTextChangedListener(nameEditTextWatcher);


        mPCustomName.setText(PokedexClass[mCurrentIndex].getPokemonCustomName());

        update();





        mPNextButton.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {

                    mCurrentIndex = (mCurrentIndex + 1) % NUM_POKEMONS;
                    update();
                    mPCustomName.setText(PokedexClass[mCurrentIndex].getPokemonCustomName());

            }

        });





        mSound = MediaPlayer.create(this, R.raw.music);

        mSound.start();
        mSound.setLooping(true);


        mPPreviousButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            mCurrentIndex = (mCurrentIndex - 1);

            //ensures we wrap back to index 3 (instead of -1)
            if(mCurrentIndex == -1)
                mCurrentIndex = NUM_POKEMONS-1;

            mPCustomName.setText(PokedexClass[mCurrentIndex].getPokemonCustomName());
            update();


        }
    });

        mPCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PokedexClass[mCurrentIndex].setmCollect(mPCheckBox.isChecked());
            }
        });

        //Define the behavior of the Settings Button (launches SettingsActivity)
        mPSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //First argument here is the "calling" (current) Activity,
                // second is the Activity to launch.
                Intent i = new Intent(Pokedex.this, SettingsActivity.class);

                //Launch the activity defined in the Intent, indicate we are requesting data
                // back which will be identified by the REQUEST_CODE_SETTINGS key
                startActivityForResult(i, REQUEST_CODE_SETTINGS);
            }
        });





}//end of OnCreate()



    public void onResume(){
        super.onResume();
        mPCustomName.setText(PokedexClass[mCurrentIndex].getPokemonCustomName());
        update();

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent recvdData) {


        if (resultCode != Activity.RESULT_OK) {
            return; //something bad happened!
        }


        if(requestCode == REQUEST_CODE_SETTINGS)
            if (SettingsActivity.wasSettingsResetClicked(recvdData))
                reset();


    }



    private void reset() {
        //Reset the app to display the first contact
        mCurrentIndex = 0;

        //Reset the first contact first and update the Views displayed
        PokedexClass[0].setmCollect(false);
        mPCustomName.setText(R.string.customName); //this will invoke update()

        //Reset data for all the remaining contacts
        for (int i = 1; i < NUM_POKEMONS; i++) {
            PokedexClass[i].setmCollect(false);
            PokedexClass[i].setPokemonCustomName("Give It A Name");
        }

        //Since we are resetting, we should clear out all persistent data as well
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
    }

    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");


        if(mSound != null)
        {
            try{
                mSound.stop();
                mSound.release();
            }finally {
                mSound = null;
            }
        }
        //mPImage.setImageBitmap(null);


        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        for(int i = 0; i < NUM_POKEMONS; i++) {

            editor.putString(getString(PokedexClass[i].getmNameResId())+"Custom Name", PokedexClass[i].getPokemonCustomName());


            editor.putBoolean(getString(PokedexClass[i].getmNameResId())+"Collect", PokedexClass[i].ismCollect());

        }

        editor.apply();
    }





    private final TextWatcher nameEditTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            PokedexClass[mCurrentIndex].setPokemonCustomName(s.toString());
            update();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    };






    private void update(){

        mPImage.setImageResource(PokedexClass[mCurrentIndex].getmImageResId());
        mPName.setText(PokedexClass[mCurrentIndex].getmNameResId());
        mPType.setBackgroundResource(PokedexClass[mCurrentIndex].getTypeColor());
        mPType.setText(PokedexClass[mCurrentIndex].getTypeId());
        mPCategory.setText(PokedexClass[mCurrentIndex].getCategoryId());

        mPCheckBox.setChecked(PokedexClass[mCurrentIndex].ismCollect());


    }





    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Make sure to call the super method so that the states of our views are saved
        super.onSaveInstanceState(savedInstanceState);
        // Save our own state now
        savedInstanceState.putInt(KEY_POKEMON_INDEX, mCurrentIndex);



    }


}