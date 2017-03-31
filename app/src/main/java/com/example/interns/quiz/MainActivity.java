package com.example.interns.quiz;

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

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText name;
    ArrayList<Score> highScores;
    TextView scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scores = (TextView) findViewById(R.id.scores);

        name = (EditText) findViewById(R.id.player);

        highScores = new ArrayList<>();

        getScore();
        displayScore();

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

    public void displayScore()
    {
        for(int i = 0; i < highScores.size(); i++)
        {
            scores.setText(i+1 + ". " + highScores.get(i).getPlayer() + " " + highScores.get(i).getScore() + "\n");
        }
    }

    public void getScore()
    {
        int temp;

        Intent intent = getIntent();
        Score s;
        try {
             s = (Score) intent.getExtras().getSerializable("score");
        } catch (Exception e) {
            s = new Score();
        }

        if(highScores.size() == 0 && s.getPlayer() != null)
            highScores.add(s);
        else
        for(Score one : highScores)
        {
            if(s.getScore() > one.getScore())
            {
                temp = one.getScore();
                one.setScore(s.getScore());
                s.setScore(temp);
            }
        }
    }

}
