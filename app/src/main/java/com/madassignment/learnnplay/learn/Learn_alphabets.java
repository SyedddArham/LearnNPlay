package com.madassignment.learnnplay.learn;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.madassignment.learnnplay.Constants;
import com.madassignment.learnnplay.MainActivity;
import com.madassignment.learnnplay.R;

public class Learn_alphabets extends Activity {

	MediaPlayer[] media = new MediaPlayer[26];
	private static int click = 0;

	private final Bitmap[] mAlpha = new Bitmap[26];
	private final int[] mAlphaHwidth = new int[26];
	private final int[] mAlphaHheight = new int[26];
	private Bitmap mBG;
	private float x = 0;
	private float y = 0;
	private float vx = 1;
	private float vy = 1;
	private boolean mTouching;
	private Random rand;
	private ImageButton backButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		rand = new Random();
		media_create();

		mBG = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
		for (int i = 0; i < 26; i++) {
			mAlpha[i] = BitmapFactory.decodeResource(getResources(), Constants.alphabetphotos[i]);
			mAlphaHwidth[i] = mAlpha[i].getWidth() / 2;
			mAlphaHheight[i] = mAlpha[i].getHeight() / 2;
		}

		RelativeLayout rootLayout = new RelativeLayout(this);

		// Create the custom view
		View customView = new View(this) {

			@Override
			protected void onDraw(Canvas canvas) {
				float ScaleX = this.getWidth() / ((float) mBG.getWidth());
				float ScaleY = this.getHeight() / ((float) mBG.getHeight());

				canvas.save();
				canvas.scale(ScaleX, ScaleY);
				canvas.drawBitmap(mBG, 0, 0, null);

				canvas.restore();

				canvas.translate(x, y);

				if (mTouching) {
					vx = 1;
					vy = 1;
					x = 0;
					y = 0;
					media[click].stop();
					media[click].release();

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						Log.e("MainActivity", "EXCEPTION " + e);
					}

					click++;
					if (click <= 25) {
						media_create();

						canvas.drawBitmap(mAlpha[click], 0, 0, null);
						media[click].start();

					} else {
						click = 0;
						media_create();
					}
				} else {
					canvas.drawBitmap(mAlpha[click], 0, 0, null);
					media[click].start();
				}

				final int rWidth = rand.nextInt(3);
				final int rHeight = rand.nextInt(3);

				x = x + vx;
				y = y + vy;

				if ((y + 2 * mAlphaHheight[0] - 1 >= this.getHeight()) || (y <= -1)) {
					vy = -vy;
				} else {
					vy = vy + rHeight * 0.3f;
				}

				if ((x + 2 * mAlphaHwidth[0] + 1 >= this.getWidth()) || (x <= -1)) {
					vx = -vx;
				} else {
					vx = vx + rWidth * 0.4f;
				}

				postInvalidateDelayed(2);
			}

			@Override
			public boolean performClick() {
				// Call the super method to handle accessibility events
				return super.performClick();
			}
		};

		customView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				if (action == MotionEvent.ACTION_DOWN) {
					mTouching = true;
				}
				if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_MOVE) {
					mTouching = false;
				}

				// Indicate that the touch event has been handled
				v.performClick();
				return true;
			}
		});

		// Add the custom view to the root layout
		rootLayout.addView(customView);

		// Create the back button programmatically
		backButton = new ImageButton(this);
		backButton.setId(View.generateViewId());
		backButton.setLayoutParams(new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT));
		backButton.setBackground(null);
		backButton.setScaleType(ImageButton.ScaleType.FIT_XY);
		backButton.setImageResource(R.drawable.previous);

		// Set margins for the back button
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) backButton.getLayoutParams();
		params.setMargins(10, 10, 0, 0); // marginStart and marginTop

		// Set the click listener for the back button
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), Learn_Levels.class);
				startActivity(intent);
			}
		});

		// Add the back button to the root layout
		rootLayout.addView(backButton);

		// Set the root layout as the content view
		setContentView(rootLayout);
	}

	private void media_create() {
		media[click] = MediaPlayer.create(Learn_alphabets.this, Constants.alphabetsound[click]);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		media[click].pause();
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		media[click].start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		media[click].pause();
	}

	@Override
	protected void onStop() {
		super.onStop();
		media[click].stop();
	}
}
