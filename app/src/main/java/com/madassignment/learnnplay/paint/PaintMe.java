package com.madassignment.learnnplay.paint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;


public class PaintMe extends Activity {

	private Bitmap mBitmap;
	private Paint p;
	private Canvas c;
	private static String mAlphaName = "";
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		alertdialog();

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		final int width = size.x;
		final int height = size.y;

		mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		c = new Canvas(mBitmap);
		p = new Paint();

		mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {

			@Override
			public void onShake() {
				c.drawColor(0, Mode.CLEAR);
				p.reset();
				c.drawText(mAlphaName, width / 10, height / 1.3f, p);
			}
		});

		final View view = new View(this) {

			@Override
			protected void onDraw(Canvas canvas) {
				canvas.drawBitmap(mBitmap, 0, 0, null);

				setFocusable(true);
				setFocusableInTouchMode(true);

				p.setTextSize(60);
				p.setColor(0xff000000);
				canvas.drawText("Shake to Clear", width / 3f, 50, p);

				p.setStyle(Paint.Style.STROKE);
				p.setStrokeWidth(4);
				p.setColor(0xff000000);
				p.setTextSize(height / 1.09f);
				canvas.drawText(mAlphaName, width / 10f, height / 1.3f, p);

				postInvalidateDelayed(0);
			}

			@Override
			public boolean performClick() {
				// Call the super method to handle accessibility events
				return super.performClick();
			}
		};

		setContentView(view);

		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(final View v, MotionEvent event) {
				float Pointx = event.getX();
				float Pointy = event.getY();

				p.reset();
				p.setColor(0xff0000ff);
				c.drawCircle(Pointx, Pointy, 15, p);

				// Indicate that the touch event has been handled
				v.performClick();
				return true;
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(mShakeDetector);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	private void alertdialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Set the Alphabet/Number");
		builder.setMessage("Enter the Alphabet/Number to be displayed");
		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(input);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				mAlphaName = input.getText().toString();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
