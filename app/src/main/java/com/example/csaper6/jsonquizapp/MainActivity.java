package com.example.csaper6.jsonquizapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private Button butt1, butt2, butt3, butt4;
    private TextView questionText, pointText;
    private Problem randProb;
    private ArrayList<Question> questions;
    private ArrayList<Answer> currentAnswers;
    private ArrayList<Problem> problems;
    private ArrayList<String> falseAnswers;
    private Map<String, Integer> planetCats;
    private boolean correct = false;
    private int points = 0;
    private View coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        planetCats = new HashMap<>();
        falseAnswers = new ArrayList<>();
        currentAnswers = new ArrayList<>();
        questions = new ArrayList<>();
        problems = new ArrayList<>();
        //wire widgets
        butt1 = (Button) findViewById(R.id.button);
        butt2 = (Button) findViewById(R.id.button2);
        butt3 = (Button) findViewById(R.id.button3);
        butt4 = (Button) findViewById(R.id.button4);
        questionText = (TextView) findViewById(R.id.textView);
        pointText = (TextView) findViewById(R.id.textView2);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        setArrays();
        createProblems();
        setDisplay();

        butt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(0);
            }
        });
        butt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(1);
            }
        });
        butt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(2);
            }
        });
        butt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(3);
            }
        });

    }

    private void setArrays() {
        //hashmap vars
        planetCats.put("Distance", 0);
        planetCats.put("Mass", 1);
        planetCats.put("Temperature", 2);
        planetCats.put("Volume", 3);
        //fake answer choices
        falseAnswers.add("69,816");
        falseAnswers.add("440");
        falseAnswers.add("3.3011×10^23");
        falseAnswers.add("108,939,000");
        //new question object
        questions.add(new Question("Sun","Mass"));
        questions.add(new Question("Mercury" , "Distance"));
    }

    private String getFalseAnswer(){
        return falseAnswers.get((int) (Math.random() * falseAnswers.size() -1 ));
    }

    private void createProblems() {
        for(int i = 0; i <= questions.size()-1; i++){
            problems.add(new Problem(questions.get(i), new Answer(findAnswer(questions.get(i)), true),
                    new Answer(getFalseAnswer(), false) , new Answer(getFalseAnswer(), false), new Answer(getFalseAnswer(), false)));
        }
    }

    private void setDisplay() {
        randProb = problems.get((int) (Math.random() * questions.size() -1 ));
        setButtons();
        questionText.setText(randProb.getQuest().getQuestion());
    }

    private void setButtons() {
        currentAnswers.add(randProb.getAnswer());
        currentAnswers.add(randProb.getFalse1());
        currentAnswers.add(randProb.getFalse2());
        currentAnswers.add(randProb.getFalse3());
        Collections.shuffle(currentAnswers);
        butt1.setText(currentAnswers.get(0).getAnswer());
        butt2.setText(currentAnswers.get(1).getAnswer());
        butt3.setText(currentAnswers.get(2).getAnswer());
        butt4.setText(currentAnswers.get(3).getAnswer());
    }

    private void checkAnswer(int i) {
        if(currentAnswers.get(i).getCorrect())
        {
            Toast.makeText(MainActivity.this, "CORRECT", Toast.LENGTH_SHORT).show();
            correct = true;
            points++;
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Correct", Snackbar.LENGTH_INDEFINITE).setAction("Next", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setDisplay();
                }
            });
            snackbar.show();
        }
        else{
            Toast.makeText(MainActivity.this, "WRONG", Toast.LENGTH_SHORT).show();
            points--;
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Correct Answer", Snackbar.LENGTH_INDEFINITE).setAction("Next", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setDisplay();
                }
            });
            snackbar.show();
        }

        //check if clicked is right answer
        //if yes; ++point
        //if no; reveal answer, --point

        correct = false;
    }

    private String findAnswer(Question quest) {
        String answer = "";
        String jsonString = "";
        try {
            String[] files = getAssets().list("");
            String fileNames = "";
            for(String s : files) {
                fileNames+=s + " ";

            }
            Log.d(TAG, "findAnswer: " + fileNames);
            InputStream fileInput = getAssets().open("Planets.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
            String line;

            while((line = reader.readLine()) != null){
                jsonString += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "onCreate: FILED NOT FOUND ");
        }
        JSONObject jsonData = null;
        try {
            jsonData = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(jsonData != null){
            Log.d(TAG, "findAnswer: " + planetCats.get(quest.getVariable()));
            answer = jsonData.optJSONArray(quest.getObject()).optJSONObject(planetCats.get(quest.getVariable())).optString(quest.getVariable());
        }
        return answer;
    }


    // clear display after answered!! (clear answer array)
}
