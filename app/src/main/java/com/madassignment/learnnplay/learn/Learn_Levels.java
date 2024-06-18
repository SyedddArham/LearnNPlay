package com.madassignment.learnnplay.learn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.madassignment.learnnplay.MainActivity;
import com.madassignment.learnnplay.R;


public class Learn_Levels extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn);

		ImageButton back = (ImageButton) findViewById(R.id.back);
		ImageButton alphabets = (ImageButton) findViewById(R.id.Quiz_alphabets);
		ImageButton numbers = (ImageButton) findViewById(R.id.Quiz_fruits);
		ImageButton shapes = (ImageButton) findViewById(R.id.Learn_Shapes);

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			}
		});
		alphabets.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), Learn_alphabets.class);
				startActivity(intent);
			}
		});
		
		numbers.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), Learn_numbers.class);
				startActivity(intent);
			}
		});
		
		shapes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), Learn_Shapes.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
}
