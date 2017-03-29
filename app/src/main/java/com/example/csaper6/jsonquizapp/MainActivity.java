package com.example.csaper6.jsonquizapp;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.TransitionManager;

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
    private Button butt1, butt2, butt3, butt4, resetButt, nextButt;
    private TextView questionText, pointText;
    private Problem randProb;
    private ArrayList<Question> questions;
    private ArrayList<Answer> currentAnswers;
    private ArrayList<Problem> problems;
    private ArrayList<String> falseAnswers;
    private Map<String, Integer> planetCats;
    private String snackBarAnswer;
    private int points = 0;
    private int currentQuestion;
    private View coordinatorLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewGroup transitionsContainer = (ViewGroup) findViewById(R.id.transitions_container);

        currentQuestion = 0;
        planetCats = new HashMap<>();
        falseAnswers = new ArrayList<>();
        currentAnswers = new ArrayList<>();
        questions = new ArrayList<>();
        problems = new ArrayList<>();

        butt1 = (Button) transitionsContainer.findViewById(R.id.button);
        butt2 = (Button) transitionsContainer.findViewById(R.id.button2);
        butt3 = (Button) transitionsContainer.findViewById(R.id.button3);
        butt4 = (Button) transitionsContainer.findViewById(R.id.button4);
        resetButt = (Button) findViewById(R.id.button5);
        nextButt = (Button) findViewById(R.id.button6);
        questionText = (TextView) findViewById(R.id.textView);
        pointText = (TextView) findViewById(R.id.textView2);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        setArrays();
        createProblems();
        setDisplay();

        butt1.setOnClickListener(new View.OnClickListener() {
            boolean mColorsInverted = false;
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(transitionsContainer, new Recolor());

                mColorsInverted = !mColorsInverted;
                butt1.setTextColor(getResources().getColor(!mColorsInverted ? R.color.colorAccent2 : R.color.colorAccent));
                butt1.setBackgroundDrawable(new ColorDrawable(getResources().getColor(!mColorsInverted ? R.color.colorAccent : R.color.colorAccent2)));
                checkAnswer(0);
            }
        });
        butt2.setOnClickListener(new View.OnClickListener() {
            boolean mColorsInverted;
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(transitionsContainer, new Recolor());

                mColorsInverted = !mColorsInverted;
                butt2.setTextColor(getResources().getColor(!mColorsInverted ? R.color.colorAccent : R.color.colorAccent2));
                butt2.setBackgroundDrawable(new ColorDrawable(getResources().getColor(!mColorsInverted ? R.color.colorAccent2 : R.color.colorAccent)));
                checkAnswer(1);
            }
        });
        butt3.setOnClickListener(new View.OnClickListener() {
            boolean mColorsInverted;
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(transitionsContainer, new Recolor());

                mColorsInverted = !mColorsInverted;
                butt3.setTextColor(getResources().getColor(!mColorsInverted ? R.color.colorAccent : R.color.colorAccent2));
                butt3.setBackgroundDrawable(new ColorDrawable(getResources().getColor(!mColorsInverted ? R.color.colorAccent2 : R.color.colorAccent)));
                checkAnswer(2);
            }
        });
        butt4.setOnClickListener(new View.OnClickListener() {
            boolean mColorsInverted;
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(transitionsContainer, new Recolor());

                mColorsInverted = !mColorsInverted;
                butt4.setTextColor(getResources().getColor(!mColorsInverted ? R.color.colorAccent : R.color.colorAccent2));
                butt4.setBackgroundDrawable(new ColorDrawable(getResources().getColor(!mColorsInverted ? R.color.colorAccent2 : R.color.colorAccent)));
                checkAnswer(3);
            }
        });
        resetButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentQuestion = 0;
                setDisplay();
            }
        });
        nextButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNext();
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
        falseAnswers.add("10");
        falseAnswers.add("300");
        falseAnswers.add("568");
        falseAnswers.add("21");
        falseAnswers.add("Correct Answer");
        falseAnswers.add("Pick Me");
        falseAnswers.add("Click Here");
        falseAnswers.add("Wrong Answer");
        //new question object
        //mass, distance, temperature, volume of sun-earth
        questions.add(new Question("Sun", "Mass"));
        questions.add(new Question("Mercury", "Distance"));
        questions.add(new Question("Venus", "Temperature"));
        questions.add(new Question("Earth", "Volume"));
        questions.add(new Question("Sun", "Temperature"));
        questions.add(new Question("Mercury", "Mass"));
        questions.add(new Question("Venus", "Distance"));
        questions.add(new Question("Earth", "Distance"));
        questions.add(new Question("Sun", "Volume"));
        questions.add(new Question("Mercury", "Temperature"));
        questions.add(new Question("Venus", "Mass"));
        questions.add(new Question("Earth", "Mass"));
        questions.add(new Question("Sun", "Distance"));
        questions.add(new Question("Mercury", "Volume"));
        questions.add(new Question("Venus", "Volume"));
        questions.add(new Question("Earth", "Temperature"));
    }

    private String getFalseAnswer() {
        return falseAnswers.get((int) (Math.random() * falseAnswers.size() - 1));
    }

    private void createProblems() {
        for (int i = 0; i <= questions.size() - 1; i++) {
            problems.add(new Problem(questions.get(i), new Answer(findAnswer(questions.get(i)), true),
                    new Answer(getFalseAnswer(), false), new Answer(getFalseAnswer(), false), new Answer(getFalseAnswer(), false)));
        }
    }

    private void setDisplay() {
        if (currentQuestion > problems.size() - 1) {
            questionText.setText("Done with Quiz!");
            butt1.setVisibility(View.GONE);
            butt2.setVisibility(View.GONE);
            butt3.setVisibility(View.GONE);
            butt4.setVisibility(View.GONE);
            nextButt.setVisibility(View.GONE);
            resetButt.setVisibility(View.VISIBLE);
        } else {
            butt1.setVisibility(View.VISIBLE);
            butt2.setVisibility(View.VISIBLE);
            butt3.setVisibility(View.VISIBLE);
            butt4.setVisibility(View.VISIBLE);
            nextButt.setVisibility(View.VISIBLE);
            resetButt.setVisibility(View.GONE);
            randProb = problems.get(currentQuestion);
            setButtons();
            questionText.setText(randProb.getQuest().getQuestion());
            currentQuestion++;
        }
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

    private void checkAnswer(int i){

        if (currentAnswers.get(i).getCorrect()) {
            points++;
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Correct", Snackbar.LENGTH_INDEFINITE).setAction("Next", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            snackbar.show();

        } else {
            for (int j = 0; j < 4; j++) {
                if (currentAnswers.get(j).getCorrect()) {
                    snackBarAnswer = currentAnswers.get(j).getAnswer();
                }
            }
            points--;
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Answer: " + snackBarAnswer, Snackbar.LENGTH_INDEFINITE).setAction("Next", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            snackbar.show();
        }

        pointText.setText("Points: " + points);

    }

    private void showNext() {
        currentAnswers.clear();
        setDisplay();
    }

    private String findAnswer(Question quest) {
        String answer = "";
        String jsonString = "";
        try {
            String[] files = getAssets().list("");
            String fileNames = "";
            for (String s : files) {
                fileNames += s + " ";

            }
            Log.d(TAG, "findAnswer: " + fileNames);
            InputStream fileInput = getAssets().open("Planets.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
            String line;

            while ((line = reader.readLine()) != null) {
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

        if (jsonData != null) {
            Log.d(TAG, "findAnswer: " + planetCats.get(quest.getVariable()));
            answer = jsonData.optJSONArray(quest.getObject()).optJSONObject(planetCats.get(quest.getVariable())).optString(quest.getVariable());
        }
        return answer;
    }


}
