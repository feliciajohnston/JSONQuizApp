package com.example.csaper6.jsonquizapp;

import android.os.Bundle;
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
    private ArrayList<Question> questions;
    private Button butt1, butt2, butt3, butt4;
    private TextView questionText, pointText;
    private Problem randProb;
    private ArrayList<Answer> answers;
    private ArrayList<Problem> problems;
    private Map<String, Integer> planetCats;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        planetCats = new HashMap<>();
        planetCats.put("Distance", 0);
        planetCats.put("Mass", 1);
        planetCats.put("Temperature", 2);
        planetCats.put("Volume", 3);

        answers = new ArrayList<>();
        questions = new ArrayList<>();
        problems = new ArrayList<>();

        butt1 = (Button) findViewById(R.id.button);
        butt2 = (Button) findViewById(R.id.button2);
        butt3 = (Button) findViewById(R.id.button3);
        butt4 = (Button) findViewById(R.id.button4);
        questionText = (TextView) findViewById(R.id.textView);
        pointText = (TextView) findViewById(R.id.textView2);

        setQuestions();
        setAnswers();
        askProblem();

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

    private void setQuestions() {
        //format question: what is the [objects]'s [variable]?
        questions.add(new Question("Sun","Mass"));
        questions.add(new Question("Mercury" , "Distance"));
    }

    private void setAnswers() {
        for(int i = 0; i <= questions.size()-1; i++){
            problems.add(new Problem(questions.get(i), new Answer(findAnswer(questions.get(i)), true),
                    new Answer("blah", false), new Answer("merp", false), new Answer("meh", false)));
        }
    }

    private void checkAnswer(int i) {
        if(answers.get(i).getCorrect())
        {
            Toast.makeText(MainActivity.this, "CORRECT", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "WRONG", Toast.LENGTH_SHORT).show();
        }

        //check if clicked is right answer
        //if yes; ++point
        //if no; reveal answer, --point

    }

    private void askProblem() {
        randProb = problems.get((int) (Math.random() * questions.size() -1 ));
        setButtons();
        questionText.setText(randProb.getQuest().getQuestion());
    }

    private void setButtons() {
        answers.add(randProb.getAnswer());
        answers.add(randProb.getFalse1());
        answers.add(randProb.getFalse2());
        answers.add(randProb.getFalse3());
        Collections.shuffle(answers);
        butt1.setText(answers.get(0).getAnswer());
        butt2.setText(answers.get(1).getAnswer());
        butt3.setText(answers.get(2).getAnswer());
        butt4.setText(answers.get(3).getAnswer());
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


}
