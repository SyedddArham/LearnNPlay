package com.madassignment.learnnplay.quiz;

import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.madassignment.learnnplay.Constants;
import com.madassignment.learnnplay.R;
import com.madassignment.learnnplay.learn.Learn_Levels;

public class Quiz_shapes extends Activity {
	private ImageView shape;
	private EditText shapeName;
	private Random rand;
	private MediaPlayer media;
	private int score = 0;
	private int totalQuestions = 5;
	private int currentQuestion = 0;
	private int randomNumber = 0;
	private TextView scoreView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_shapes);

		shape = findViewById(R.id.shape_image);
		ImageButton next = findViewById(R.id.next_shape);
		ImageButton back = findViewById(R.id.back);
		shapeName = findViewById(R.id.editText1);
		rand = new Random();
		scoreView = findViewById(R.id.scoreView);

		shapeSetUp();
		updateScore();

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), Quiz_Levels.class);
				startActivity(intent);
			}
		});

		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = shapeName.getText().toString().trim().toUpperCase(Locale.US);
				if (name.equals(Constants.shapeNames[randomNumber].toUpperCase(Locale.US))) {
					media = MediaPlayer.create(Quiz_shapes.this, Constants.shapeSound[randomNumber]);
					media.start();
					score++; // Increment score for correct answer
					shapeName.setText(null);
				} else {
					media = MediaPlayer.create(Quiz_shapes.this, R.raw.wrong);
					media.start();
					shapeName.setText(null);
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					Log.e("Shape Quiz", e.getMessage());
				}
				currentQuestion++;
				if (currentQuestion < totalQuestions) {
					shapeSetUp();
					updateScore();
				} else {
					endQuiz();
				}
			}
		});
	}

	private void shapeSetUp() {
		randomNumber = rand.nextInt(Constants.shapePhotos.length);
		shape.setBackgroundResource(Constants.shapePhotos[randomNumber]);
	}

	private void endQuiz() {
		saveScore();
		showScoreDialog();
	}

	private void updateScore() {
		scoreView.setText("Score: " + score);
	}

	private void saveScore() {
		SharedPreferences preferences = getSharedPreferences("quiz_prefs", MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("Shape_score", score);
		editor.apply();
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

}
