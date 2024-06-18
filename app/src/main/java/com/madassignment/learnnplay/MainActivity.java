package com.madassignment.learnnplay;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.madassignment.learnnplay.learn.Learn_Levels;
import com.madassignment.learnnplay.paint.PaintMe;
import com.madassignment.learnnplay.quiz.Quiz_Levels;

public class MainActivity extends FragmentActivity implements ChangePasswordFragment.OnPasswordChangeListener {

	private RelativeLayout userInfo;
	private ImageView userIcon;
	private TextView userName;
	private FirebaseFirestore db;
	private ListenerRegistration userListener;
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mAuth = FirebaseAuth.getInstance();
		FirebaseUser user = mAuth.getCurrentUser();

		db = FirebaseFirestore.getInstance();

		userInfo = findViewById(R.id.userInfo);
		userIcon = findViewById(R.id.userIcon);
		userName = findViewById(R.id.userName);
		ImageButton learn = findViewById(R.id.resume);
		ImageButton quiz = findViewById(R.id.Quiz_fruits);
		ImageButton paint = findViewById(R.id.option1);

		userListener = db.collection("users").document(user.getUid())
				.addSnapshotListener(new EventListener<DocumentSnapshot>() {
					@Override
					public void onEvent(@Nullable DocumentSnapshot documentSnapshot,@Nullable FirebaseFirestoreException e) {
						if (e != null) {
							// Handle the error
							return;
						}

						if (documentSnapshot != null && documentSnapshot.exists()) {
							String username = documentSnapshot.getString("name");
							if (username != null && !username.isEmpty()) {
								userName.setText(username);
							} else {
								userName.setText("User Name");
							}
						}
					}
				});

		userInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showUserMenu(v);
			}
		});

		learn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, Learn_Levels.class));
			}
		});

		quiz.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, Quiz_Levels.class));
			}
		});

		paint.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, PaintMe.class));
			}
		});
	}

	private void showUserMenu(View anchor) {
		View popupView = LayoutInflater.from(this).inflate(R.layout.popup_menu_layout, null);

		// Measure the width of userInfo
		userInfo.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		int popupWidth = userInfo.getMeasuredWidth();

		final PopupWindow popupWindow = new PopupWindow(popupView,
				popupWidth,
				LinearLayout.LayoutParams.WRAP_CONTENT,
				true);

		// Set the custom background drawable for the popup window
		popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

		TextView menuParent = popupView.findViewById(R.id.menu_parent);
		TextView menuChangePassword = popupView.findViewById(R.id.menu_change_password);
		TextView menuSignOut = popupView.findViewById(R.id.menu_sign_out);

		menuParent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, ParentPortalActivity.class));
				popupWindow.dismiss();
			}
		});

		menuChangePassword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showChangePasswordFragment();
				popupWindow.dismiss();
			}
		});

		menuSignOut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAuth.signOut();
				startActivity(new Intent(MainActivity.this, LoginActivity.class));
				finish();
				popupWindow.dismiss();
			}
		});

		// Show the popup window below the userInfo view
		popupWindow.showAsDropDown(userInfo, 0, 0);
	}

	private void showChangePasswordFragment() {
		ChangePasswordFragment fragment = new ChangePasswordFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.fragment_container, fragment)
				.addToBackStack(null)
				.commit();
	}

	@Override
	public void onPasswordChanged() {
		mAuth.signOut();
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		finish();
	}

}
