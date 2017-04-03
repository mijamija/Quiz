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

        questionNum = 0;
        score = 0;

        i = getIntent();
        playersScore = new Score();
        playersScore.setPlayer(i.getStringExtra("name"));

        update();
        questionNum++;
        radioGroup.clearCheck();

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (atLeastOneChecked()) {
                    if (questionNum < 5) {
                        update();
                        questionNum++;
                        radioGroup.clearCheck();
                    }
                    if (questionNum == 6) {
                        playersScore.setScore(score);
                        Intent intent = new Intent(QuestionsActivity.this, MainActivity.class);
                        intent.putExtra("score",playersScore);
                        startActivity(intent);
                        finish();
                    }
                    if (questionNum == 5) {
                        next.setText("Finish");
                        questionNum++;
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

    public boolean atLeastOneChecked()
    {
        int index = radioGroup.getCheckedRadioButtonId();

        RadioButton selectedAnswer = (RadioButton) findViewById(index);

        if(selectedAnswer == null)
            return false;
        return true;
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

                    question.setText("Question number " + (questionNum + 1) + ":\n" + list.getJSONObject(questionNum).getString("question"));

                    answer1.setText(list.getJSONObject(questionNum).getString("answer1"));
                    answer2.setText(list.getJSONObject(questionNum).getString("answer2"));
                    answer3.setText(list.getJSONObject(questionNum).getString("answer3"));
                    answer4.setText(list.getJSONObject(questionNum).getString("answer4"));

                    int index = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));

                    if((index + 1) == list.getJSONObject(questionNum).getInt("correct_answer"))
                        score++;
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
}
