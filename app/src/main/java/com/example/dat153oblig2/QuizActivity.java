package com.example.dat153oblig2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dat153oblig2.Room.Animal;
import com.example.dat153oblig2.Room.AnimalDAO;
import com.example.dat153oblig2.Room.AnimalDatabase;
// import com.example.dat153oblig2.Room.AnimalDAO;
// import com.example.dat153oblig2.Room.AnimalDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    // Deler av denne oppgaven er løst med inspirasjon fra lignende prosketer på GitHUB
    private static final String TAG = "QuizActivity";

    private final Random random = new Random();

    private final int ANSWERS_TOTAL = 3;

    private int nextCount;
    private List<Animal> shuffledAnimal;
    private List<String> options;
    private Animal currentAnimal;
    private int correctAnswer;

    private AnimalDAO animalDAO;

    // Views
    private ImageView ivImage;
    private final Button[] btnAnswers = new Button[ANSWERS_TOTAL];
    private TextView tvStatsPercent;
    private TextView tvStats;

    // Stats
    private int points;
    private int attempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        AnimalViewModel animalViewModel = new ViewModelProvider(this,
                (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(AnimalViewModel.class);

        ivImage = findViewById(R.id.ivImage);
        btnAnswers[0] = findViewById(R.id.btnAnswer0);
        btnAnswers[1] = findViewById(R.id.btnAnswer1);
        btnAnswers[2] = findViewById(R.id.btnAnswer2);
        tvStats = findViewById(R.id.tvStats);
        tvStatsPercent = findViewById(R.id.tvStatsPercent);

        //har kommentert bort litt greier her
        // dao = AnimalDatabase.getAllAnimals(getApplicationContext()).personDao();
        //dao = AnimalDAO.getAllAnimals(getApplicationContext()).personDao();
        shuffledAnimal = new ArrayList<>();
        shuffledAnimal = (List<Animal>) animalViewModel.getAllAnimals();
        //animalViewModel.getAllAnimals(); // returnerer liste av alle Animals i databasen
        //shuffledAnimal.size();



        //Collections.shuffle(shuffledAnimal);

        for (Button btn : btnAnswers) {
            btn.setOnClickListener(this);
        }

        nextAnimal();
    }

    private void nextAnimal() {
        currentAnimal = shuffledAnimal.get(nextCount++);
        if (nextCount == shuffledAnimal.size()) {
            Collections.shuffle(shuffledAnimal);
            nextCount = 0;
        }

        correctAnswer = random.nextInt(ANSWERS_TOTAL);

        options = new ArrayList<>(ANSWERS_TOTAL);
        for (int i = 0; i < ANSWERS_TOTAL; i++) {
            options.add(i == correctAnswer ? currentAnimal.getName() : randomName());
        }

        ivImage.setImageURI(currentAnimal.getUriImage());

        for (int i = 0; i < ANSWERS_TOTAL; i++) {
            btnAnswers[i].setText(options.get(i));
        }
    }

    private String randomName() {
        String randomName;
        do {
            randomName = shuffledAnimal.get(random.nextInt(shuffledAnimal.size())).getName();
        } while (randomName.equals(currentAnimal.getName()) || options.contains(randomName));
        return randomName;
    }

    @Override
    public void onClick(View view) {
        if (btnAnswers[correctAnswer].equals(view)) {
            points++;
        }
        attempts++;

        tvStats.setText(String.format(Locale.ROOT, "Score: %d/%d", points, attempts));
        tvStatsPercent.setText(String.format(Locale.ROOT, "%.0f%%", (points / (float)attempts * 100)));

        view.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        btnAnswers[correctAnswer].setBackgroundColor(ContextCompat.getColor(this, R.color.green));
        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) { }

            public void onFinish() {
                for (Button btn : btnAnswers) {
                    btn.setBackgroundColor(ContextCompat.getColor(view.getContext(), com.google.android.material.R.color.design_default_color_background));
                }
                nextAnimal();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
    }

    public void setShuffledAnimal(List<Animal> shuffledAnimal) {
        this.shuffledAnimal = shuffledAnimal;
    }
}