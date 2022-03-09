package com.example.dat153oblig2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.sql.Date;


public class Converters {

/*    @TypeConverter
    public static byte[] fromBitmap(Bitmap bitmapPicture){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @TypeConverter
    public static Bitmap toBitmap(byte[] byteArray){
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }*/

    @TypeConverter
    public static Uri fromString(String uriString){
        return Uri.parse(uriString);
    }

    @TypeConverter
    public static String uriToString(Uri uri){
        return uri.toString();
    }
}
