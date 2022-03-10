package com.example.dat153oblig2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dat153oblig2.Room.Animal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.List;

public class DatabaseActivity extends AppCompatActivity {

    public static final String TAG = "DatabaseActivity";
    private AnimalViewModel animalViewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton fabBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        Log.d(TAG, "onCreate");

        fabBtn = findViewById(R.id.fabAdd);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatabaseActivity.this, AddEntryActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setHasFixedSize(true);

        AnimalAdapter adapter = new AnimalAdapter();
        recyclerView.setAdapter(adapter);

        animalViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AnimalViewModel.class);
        animalViewModel.getAllAnimals().observe(this, new Observer<List<Animal>>() {
            @Override
            public void onChanged(List<Animal> animals) {
                Log.d(TAG, "onChanged");
                adapter.setAnimals(animals);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                animalViewModel.delete(adapter.getAnimalAt(viewHolder.getAdapterPosition()));
                Toast.makeText(DatabaseActivity.this, "Animal removed", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.database_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.sortAscending:
                animalViewModel.sortAsc();
                Toast.makeText(this, "Database sorted ascendingly", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}