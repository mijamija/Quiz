package com.example.interns.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity {

    TextView question;
    RadioButton answer1, answer2, answer3, answer4;
    ArrayList<Quizie> list;
    int questionNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        question = (TextView) findViewById(R.id.question);

        answer1 = (RadioButton) findViewById(R.id.answer1);
        answer2 = (RadioButton) findViewById(R.id.answer2);
        answer3 = (RadioButton) findViewById(R.id.answer3);
        answer4 = (RadioButton) findViewById(R.id.answer4);

        list = new ArrayList<>();
        makeList(list);

        update();

        Button next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionNum <= 5)
                    update();


            }
        });

    }

    public void update()
    {
        questionNum++;
        question.setText(question.getText().toString() + questionNum + ":\n" + list.get(questionNum).getQuestion());

        answer1.setText(list.get(questionNum).getAnswerOne());
        answer2.setText(list.get(questionNum).getAnswerTwo());
        answer3.setText(list.get(questionNum).getAnswerThree());
        answer4.setText(list.get(questionNum).getAnswerFour());
    }

    public void makeList(ArrayList<Quizie> list)
    {

        Quizie item = new Quizie();

        item.setQuestion("Where would you find the Sea of tranquility?");
        item.setAnswerOne("In Australia");
        item.setAnswerTwo("In Alaska");
        item.setAnswerThree("On the Moon");
        item.setAnswerFour("In India");
        item.setCorrectAnswer(3);
        list.add(item);

        item.setQuestion("In which film did Humphrey Bogart say, \"We'll always have Paris?\" ");
        item.setAnswerOne("Holiday in Rome");
        item.setAnswerTwo("Casablanca");
        item.setAnswerThree("Breakfast at Tiffanys");
        item.setAnswerFour("Always");
        item.setCorrectAnswer(2);
        list.add(item);

        item.setQuestion("How many colors are there in the rainbow?");
        item.setAnswerOne("7");
        item.setAnswerTwo("12");
        item.setAnswerThree("8");
        item.setAnswerFour("5");
        item.setCorrectAnswer(1);
        list.add(item);

        item.setQuestion("What is rum made from?");
        item.setAnswerOne("Corn");
        item.setAnswerTwo("Potato");
        item.setAnswerThree("Coconut");
        item.setAnswerFour("Sugar cane");
        item.setCorrectAnswer(4);
        list.add(item);

        item.setQuestion("Who is the best character in Game of Thrones?");
        item.setAnswerOne("Sersei");
        item.setAnswerTwo("Tyrion");
        item.setAnswerThree("Khal Drogo");
        item.setAnswerFour("Jon Snow");
        item.setCorrectAnswer(2);
        list.add(item);
    }
}
