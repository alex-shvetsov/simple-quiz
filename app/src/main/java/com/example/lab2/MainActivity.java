package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Question[] questions;
    private int page;

    Button next;
    Button prev;
    Button cheat;
    Button yes;
    Button no;

    TextView questionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("page", page);
        outState.putSerializable("q1", questions[0]);
        outState.putSerializable("q2", questions[1]);
        outState.putSerializable("q3", questions[2]);
        outState.putBoolean("prevEnabled", prev.isEnabled());
        outState.putBoolean("nextEnabled", next.isEnabled());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);

        questions = new Question[3];
        questions[0] = (Question)inState.getSerializable("q1");
        questions[1] = (Question)inState.getSerializable("q2");
        questions[2] = (Question)inState.getSerializable("q3");
        page = inState.getInt("page");
        prev.setEnabled(inState.getBoolean("prevEnabled"));
        next.setEnabled(inState.getBoolean("nextEnabled"));

        setPage(page);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            questions[page].setCheated(data.getBooleanExtra("cheated", false));
            if (questions[page].isCheated()) {
                cheat.setEnabled(false);
            }
        }
    }

    public void onNextClick(View view) {
        setPage(++page);
        switch (page) {
            case 1: prev.setEnabled(true); break;
            case 2: next.setEnabled(false); break;
            default: break;
        }
    }

    public void onPrevClick(View view) {
        setPage(--page);
        switch (page) {
            case 0: prev.setEnabled(false); break;
            case 1: next.setEnabled(true); break;
            default: break;
        }
    }

    public void onCheatClick(View view) {
        Intent cheatIntent = new Intent(this, CheatActivity.class);
        cheatIntent.putExtra("answer", questions[page].answer());
        startActivityForResult(cheatIntent, 1);
    }

    public void onYesClick(View view) {
        questions[page].setAnswered(true);
        setPage(page);

        if (questions[page].answer()) {
            if (questions[page].isCheated()) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.cheater), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.right), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong), Toast.LENGTH_SHORT).show();
        }
    }

    public void onNoClick(View view) {
        questions[page].setAnswered(true);
        setPage(page);

        if (!questions[page].answer()) {
            if (questions[page].isCheated()) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.cheater), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.right), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.wrong), Toast.LENGTH_SHORT).show();
        }
    }

    public void onRestartClick(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void init() {
        questions = new Question[3];
        questions[0] = new Question(getResources().getString(R.string.q1), true);
        questions[1] = new Question(getResources().getString(R.string.q2), false);
        questions[2] = new Question(getResources().getString(R.string.q3), true);
        page = 0;

        next = (Button)findViewById(R.id.nextButton);
        prev = (Button)findViewById(R.id.prevButton);
        cheat = (Button)findViewById(R.id.cheatButton);
        yes = (Button)findViewById(R.id.yesButton);
        no = (Button)findViewById(R.id.noButton);

        questionView = (TextView)findViewById(R.id.questionTextView);

        prev.setEnabled(false);
    }

    private void setPage(int page) {
        this.page = page;
        questionView.setText(questions[page].getText());

        boolean answered = questions[page].isAnswered();
        yes.setEnabled(!answered);
        no.setEnabled(!answered);
        cheat.setEnabled(!answered);

        if (questions[page].isCheated()) {
            cheat.setEnabled(false);
        }
    }

}
