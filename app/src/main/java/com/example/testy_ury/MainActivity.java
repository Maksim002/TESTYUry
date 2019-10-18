package com.example.testy_ury;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

import static android.view.Gravity.*;

public class MainActivity extends Activity {
    private TextView countLabel;
    ProgressBar mProgressBar;
    private TextView questionLabel;
    private TextView quizTimer;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;
    private Button answerButton4;
    private CountDownTimer mCountDownTimer;
    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;

    static final private int QUIZ_COUNT = 10;
    static final long START_TIME_IN_MILIS = 60000;
    private long mTimeLeftinMillis = START_TIME_IN_MILIS;
    int PROGRESS_BAR_INCREMENT = 100 / QUIZ_COUNT;

    private String corect = "Correct";
    private String wrong = "Wrong";

    private Activity mActivity;

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftinMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftinMillis = millisUntilFinished;
                quizTimer.setText("Time: " + millisUntilFinished / 2000);
            }

            @Override
            public void onFinish() {
                quizTimer.setText("DONE!");
            }
        }.start();

    }

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();
    String quizData[][] = {
            //{"Question", "right answer", "choicea", "choiceb", "choicec", "choiced"}
            {"Форма полуэллипса сохраняется у всех видов ванн благодаря выигрышному внешнему виду и удобству.", "White", "Green", "Blue", "yellow"},
            {"Coal color?", "Black", "White", "Blue", "Orange"},
            {"Donald Trump's color?", "Orange", "Black", "Green", "Blue"},
            {"Course number?", "cs3300", "cs4300", "cs1400", "cs4770"},
            {"Assignment number?", "assign3", "assign2", "assign69", "assign5"},
            {"Canada's capital city?", "Ottawa", "Toronto", "St.Johns", "Montreal"},
            {"The sound dog makes?", "woof?", "meow", "be be", "moo"},
            {"Number of planets in solar system?", "9", "8", "7", "11"},
            {"Biggest planet in solar system?", "Jupiter", "Neptune", "Saturn", "Uranus"},
            {"Biggest planet in solar system?", "Jupiter", "Neptune", "Saturn", "Uranus"}
    };


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.question_text_view);
        quizTimer = findViewById(R.id.timer);
        mProgressBar = findViewById(R.id.progress_bar);
        answerButton1 = findViewById(R.id.answer1);
        answerButton2 = findViewById(R.id.answer2);
        answerButton3 = findViewById(R.id.answer3);
        answerButton4 = findViewById(R.id.answer4);
        mActivity = MainActivity.this;

        startTimer();

        for (int i = 0; i < quizData.length; i++) {

            ArrayList<String> tmpArray = new ArrayList<>();
            tmpArray.add(quizData[i][0]); //Country
            tmpArray.add(quizData[i][1]); //Right Answer
            tmpArray.add(quizData[i][2]); //Choice1
            tmpArray.add(quizData[i][3]); //Choice2
            tmpArray.add(quizData[i][4]); //Choice3

            quizArray.add(tmpArray);
        }
        showNextQuiz();
    }

    public void showNextQuiz() {

        countLabel.setText("Вопрос" + quizCount);
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        ArrayList<String> quiz = quizArray.get(randomNum);

        questionLabel.setText(quiz.get(0));
        rightAnswer = quiz.get(1);

        quiz.remove(0);
        Collections.shuffle(quiz);

        answerButton1.setText(quiz.get(0));
        answerButton2.setText(quiz.get(1));
        answerButton3.setText(quiz.get(2));
        answerButton4.setText(quiz.get(3));

        quizArray.remove(randomNum);
    }

    @SuppressLint({"NewApi", "ResourceType"})
    public String checkAnswer(View view) {

        Button answerBtn = (Button) findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        final String alertTitle;

        if (btnText.equals(rightAnswer)) {
            alertTitle = corect;
            rightAnswerCount++;
        } else {
            alertTitle = wrong;
        }

        ContextThemeWrapper cw = new ContextThemeWrapper(this, R.style.AlertDialogTheme);
        AlertDialog.Builder builder = new AlertDialog.Builder(cw);
        TextView dialogTitle = new TextView (getApplicationContext ());
        dialogTitle.setText (alertTitle);
        dialogTitle.setTextSize (TypedValue.COMPLEX_UNIT_DIP, 40);
        dialogTitle.setGravity(CENTER);
        dialogTitle.setTextColor(getResources().getColor(R.color.color1));
        builder.setCustomTitle (dialogTitle);
        builder.setTitle(alertTitle);

            if (alertTitle == corect) {
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                final View view2 = factory.inflate(R.layout.sample, null);
                builder.setView(view2);
            } else {
                LayoutInflater factory = LayoutInflater.from(MainActivity.this);
                final View view1 = factory.inflate(R.layout.smale_fols, null);
                builder.setView(view1);
            }
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (quizCount == QUIZ_COUNT) {
                        Intent intent = new Intent(getApplicationContext(), resultActivity.class);
                        mProgressBar.setProgress(0);
                        intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                        startActivity(intent);
                    } else {
                        quizCount++;
                        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
                        showNextQuiz();
                    }
                }
            });
            AlertDialog dialog1 = builder.create();
            dialog1 = builder.create();
            dialog1.show();
            dialog1.getWindow().setLayout(900,600);

            WindowManager.LayoutParams wmlp = dialog1.getWindow().getAttributes();

            if (alertTitle.equals(corect)){
                Objects.requireNonNull(dialog1.getWindow()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorTrue)));
            } else {
                Objects.requireNonNull(dialog1.getWindow()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorFalse)));
            }
            dialog1.show();
            return corect;
        }

    }


