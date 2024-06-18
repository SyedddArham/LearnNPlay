package com.madassignment.learnnplay.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.madassignment.learnnplay.MainActivity;
import com.madassignment.learnnplay.R;

public class Quiz_Levels extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		ImageButton back = (ImageButton) findViewById(R.id.back);
		ImageButton alphabets = (ImageButton) findViewById(R.id.Quiz_alphabets);
		ImageButton shapes = (ImageButton) findViewById(R.id.Learn_Shapes);
		ImageButton num = (ImageButton) findViewById(R.id.Quiz_num);

		alphabets.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), Quiz_alphabets.class);
				startActivity(intent);
			}
		});
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		});

		shapes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), Quiz_shapes.class);
				startActivity(intent);
			}
		});
		num.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), QuizNum.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
