package com.example.dat153oblig2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.dat153oblig2.Room.Animal;

import java.util.List;

public class DatabaseActivity extends AppCompatActivity {
    private AnimalViewModel animalViewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        AnimalAdapter adapter = new AnimalAdapter();
        recyclerView.setAdapter(adapter);

        animalViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AnimalViewModel.class);
        animalViewModel.getAllAnimals().observe(this, new Observer<List<Animal>>() {
            @Override
            public void onChanged(List<Animal> animals) {
                adapter.setAnimals(animals);
            }
        });
    }

    public Bitmap getBitmap(int imageID){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageID);
        return bitmap;
    }
}