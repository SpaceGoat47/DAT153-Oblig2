package com.example.dat153oblig2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dat153oblig2.Room.Animal;

import java.util.List;

public class AnimalViewModel extends AndroidViewModel {
    private AnimalRepository repository;
    private LiveData<List<Animal>> allAnimals;

    public AnimalViewModel(@NonNull Application application) {
        super(application);
        repository = new AnimalRepository(application);
        this.allAnimals = repository.getAllAnimals();
    }

    //Our activities can only access our ViewModel, so we need wrapper methods from AnimalRepository.class
    public void insert(Animal animal){
        repository.insert(animal);
    }

    public void delete(Animal animal){
        repository.delete(animal);
    }

    public void sortAsc(){
        repository.sortAsc();
    }

    public LiveData<List<Animal>> getAllAnimals(){
        return allAnimals;
    }
}
