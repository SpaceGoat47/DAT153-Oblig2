package com.example.dat153oblig2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "MainActivity";

    private Button btnQuiz, btnDatabase;
    private Intent intent;
    private FloatingActionButton fabBtn;

    @Override
    public void onClick(View view) {

        Log.d(TAG, "onClick: " + view.getResources().getResourceEntryName(view.getId()));

        switch(view.getId()){
            case R.id.btnDatabase:
                intent = new Intent(this, DatabaseActivity.class);
                startActivity(intent);
                break;
            case R.id.btnQuiz:
                intent = new Intent(this, QuizActivity.class);
                startActivity(intent);
                break;
            case R.id.fabAdd:
                intent = new Intent(this, AddEntryActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate");

        btnDatabase = findViewById(R.id.btnDatabase);
        btnQuiz = findViewById(R.id.btnQuiz);
        fabBtn = findViewById(R.id.fabAdd);

        btnDatabase.setOnClickListener(this);
        btnQuiz.setOnClickListener(this);
        fabBtn.setOnClickListener(this);

    }
}