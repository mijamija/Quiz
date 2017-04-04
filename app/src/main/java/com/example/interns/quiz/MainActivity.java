package com.example.interns.quiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    EditText name;
    ArrayList<Score> highScores;
    TextView scores;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scores = (TextView) findViewById(R.id.scores);

        name = (EditText) findViewById(R.id.player);

        highScores = new ArrayList<>();

        setScores();

        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals(""))
                    Toast.makeText(MainActivity.this, "You need to enter a name!", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
                    intent.putExtra("name",name.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void setScores()
    {
        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://zoran.ogosense.net/api/get-leaderboard", new JsonHttpResponseHandler()
        {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try{
                    JSONArray list = response.getJSONArray("data");

                    for(int i = 0; i < 10; i++)
                    {
                        scores.setText(scores.getText().toString() + "\n" + (i+1) + ". " + list.getJSONObject(i).getString("name") + " " + list.getJSONObject(i).getInt("score"));
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStart() {
                progressDialog= new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
            }

            @Override
            public void onFinish() {
                    progressDialog.dismiss();
            }
        });
    }

    public void getScore()
    {
        Intent intent = getIntent();
        Score s;
        try {
             s = (Score) intent.getExtras().getSerializable("score");
        } catch (Exception e) {
            s = new Score();
        }

        if(s.getPlayer() != null)
        {
            AsyncHttpClient client = new AsyncHttpClient();

            client.post("http://zoran.ogosense.net/api/get-leaderboard", new JsonHttpResponseHandler()
            {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try{
                        
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStart() {
                    progressDialog= new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }

                @Override
                public void onFinish() {
                    progressDialog.dismiss();
                }
            });
        }

    }

}
