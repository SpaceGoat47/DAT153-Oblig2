package com.example.dat153oblig2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dat153oblig2.Room.Animal;

import java.nio.channels.InterruptedByTimeoutException;

public class AddEntryActivity extends AppCompatActivity {
    private static final String TAG = "AddEntryActivity";

    private Button btnChooseImage;
    private ImageView imgImage;
    private EditText edtName;
    private ActivityResultLauncher<Intent> chooseImageResult;
    private Uri uriImage;
    private AnimalViewModel animalViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        Log.d(TAG, "onCreate");

        btnChooseImage = findViewById(R.id.btnChooseImage);
        imgImage = findViewById(R.id.imgImage);
        edtName = findViewById(R.id.edtName);

        animalViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AnimalViewModel.class);

        chooseImageResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() != Activity.RESULT_OK || result.getData() == null) return;
            uriImage = result.getData().getData();
            getContentResolver().takePersistableUriPermission(uriImage, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Log.d(TAG, "onActivityResult: uri: " + uriImage);
            Log.d(TAG, "onActivityResult: result: " + result);
            imgImage.setImageURI(uriImage);
        });

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    private void saveAnimal(){
        String name = edtName.getText().toString();

        if(name.trim().isEmpty()){
            Toast.makeText(this, "Please fill in a name", Toast.LENGTH_SHORT).show();
        } else{
            Animal animal = new Animal(name, uriImage);
            animalViewModel.insert(animal);

            Toast.makeText(this, String.format("%s added to database", name), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, DatabaseActivity.class);
            startActivity(intent);
        }
    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        chooseImageResult.launch(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.saveNote:
                saveAnimal();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}