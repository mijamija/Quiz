package com.example.interns.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText name;
    ArrayList<Score> highScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.player);

        highScores = new ArrayList<>(10);

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
}
