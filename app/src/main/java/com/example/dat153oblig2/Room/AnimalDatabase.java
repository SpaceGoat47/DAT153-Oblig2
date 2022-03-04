package com.example.dat153oblig2.Room;

import static com.example.dat153oblig2.ImageConverter.getUri;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.dat153oblig2.Converters;
import com.example.dat153oblig2.R;

@Database(entities = {Animal.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AnimalDatabase extends RoomDatabase {

    //abstract DAO method with no body
    public abstract AnimalDAO animalDAO();

    private static AnimalDatabase instance = null;

    //thread-safe singleton method
    public static synchronized AnimalDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AnimalDatabase.class, "animal_database")
                    .fallbackToDestructiveMigration() //when db version doesnt correspond to actual version(??) -- increments, this deletes our database and create it from scratch
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            new InitializeAsyncTask(instance).execute();
            super.onCreate(db);
        }
    };

    private static class InitializeAsyncTask extends AsyncTask<Void, Void, Void>{
        private AnimalDAO animalDAO;

        private InitializeAsyncTask(AnimalDatabase db){
            animalDAO = db.animalDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            animalDAO.insert(new Animal("Cat", getUri(R.drawable.cat)));
            animalDAO.insert(new Animal("Dog", getUri(R.drawable.dog)));
            animalDAO.insert(new Animal("Horse", getUri(R.drawable.horse)));
            animalDAO.insert(new Animal("Koala", getUri(R.drawable.koala)));
            animalDAO.insert(new Animal("Monkey", getUri(R.drawable.monkey)));
            animalDAO.insert(new Animal("Polar bear", getUri(R.drawable.polarbear)));

            return null;
        }
    }

    public Bitmap getBitmap(int imageID){
        Bitmap bitmap = getBitmap(R.drawable.cat);
        return bitmap;
    }
}
