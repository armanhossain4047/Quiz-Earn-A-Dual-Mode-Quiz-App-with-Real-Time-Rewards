package com.example.androidapps;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


/** @noinspection ALL*/

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button signup = findViewById(R.id.sing_in_btn_id);
        signup.setOnClickListener(this::onClick);

        // ***** Click Text View Change Text Color ***** //
        TextView signInText = findViewById(R.id.sign_in_text);
        if (signInText != null) {  // Check for null
            signInText.setOnClickListener(v -> {
                // Change text color to give feedback
                signInText.setTextColor(getResources().getColor(R.color.purple));
                // Start the new Activity
                Intent intent = new Intent(Login.this, Quiz.class);
                startActivity(intent);
            });
        }

        // ***** Password Show and Hide Code ***** //
        EditText passwordEditText = findViewById(R.id.password);
        ImageButton eyeIcon = findViewById(R.id.eye_icon_id);

        if (passwordEditText != null && eyeIcon != null) {  // Check for null
            // Set initial state to hide password
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());

            // Set click listener on eye icon
            eyeIcon.setOnClickListener(new View.OnClickListener() {
                boolean isPasswordVisible = false;

                @Override
                public void onClick(View v) {
                    if (isPasswordVisible) {
                        // Hide the password
                        passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        eyeIcon.setImageResource(R.drawable.eye_vector); // Set to the closed eye icon
                    } else {
                        // Show the password
                        passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        eyeIcon.setImageResource(R.drawable.eye_vector); // Set to the open eye icon (you need this drawable)
                    }
                    // Toggle the flag
                    isPasswordVisible = !isPasswordVisible;

                    // Move the cursor to the end of the text
                    passwordEditText.setSelection(passwordEditText.getText().length());
                }
            });
        }
    }
    private void onClick(View v) {
        Intent intent = new Intent(Login.this, Home.class);
        startActivity(intent);
    }
}