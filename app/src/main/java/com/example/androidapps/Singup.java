package com.example.androidapps;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Singup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ***** Click Text view Change Text Color ***** //
        TextView signInText = findViewById(R.id.sign_up_text);
        signInText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change text color to give feedback
                signInText.setTextColor(getResources().getColor(R.color.purple));

                // Start the new Activity
                Intent intent = new Intent(Singup.this, Login.class);
                startActivity(intent);
            }
        });

        // ***** Password show and Hide code ***** //
        View eyeIcon = findViewById(R.id.eye_icon_id);
        EditText passwordEditText = findViewById(R.id.password);

// Initial state: password is hidden
        passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        eyeIcon.setBackgroundResource(R.drawable.eye_vector); // Set the initial background

        eyeIcon.setOnClickListener(new View.OnClickListener() {
            boolean isPasswordVisible = false;

            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Hide the password
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eyeIcon.setBackgroundResource(R.drawable.eye_vector); // Set to the closed eye icon
                } else {
                    // Show the password
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eyeIcon.setBackgroundResource(R.drawable.eye_vector); // Set to the open eye icon
                }
                // Toggle the flag
                isPasswordVisible = !isPasswordVisible;

                // Move the cursor to the end of the text
                passwordEditText.setSelection(passwordEditText.getText().length());
            }
        });
// ***** Password show and Hide code ***** //
        View eyeIcon1 = findViewById(R.id.eye_icon_id1);
        EditText passwordEditText1 = findViewById(R.id.password1);

// Initial state: password is hidden
        passwordEditText1.setTransformationMethod(PasswordTransformationMethod.getInstance());
        eyeIcon1.setBackgroundResource(R.drawable.eye_vector); // Set the initial background

        eyeIcon1.setOnClickListener(new View.OnClickListener() {
            boolean isPasswordVisible = false;

            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Hide the password
                    passwordEditText1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eyeIcon1.setBackgroundResource(R.drawable.eye_vector); // Set to the closed eye icon
                } else {
                    // Show the password
                    passwordEditText1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eyeIcon1.setBackgroundResource(R.drawable.eye_vector); // Set to the open eye icon
                }
                // Toggle the flag
                isPasswordVisible = !isPasswordVisible;

                // Move the cursor to the end of the text
                passwordEditText1.setSelection(passwordEditText1.getText().length());
            }
        });





    }
}