package com.example.interns.quiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.R.id.list;

public class QuestionsActivity extends AppCompatActivity {

    TextView question;
    RadioButton answer1, answer2, answer3, answer4;
    RadioGroup radioGroup;
    Button next, hint;
    int questionNum, score;
    Score playersScore;
    Intent i;
    ProgressDialog progressDialog;
    ArrayList<Quizie> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        question = (TextView) findViewById(R.id.question);

        answer1 = (RadioButton) findViewById(R.id.answer1);
        answer2 = (RadioButton) findViewById(R.id.answer2);
        answer3 = (RadioButton) findViewById(R.id.answer3);
        answer4 = (RadioButton) findViewById(R.id.answer4);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        questions = new ArrayList<>();

        questionNum = 0;
        score = 0;

        i = getIntent();
        playersScore = new Score();
        playersScore.setPlayer(i.getStringExtra("name"));

        update();



        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (atLeastOneChecked()) {
                    if (questionNum < 9) {
                        setEverythingUp();
                        questionNum++;
                        radioGroup.clearCheck();
                        return;
                    }
                    if (questionNum == 10) {
                        playersScore.setScore(10);
                        sendScores();
                        Intent intent = new Intent(QuestionsActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (questionNum == 9) {
                        next.setText("Finish");
                        setEverythingUp();
                        questionNum++;
                        radioGroup.clearCheck();
                    }
                } else {
                    Toast.makeText(QuestionsActivity.this, "You need to check at least one", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        hint = (Button) findViewById(R.id.hint);
//        hint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(QuestionsActivity.this, list.get(questionNum - 1).getHint(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    public void setEverythingUp()
    {

        question.setText("Question number " + (questionNum + 1) + ":\n" + questions.get(questionNum).getQuestion());

        answer1.setText(questions.get(questionNum).getAnswerOne());
        answer2.setText(questions.get(questionNum).getAnswerTwo());
        answer3.setText(questions.get(questionNum).getAnswerThree());
        answer4.setText(questions.get(questionNum).getAnswerFour());

        int index = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));

        if((index) == questions.get(questionNum + 1).getCorrectAnswer())
            score++;

    }

    public boolean atLeastOneChecked()
    {
        int index = radioGroup.getCheckedRadioButtonId();

        RadioButton selectedAnswer = (RadioButton) findViewById(index);

        return selectedAnswer != null;
    }

    public void update()
    {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://zoran.ogosense.net/api/get-questions", new JsonHttpResponseHandler()
        {
            @Override
            public void onStart() {
                progressDialog= new ProgressDialog(QuestionsActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try{
                    JSONArray list = response.getJSONArray("data");

                    Quizie[] item = new Quizie[10];

                    for(int i = 0; i < list.length(); i++)
                    {
                        item[i] = new Quizie();
                        item[i].setAnswerOne(list.getJSONObject(i).getString("answer1"));
                        item[i].setAnswerTwo(list.getJSONObject(i).getString("answer2"));
                        item[i].setAnswerThree(list.getJSONObject(i).getString("answer3"));
                        item[i].setAnswerFour(list.getJSONObject(i).getString("answer4"));

                        item[i].setQuestion(list.getJSONObject(i).getString("question"));

                        item[i].setCorrectAnswer(list.getJSONObject(i).getInt("correct_answer"));

                        questions.add(item[i]);
                    }

                    setEverythingUp();
                    questionNum++;
                    radioGroup.clearCheck();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
            }
        });
    }

    public void sendScores() {
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("name", playersScore.getPlayer());
        params.put("score", playersScore.getScore());

        try {
            client.post("http://zoran.ogosense.net/api/set-score", params, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
//
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
