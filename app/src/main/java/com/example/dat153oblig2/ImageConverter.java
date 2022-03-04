package com.example.dat153oblig2;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;

public class ImageConverter {
    public static Uri getUri(int imageID){
        Uri imageUri = Uri.parse("android.resource://com.example.dat153oblig2/" + imageID);
        return imageUri;
    }
}
