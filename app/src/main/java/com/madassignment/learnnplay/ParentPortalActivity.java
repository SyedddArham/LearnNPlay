package com.madassignment.learnnplay;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ParentPortalActivity extends Activity {

    private TextView userNameTextView,userEmailTextView,userAgeTextView,userGenderTextView;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TextView alphabetGameScoreView;
    private TextView shapeGameScoreView, numGameScoreView;
    private EditText feedbackCommentBox; // Add EditText for feedback
    private Button submitFeedbackButton;
//    private TextView userNameView;  // Assuming you want to display the user's name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_portal);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userNameTextView = findViewById(R.id.user_name);
        userEmailTextView = findViewById(R.id.user_email);
        userAgeTextView = findViewById(R.id.user_age);
        userGenderTextView = findViewById(R.id.user_gender);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Retrieve user document from Firestore
            db.collection("users").document(currentUser.getUid())
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                // Handle the error
                                return;
                            }

                            if (documentSnapshot != null && documentSnapshot.exists()) {
                                String username = documentSnapshot.getString("name");
                                String email = documentSnapshot.getString("email");
                                String age = documentSnapshot.getString("age");
                                String gender = documentSnapshot.getString("gender");

                                if (username != null && !username.isEmpty()) {
                                    userNameTextView.setText(username);
                                } else {
                                    userNameTextView.setText("User Name");
                                }

                                if (email != null && !email.isEmpty()) {
                                    userEmailTextView.setText(email);
                                } else {
                                    userEmailTextView.setText("User Email");
                                }

                                if (age != null && !age.isEmpty()) {
                                    userAgeTextView.setText(age);
                                } else {
                                    userAgeTextView.setText("User Age");
                                }

                                if (gender != null && !gender.isEmpty()) {
                                    userGenderTextView.setText(gender);
                                } else {
                                    userGenderTextView.setText("User Gender");
                                }
                            }
                        }
                    });
        }


        alphabetGameScoreView = findViewById(R.id.alphabet_game_score);
        shapeGameScoreView = findViewById(R.id.shape_game_score);
        numGameScoreView = findViewById(R.id.num_game_score);
        feedbackCommentBox = findViewById(R.id.feedback_comment_box); // Initialize EditText
        submitFeedbackButton = findViewById(R.id.submit_feedback_button);
//        userNameView = findViewById(R.id.user_name);  // Assuming there's a TextView for the user's name

        loadScores();
        submitFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });

    }

    private void loadScores() {
        SharedPreferences preferences = getSharedPreferences("quiz_prefs", MODE_PRIVATE);
        int alphabetScore = preferences.getInt("last_score", 0); // Load alphabet game score
        int shapeScore = preferences.getInt("Shape_score", 0); // Load shape game score
        int numberScore = preferences.getInt("last_score", 0); // Load number game score

        alphabetGameScoreView.setText("Alphabet Game: " + alphabetScore);
        shapeGameScoreView.setText("Shape Game: " + shapeScore);
        numGameScoreView.setText("Number Game: " + numberScore); // Assuming you have a TextView for number game score
    }
    private void submitFeedback() {
        // Retrieve feedback from EditText
        String feedback = feedbackCommentBox.getText().toString().trim();

        // Display a toast message indicating that feedback has been submitted
        Toast.makeText(this, "Feedback submitted: " + feedback, Toast.LENGTH_SHORT).show();
        feedbackCommentBox.setText("");
        submitFeedbackButton.setBackgroundColor(getResources().getColor(R.color.gray)); // Change the color temporarily

// Post a delayed action to revert the button's background color after a short delay (e.g., 200 milliseconds)
        submitFeedbackButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                submitFeedbackButton.setBackgroundColor(getResources().getColor(R.color.green)); // Revert the color
            }
        }, 200);

    }

}