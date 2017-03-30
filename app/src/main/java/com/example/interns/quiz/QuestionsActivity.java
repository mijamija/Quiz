package com.example.interns.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity {

    TextView question;
    RadioButton answer1, answer2, answer3, answer4;
    RadioGroup radioGroup;
    Button next, hint;
    ArrayList<Quizie> list;
    int questionNum, score;
    Score playersScore;
    Intent i;

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

        list = new ArrayList<>();
        makeList(list);

        update();
        if(checkCorrectAnswer())
            score++;
        questionNum++;
        radioGroup.clearCheck();

        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (atLeastOneChecked()) {
                    if (questionNum < 5) {
                        update();
                        if (checkCorrectAnswer())
                            score++;
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

        hint = (Button) findViewById(R.id.hint);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuestionsActivity.this, list.get(questionNum - 1).getHint(), Toast.LENGTH_SHORT).show();
            }
        });

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
        String startQuestion = "Question number ";

        question.setText(startQuestion + (questionNum + 1) + ":\n" + list.get(questionNum).getQuestion());

        answer1.setText(list.get(questionNum).getAnswerOne());
        answer2.setText(list.get(questionNum).getAnswerTwo());
        answer3.setText(list.get(questionNum).getAnswerThree());
        answer4.setText(list.get(questionNum).getAnswerFour());
    }

    public boolean checkCorrectAnswer()
    {
        int index = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));

        if((index + 1) == list.get(questionNum).getCorrectAnswer())
            return true;
        else
            return false;
    }

    public void makeList(ArrayList<Quizie> list)
    {

        Quizie item1 = new Quizie();
        Quizie item2 = new Quizie();
        Quizie item3 = new Quizie();
        Quizie item4 = new Quizie();
        Quizie item5 = new Quizie();

        item1.setQuestion("Where would you find the Sea of tranquility?");
        item1.setAnswerOne("In Australia");
        item1.setAnswerTwo("In Alaska");
        item1.setAnswerThree("On the Moon");
        item1.setAnswerFour("In India");
        item1.setCorrectAnswer(3);
        item1.setHint("It's a planet");
        list.add(item1);

        item2.setQuestion("In which film did Humphrey Bogart say, \"We'll always have Paris?\" ");
        item2.setAnswerOne("Holiday in Rome");
        item2.setAnswerTwo("Casablanca");
        item2.setAnswerThree("Breakfast at Tiffanys");
        item2.setAnswerFour("Always");
        item2.setCorrectAnswer(2);
        item2.setHint("Movie is in black and white");
        list.add(item2);

        item3.setQuestion("How many colors are there in the rainbow?");
        item3.setAnswerOne("7");
        item3.setAnswerTwo("12");
        item3.setAnswerThree("8");
        item3.setAnswerFour("5");
        item3.setCorrectAnswer(1);
        item3.setHint("Less than 8, but more than 6");
        list.add(item3);

        item4.setQuestion("What is rum made from?");
        item4.setAnswerOne("Corn");
        item4.setAnswerTwo("Potato");
        item4.setAnswerThree("Coconut");
        item4.setAnswerFour("Sugar cane");
        item4.setCorrectAnswer(4);
        item4.setHint("It's sweet");
        list.add(item4);

        item5.setQuestion("Who is the best character in Game of Thrones?");
        item5.setAnswerOne("Sersei");
        item5.setAnswerTwo("Tyrion");
        item5.setAnswerThree("Khal Drogo");
        item5.setAnswerFour("Jon Snow");
        item5.setCorrectAnswer(2);
        item5.setHint("That's what I do, I drink and I know things");
        list.add(item5);
    }
}
