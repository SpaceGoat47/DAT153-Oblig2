package com.example.dat153oblig2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "MainActivity";

    private Button btnQuiz, btnDatabase;
    private Intent intent;

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

        btnDatabase.setOnClickListener(this);
        btnQuiz.setOnClickListener(this);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
    }
}