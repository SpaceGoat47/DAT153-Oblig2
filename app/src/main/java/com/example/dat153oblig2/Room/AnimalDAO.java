package com.example.dat153oblig2.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dat153oblig2.Room.Animal;

import java.util.List;

@Dao
public interface AnimalDAO {

    @Insert
    void insert(Animal animal);

    @Delete
    void delete(Animal animal);

    @Query("SELECT * FROM animals")
    LiveData<List<Animal>> getAllAnimals();

    //Sort method??
    @Query("SELECT * FROM animals ORDER BY name ASC")
    void sortAnimalsAsc();

    //TODO: descending too??
}
