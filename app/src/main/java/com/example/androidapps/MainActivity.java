package com.example.androidapps;

//import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

// Java Code save for sefty //
/* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });*/

public class MainActivity extends AppCompatActivity {
    private View activity_shift;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        activity_shift = findViewById(R.id.backbutton_id);
        activity_shift.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
        });
    }
}
