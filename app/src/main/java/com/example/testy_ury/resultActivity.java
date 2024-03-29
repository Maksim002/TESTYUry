package com.example.testy_ury;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class resultActivity extends AppCompatActivity {

    @SuppressLint({"ApplySharedPref", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultLabel = (TextView) findViewById(R.id.resultLabel);
        TextView totalScoreLabel = (TextView) findViewById(R.id.totalScoreLabel);

        int score = getIntent().getIntExtra("RIGHT_ANSWER_COUNT", 0);
        SharedPreferences settings = getSharedPreferences("quizzler", Context.MODE_PRIVATE);
        int totalScore = settings.getInt("totalScore", 0);
        totalScore +=score;

        resultLabel.setText(score +" / 10");
        totalScoreLabel.setText("Total Score: "+ totalScore);

        //Update total score
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("totalScore", totalScore);
        editor.commit();
    }
}
