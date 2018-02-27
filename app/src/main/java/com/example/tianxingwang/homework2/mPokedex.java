package com.example.tianxingwang.homework2;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by tianxingwang on 1/25/18.
 */

public class mPokedex {
    public int getmNameResId() {
        return mNameResId;
    }

    //Set the resource ID representing the contact's name
    public void setmNameResId(int mNameResId) {
        this.mNameResId = mNameResId;
    }

    //Refers to a string defined in strings.xml
    private int mNameResId; //not storing actual text here!
    private int mTypeResId;
    private int mCategoryResId;
    private int mtypeColor;
    private boolean mCollect;


    public int getTypeColor(){return mtypeColor;}
    public void setTypeColor(int myColor){this.mtypeColor = myColor;}

    public int getTypeId(){return mTypeResId;}
    public void setTypeId(int myType){this.mTypeResId = myType;}


    public int getCategoryId(){return mCategoryResId;}
    public void setCategoryId(int myCategory){this.mCategoryResId= myCategory;}

    //Return the resource ID representing the contact's photo
    public int getmImageResId() {
        return mImageResId;
    }

    //Set the resource ID representing the contact's photo
    public void setmImageResId(int mImageResId) {
        this.mImageResId = mImageResId;
    }

    //Refers to an image defined in res/drawable (one image for all DPIs for now)
    private int mImageResId; //not storing actual images here!

    //A user-defined location, we'll store the string the user enters in this object
    // and back it up to a file for persistent storage
    private String pokemonCustomName;

    public String getPokemonCustomName() {
        return pokemonCustomName;
    }

    public void setPokemonCustomName(String name) {
        this.pokemonCustomName = name;
    }


    public boolean ismCollect() {
        return mCollect;
    }

    //Set whether this wiseman been found.  (true -> found, false -> not found)
    public void setmCollect(boolean collect) {
        this.mCollect = collect;
    }

    //Constructor for a contact sets its name and image resources by parameter,
    // but always sets the location to ??? initially
    public mPokedex(int nameResId, int imageResId, int typeResId, int categoryResId, int color) {
        mNameResId = nameResId;
        mImageResId = imageResId;
        mTypeResId = typeResId;
        mCategoryResId = categoryResId;
        mtypeColor = color;
        mCollect=false;
        pokemonCustomName = "Give It A Name";
    }


}
