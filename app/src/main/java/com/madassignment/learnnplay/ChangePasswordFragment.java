package com.madassignment.learnnplay;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordFragment extends Fragment {

    private EditText currentPassword, newPassword, confirmNewPassword;
    private Button updatePasswordButton, CloseButton;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private OnPasswordChangeListener passwordChangeListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPasswordChangeListener) {
            passwordChangeListener = (OnPasswordChangeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPasswordChangeListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);


        currentPassword = view.findViewById(R.id.current_password);
        newPassword = view.findViewById(R.id.new_password);
        confirmNewPassword = view.findViewById(R.id.confirm_new_password);
        updatePasswordButton = view.findViewById(R.id.update_password_button);
        CloseButton = view.findViewById(R.id.close);
        progressBar = view.findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        CloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPass = currentPassword.getText().toString();
                String newPass = newPassword.getText().toString();
                String confirmNewPass = confirmNewPassword.getText().toString();

                if (currentPass.isEmpty() || newPass.isEmpty() || confirmNewPass.isEmpty()) {
                    Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (newPass.equals(confirmNewPass)) {
                    // Check current password
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        reauthenticateAndChangePassword(user, currentPass, newPass);
                    }
                } else {
                    Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void reauthenticateAndChangePassword(FirebaseUser user, String currentPass, String newPass) {
        loading(true);
        String email = user.getEmail();
        if (email != null && !email.isEmpty()) {
            AuthCredential credential = EmailAuthProvider.getCredential(email, currentPass);

            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user.updatePassword(newPass).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(getActivity(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                            passwordChangeListener.onPasswordChanged();
                        } else {
                            loading(false);
                            Toast.makeText(getActivity(), "Password update failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    loading(false);
                    Toast.makeText(getActivity(), "Current password is incorrect", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            loading(false);
            Toast.makeText(getActivity(), "User email is not available", Toast.LENGTH_SHORT).show();
        }
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            updatePasswordButton.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            updatePasswordButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        passwordChangeListener = null;
    }

    public interface OnPasswordChangeListener {
        void onPasswordChanged();
    }
}
