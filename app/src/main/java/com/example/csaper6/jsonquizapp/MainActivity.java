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

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private ArrayList<Question> questions;
    private Button butt1, butt2, butt3, butt4;
    private TextView questionText, pointText;
    private Question ranQuest;
    ArrayList<Answer> answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        answers = new ArrayList<>();

        butt1 = (Button) findViewById(R.id.button);
        butt2 = (Button) findViewById(R.id.button2);
        butt3 = (Button) findViewById(R.id.button3);
        butt4 = (Button) findViewById(R.id.button4);
        questionText = (TextView) findViewById(R.id.textView);
        pointText = (TextView) findViewById(R.id.textView2);

        setQuestions();

        askQuestion();

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

    private void askQuestion() {
        ranQuest = questions.get((int) (Math.random() * questions.size() -1 ));
        setButtons();
        questionText.setText(ranQuest.getQuestion());

        //get random question
        //set visual values

    }

    private void setButtons() {
        answers.add(ranQuest.getAnswer());
        answers.add(ranQuest.getAnswerFalse1());
        answers.add(ranQuest.getAnswerFalse2());
        answers.add(ranQuest.getAnswerFalse3());
        Collections.shuffle(answers);
        butt1.setText(answers.get(0).getAnswer());
        butt2.setText(answers.get(1).getAnswer());
        butt3.setText(answers.get(2).getAnswer());
        butt4.setText(answers.get(3).getAnswer());
    }

    private void setQuestions() {
        //questions.add(new Question("object" , "variable", findAnswer(), "false answer 1", "false answer2", "false answer3"));
        //format question: what is the [objects]'s [variable]?
        questions.add(new Question("Sun","Mass", new Answer(findAnswer(), true) , new Answer("678kg", false), new Answer("gjhrdb", false) , new Answer("correct", false)));


    }

    private String findAnswer() {
        String answer = "";
        String jsonString = "";
        try {
            InputStream fileInput = getAssets().open("Planets");
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

            Log.d(TAG, jsonData.optJSONObject(ranQuest.getObject()).optString(ranQuest.getVariable()));
            //find answer
            //get object and variable from question



        }


        return answer;
    }


}
