package com.madassignment.learnnplay.learn;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.madassignment.learnnplay.Constants;
import com.madassignment.learnnplay.MainActivity;
import com.madassignment.learnnplay.R;

public class Learn_Shapes extends Activity {

	public static int click = 0;
	private MediaPlayer media;
	private ImageView shape;
	private TextView shapeName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn_shapes);
		ImageButton back = (ImageButton) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), Learn_Levels.class);
				startActivity(intent);
			}
		});
		shape = (ImageView) findViewById(R.id.shapes);
		ImageButton next = (ImageButton) findViewById(R.id.next);
		ImageButton previous = (ImageButton) findViewById(R.id.previous);
		shapeName = (TextView) findViewById(R.id.shapename);
		
		shapeSetUp();
		
		shapeName.setText(Constants.shapeNames[click]);
		
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				click++;
				
				if(click >= Constants.shapePhotos.length)
					click = 0;
				
				media.stop();
				shapeSetUp();
			}
		});
		
		previous.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				click--;
				
				if(click < 0)
					click = Constants.shapePhotos.length - 1;
				
				media.stop();
				shapeSetUp();
			}
		});
	}
	
	private void shapeSetUp()
	{
		shape.setBackgroundResource(Constants.shapePhotos[click]);
		
		media = MediaPlayer.create(Learn_Shapes.this, Constants.shapeSound[click]);
		media.start();
		shapeName.setText(Constants.shapeNames[click]);
	}
	
}
