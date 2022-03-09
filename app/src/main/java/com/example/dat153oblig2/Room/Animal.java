package com.example.dat153oblig2.Room;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "animals")
public class Animal {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name; // private

    @NonNull
    @TypeConverters
    private Uri uriImage; // Private

    public Animal(String name, Uri uriImage) {
        this.name = name;
       this.uriImage = uriImage; //this.uriImage
    }


    //need this setter-method since it is not initialized in our constructor, if we want to set id
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Uri getUriImage() {
        return uriImage;
    }
}
