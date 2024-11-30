package com.example.androidapps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Home extends AppCompatActivity {
    ImageButton playacting, reviewable,Generate_PDf;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        View activity_shift = findViewById(R.id.backbutton_id);
        activity_shift.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, Quiz.class);
            startActivity(intent);
        });

        try {
            FindId();
            PointSetup();
            reviewable.setOnClickListener(this::onClick);
            playacting.setOnClickListener(this::onClick);
            Generate_PDf.setOnClickListener(this::onClick);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void FindId() {
        try{
        reviewable = findViewById(R.id.reviewbutton_id);
        playacting = findViewById(R.id.playAgainButton);
        Generate_PDf = findViewById(R.id.generate_pdf_id);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void PointSetup() {
        try {
            TextView CurrentAns = findViewById(R.id.HomeCorectAns);
            CurrentAns.setText(String.valueOf(Quiz.correctans));

            TextView WrongAns = findViewById(R.id.HomeWrongAns);
            WrongAns.setText(String.valueOf(Quiz.wrongans));

            TextView CompletePercentage = findViewById(R.id.HomePercentage);
            int percentage = (Quiz.currentquestion * 100) / Quiz.totalquestion;
            CompletePercentage.setText(String.valueOf(percentage));
            TextView TotalQuestion = findViewById(R.id.HomeTotalQuestion);
            TotalQuestion.setText(String.valueOf(Quiz.totalquestion));
            TextView TotalPoint = findViewById(R.id.HomeTotalPoint);
            TotalPoint.setText(String.valueOf(HomeArray.CorrectPoint+HomeArray.WrongPoint));

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void onClick(View v) {
        try {
            if (v.equals(reviewable)) {
                Intent intent = new Intent(Home.this, ReviewAns.class);
                startActivity(intent);
            } else if (v.equals(playacting)) {
                Intent intent = new Intent(Home.this, Quiz.class);
                startActivity(intent);
                //finish();
            } else if (v.equals(Generate_PDf)) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    // For Android 9 and below
                    if (ContextCompat.checkSelfPermission(Home.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        createPdf();
                    }
                } else {
                    // For Android 10 and above, use scoped storage
                    createPdf();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void createPdf() {
        // Create a new document
        PdfDocument pdfDocument = new PdfDocument();

        // Create a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();

        // Start a page
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        // Get the canvas for drawing on the page
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(15f);
        canvas.drawText("Wellcome To Quiz Show ", (float) pageInfo.getPageWidth() / 2, 50, paint);
        canvas.drawText("Total Point: ", (float) pageInfo.getPageWidth() / 2, 100, paint);
        canvas.drawText(Integer.toString(HomeArray.CorrectPoint+HomeArray.WrongPoint), (float) pageInfo.getPageWidth() / 2-10, 120, paint);
        canvas.drawText("Correct: ", (float) pageInfo.getPageWidth() / 2, 150, paint);
        canvas.drawText(Integer.toString(Quiz.correctans), (float) pageInfo.getPageWidth() / 2, 170, paint);
        canvas.drawText("Wrong: ", (float) pageInfo.getPageWidth() / 2, 200, paint);
        canvas.drawText(Integer.toString(Quiz.wrongans), (float) pageInfo.getPageWidth() / 2, 220, paint);
        canvas.drawText("Total Question", (float) pageInfo.getPageWidth() / 2, 250, paint);
        canvas.drawText(Integer.toString(Quiz.totalquestion), (float) pageInfo.getPageWidth() / 2, 270, paint);


        // Finish the page
        pdfDocument.finishPage(page);

        // Write the document content
        String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/MyPDFs/";
        File file = new File(directoryPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        String filePath = directoryPath + "sample.pdf";
        File filePathObject = new File(filePath);

        try {
            pdfDocument.writeTo(new FileOutputStream(filePathObject));
            Toast.makeText(this, "PDF file created at: " + filePath, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // Close the document
        pdfDocument.close();
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createPdf();
            } else {
                Toast.makeText(this, "Permission denied to write to external storage", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
