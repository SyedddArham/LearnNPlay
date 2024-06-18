package com.madassignment.learnnplay.quiz;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.madassignment.learnnplay.Constants;
import com.madassignment.learnnplay.MainActivity;
import com.madassignment.learnnplay.R;

public class Quiz_alphabets extends Activity {
    private ImageButton resume;
    private ImageButton op1;
    private ImageButton op2;
    private ImageButton op3;
    private ImageButton op4;
    private TextView scoreView;

    private int position;
    private int[] randomnumber = new int[4];
    private Random rand;
    private MediaPlayer media;
    private int score = 0;
    private int totalQuestions = 5;
    private int currentQuestion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_alphabets);

        resume = findViewById(R.id.resume);
        op1 = findViewById(R.id.option1);
        op2 = findViewById(R.id.option2);
        op3 = findViewById(R.id.option3);
        op4 = findViewById(R.id.option4);
        scoreView = findViewById(R.id.scoreView);
        rand = new Random();

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Quiz_Levels.class);
                startActivity(intent);
            }
        });

        shuffle();

        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                checkAnswer(0);
            }
        });

        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                checkAnswer(1);
            }
        });

        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                checkAnswer(2);
            }
        });

        op4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                checkAnswer(3);
            }
        });
    }

    private void checkAnswer(int selectedPosition) {
        if (position == selectedPosition) {
            clapmedia();
            score++;
        } else {
            wrong();
        }
        updateScore();
        currentQuestion++;
        if (currentQuestion < totalQuestions) {
            shuffle();
        } else {
            endQuiz();
        }
    }

    private void shuffle() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.e("Quiz", "Exception " + e);
        }

        randomnumber[0] = rand.nextInt(Constants.alphabetphotos.length);
        randomnumber[1] = rand.nextInt(Constants.alphabetphotos.length);
        while (randomnumber[1] == randomnumber[0])
            randomnumber[1] = rand.nextInt(Constants.alphabetphotos.length);

        randomnumber[2] = rand.nextInt(Constants.alphabetphotos.length);
        while ((randomnumber[2] == randomnumber[0]) || (randomnumber[2] == randomnumber[1]))
            randomnumber[2] = rand.nextInt(Constants.alphabetphotos.length);

        randomnumber[3] = rand.nextInt(Constants.alphabetphotos.length);
        while ((randomnumber[3] == randomnumber[0]) || (randomnumber[3] == randomnumber[1]) || (randomnumber[3] == randomnumber[2]))
            randomnumber[3] = rand.nextInt(Constants.alphabetphotos.length);

        position = rand.nextInt(4);

        media = MediaPlayer.create(Quiz_alphabets.this, Constants.alphabetsound[randomnumber[position]]);
        media.start();
        media.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });

        resume.setBackgroundResource(android.R.drawable.ic_media_play);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(android.R.drawable.ic_media_pause);
                media = MediaPlayer.create(Quiz_alphabets.this, Constants.alphabetsound[randomnumber[position]]);
                media.start();
                media.setOnCompletionListener(new OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        resume.setBackgroundResource(android.R.drawable.ic_media_play);
                        mp.release();
                    }
                });
            }
        });

        op1.setBackgroundResource(Constants.alphabetphotos[randomnumber[0]]);
        op2.setBackgroundResource(Constants.alphabetphotos[randomnumber[1]]);
        op3.setBackgroundResource(Constants.alphabetphotos[randomnumber[2]]);
        op4.setBackgroundResource(Constants.alphabetphotos[randomnumber[3]]);
    }

    private void updateScore() {
        scoreView.setText("Score: " + score);
    }

    private void endQuiz() {
        saveScore();
        showScoreDialog();
    }

    private void showScoreDialog() {
        // Inflate the custom layout
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom, null);

        // Create the AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        // Set the title and message dynamically
        TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
        TextView dialogMessage = dialogView.findViewById(R.id.dialog_message);
        Button dialogButton = dialogView.findViewById(R.id.dialog_button);

        dialogTitle.setText("Quiz Over");
        dialogMessage.setText("Your score is " + score);

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false); // Prevent dialog from closing when touched outside
        dialog.show();

        // Set the dialog width and height
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(params);
        // Set the button click listener to dismiss the dialog and finish the activity
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish(); // Close the activity after the dialog is dismissed
            }
        });
    }

    private void saveScore() {
        SharedPreferences preferences = getSharedPreferences("quiz_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("last_score", score);
        editor.apply();
    }

    private void resetQuiz() {
        score = 0;
        currentQuestion = 0;
        updateScore();
        shuffle();
    }

    private void clapmedia() {
        media = MediaPlayer.create(Quiz_alphabets.this, R.raw.clap);
        media.start();
        media.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    private void wrong() {
        media = MediaPlayer.create(Quiz_alphabets.this, R.raw.wrong);
        media.start();
        media.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }
}
