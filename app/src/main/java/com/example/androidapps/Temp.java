package com.example.androidapps;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Temp extends AppCompatActivity {
    private ProgressBar circularProgressBar;
    private TextView progressText;
    private int maxProgress = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_temp);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        circularProgressBar = findViewById(R.id.circularProgressBar);
        progressText = findViewById(R.id.progressText);

        // Set the maximum value for the ProgressBar
        circularProgressBar.setMax(maxProgress);

        // Start countdown from 30 seconds (30,000 ms)
        new CountDownTimer(30000, 300) {

            public void onTick(long millisUntilFinished) {
                // Calculate progress
                int progress = (int) (maxProgress * millisUntilFinished / 30000);

                // Update progress bar
                circularProgressBar.setProgress(progress);

                // Update text view to show remaining time
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                progressText.setText(secondsRemaining + "s");
            }

            public void onFinish() {
                // Set progress to 0 when finished
                circularProgressBar.setProgress(0);
                progressText.setText("End");
            }

        }.start();




    }
}