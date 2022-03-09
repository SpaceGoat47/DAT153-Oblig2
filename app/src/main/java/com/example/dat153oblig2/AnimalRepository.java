package com.example.dat153oblig2;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.dat153oblig2.Room.Animal;
import com.example.dat153oblig2.Room.AnimalDAO;
import com.example.dat153oblig2.Room.AnimalDatabase;

import java.util.List;

public class AnimalRepository {

    //Need to access the DAO from the repository
    private AnimalDAO animalDAO;
    //Container to get all the animals from the database
    private LiveData<List<Animal>> allAnimals;

    //constructor with Application parameter
    //Application is a subclass of context
    public AnimalRepository(Application application){
        //create the database
        AnimalDatabase database = AnimalDatabase.getInstance(application);
        animalDAO = database.animalDAO(); //calls abstract method in AnimalDatabase -- since using Room.databaseBuilder(), Room take cares of the code in the method body
        allAnimals = animalDAO.getAllAnimals(); //returns all the animals in the 'animals'-database (through AnimalDAO) -- Room autogenerates the code
    }

    //create methods for all our database operations
    //Room doesn't allow database operations on the Main thread cause of crashes
    //these methods are the API's that are exposed to the outside, ViewModel gonna call these
    public void insert(Animal animal){
        new InsertAsyncTask(animalDAO).execute(animal);
    }

    public void delete(Animal animal){
        new DeleteAsyncTask(animalDAO).execute(animal);
    }

    public LiveData<List<Animal>> sortAsc(){
        new SortAscAsyncTask(animalDAO).execute();
        return allAnimals;
    }

    //automatically returns the livedata on a background thread
    public LiveData<List<Animal>> getAllAnimals() {
        return allAnimals;
    }

    //needs to be static so it doesnt have access to the repository itself -- can cause memory leaks
    private static class InsertAsyncTask extends AsyncTask<Animal, Void, Void>{
        private AnimalDAO animalDAO;

        //need a constructor for the AnimalDAO since we cant access the AnimalDAO of our repository
        private InsertAsyncTask(AnimalDAO animalDAO){
            this.animalDAO = animalDAO;
        }

        @Override
        protected Void doInBackground(Animal... animals) {
            animalDAO.insert(animals[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Animal, Void, Void>{
        private AnimalDAO animalDAO;

        private DeleteAsyncTask(AnimalDAO animalDAO){
            this.animalDAO = animalDAO;
        }

        @Override
        protected Void doInBackground(Animal... animals) {
            animalDAO.delete(animals[0]);
            return null;
        }
    }

    private static class SortAscAsyncTask extends AsyncTask<Void, Void, LiveData<List<Animal>>>{
        private AnimalDAO animalDAO;

        private SortAscAsyncTask(AnimalDAO animalDAO){
            this.animalDAO = animalDAO;
        }

        @Override
        protected LiveData<List<Animal>> doInBackground(Void... voids) {
            return animalDAO.sortAnimalsAsc();
        }
    }

}
