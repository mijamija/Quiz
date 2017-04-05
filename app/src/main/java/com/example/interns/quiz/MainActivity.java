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
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    EditText name;
    TextView scores;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scores = (TextView) findViewById(R.id.scores);

        name = (EditText) findViewById(R.id.player);


        getScores();

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
                }
            }
        });
    }

    public void getScores()
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


}
