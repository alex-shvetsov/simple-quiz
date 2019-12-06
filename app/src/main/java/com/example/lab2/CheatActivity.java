package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CheatActivity extends AppCompatActivity {

    private boolean cheated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("cheated", cheated);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        cheated = inState.getBoolean("cheated");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("cheated", cheated);
        super.onSaveInstanceState(outState);
    }

    public void onSureCheatClick(View view) {
        cheated = true;
        if (getIntent().getBooleanExtra("answer", false)) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.answerYes), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.answerNo), Toast.LENGTH_SHORT).show();
        }
    }
}
