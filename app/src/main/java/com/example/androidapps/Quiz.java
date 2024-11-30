package com.example.androidapps;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Random;

/** @noinspection ALL*/
public class Quiz extends AppCompatActivity {
    private ProgressBar circularProgressBar;
    private TextView progressText,question_box, question_box_01,question_box_02,question_box_03,question_box_04,correctans_text,wrongans_text,questiontitle_text;
    private MediaPlayer mediaPlayer;
    private final int maxProgress = 30;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    private CountDownTimer countDownTimer;
    private  int ans_number = 0,index=0;
    public static int correctans=0,wrongans=0,totalquestion = 20,currentquestion=0;
    private  View back;
    private  boolean backbuttonenable = true;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);

        // Ensure proper padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
     correctans= Initialize();
     wrongans = Initialize();
    // totalquestion = Initialize();
     currentquestion = Initialize();
try{
    findId();
    stopMusic();
    back.setOnClickListener(this::onClick);
    startMusic(this, R.raw.countdown);
    startProgressBarAndCountdown();

    // Bring layout to front
    RelativeLayout relativeLayout = findViewById(R.id.relativeLayoutId);
    relativeLayout.bringToFront();

    QuizArray.QuestionAdd();
    QuestionSetUP();

    // Set onCheckedChangeListener for each RadioButton
    setRadioButtonListeners();
}catch (Exception e){
    Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
}

    }

    private int Initialize() {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    private  void QuestionSetUP(){
        try{
        Random random = new Random();
        int randomNumber = random.nextInt(99);
        ans_number=randomNumber;
        question_box.setText(QuizArray.Question[randomNumber][0]);
        question_box_01.setText(QuizArray.Question[randomNumber][1]);
        question_box_02.setText(QuizArray.Question[randomNumber][2]);
        question_box_03.setText(QuizArray.Question[randomNumber][3]);
        question_box_04.setText(QuizArray.Question[randomNumber][4]);
        correctans_text.setText(Integer.toString(correctans));
        wrongans_text.setText(Integer.toString(wrongans));
        questiontitle_text.setText("Question "+ (currentquestion+1)+"/"+ totalquestion);
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    private void setRadioButtonListeners() {
        try {
            radioButton1.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    handleRadioButtonSelection(radioButton1);
                }
            });

            radioButton2.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    handleRadioButtonSelection(radioButton2);
                }
            });

            radioButton3.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    handleRadioButtonSelection(radioButton3);
                }
            });

            radioButton4.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    handleRadioButtonSelection(radioButton4);
                }
            });
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    private void handleRadioButtonSelection(RadioButton selectedRadioButton) {
        try {
            uncheckOtherRadioButtons(selectedRadioButton);
            RelativeLayout selectedBox = getAnswerBox(selectedRadioButton);
            String selectedAnswer = ((TextView) findViewById(getOptionId(selectedRadioButton))).getText().toString();
            currentquestion++;
            if (selectedAnswer.equalsIgnoreCase(QuizArray.Question[ans_number][5])) {
                stopMusic();
                stopProgressBarAndCountdown();
                startMusic(this, R.raw.correct);
                selectedBox.setBackgroundResource(R.drawable.correct_ans_vector);
                correctans++;
                HomeArray.CorrectPoint+=4;
                ReviewAns(index++,selectedAnswer);
            } else {
                stopMusic();
                stopProgressBarAndCountdown();
                startMusic(this, R.raw.wrong);
                selectedBox.setBackgroundResource(R.drawable.wrong_ans_vector);
                wrongans++;
                HomeArray.WrongPoint-=1;
                ReviewAns(index++,selectedAnswer);
            }
            if (currentquestion == totalquestion) {
                stopProgressBarAndCountdown();
                backbuttonenable = false;
                new Handler().postDelayed(this::showCongratulationDialog, 1000);
            } else {
                stopProgressBarAndCountdown();
                backbuttonenable = false;
                new Handler().postDelayed(this::showCustomDialog, 1000);
            }
        }catch (Exception e){
            Toast.makeText(this, "146"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    private void ReviewAns(int index,String SelectAnsware) {
        try {
            index = index % totalquestion;

            QuizArray.ReviewQuiz[index][0] = QuizArray.Question[ans_number][0];
            QuizArray.ReviewQuiz[index][1] = QuizArray.Question[ans_number][1];
            QuizArray.ReviewQuiz[index][2] = QuizArray.Question[ans_number][2];
            QuizArray.ReviewQuiz[index][3] = QuizArray.Question[ans_number][3];
            QuizArray.ReviewQuiz[index][4] = QuizArray.Question[ans_number][4];
            QuizArray.ReviewQuiz[index][5] = QuizArray.Question[ans_number][5];
            QuizArray.ReviewQuiz[index++][6] = SelectAnsware;
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private int getOptionId(RadioButton radioButton) {
        try {
            if (radioButton.getId() == R.id.radio_button1) {
                return R.id.option01;
            } else if (radioButton.getId() == R.id.radio_button2) {
                return R.id.option02;
            } else if (radioButton.getId() == R.id.radio_button3) {
                return R.id.option03;
            } else if (radioButton.getId() == R.id.radio_button4) {
                return R.id.option04;
            } else {
                // Handle case where the radioButton ID doesn't match any known IDs
                throw new IllegalArgumentException("Invalid RadioButton ID: " + radioButton.getId());
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error in getOptionId: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            // Optionally log the error for further debugging
            Log.e("Quiz", "Error in getOptionId", e);
            // Return a default value or rethrow the exception if this is critical
            return -1;  // -1 indicates an invalid ID; handle this in the calling method
        }
    }
    private RelativeLayout getAnswerBox(RadioButton radioButton) {
        try {
            if (radioButton.getId() == R.id.radio_button1) {
                return findViewById(R.id.ans_box_01);
            } else if (radioButton.getId() == R.id.radio_button2) {
                return findViewById(R.id.ans_box_02);
            } else if (radioButton.getId() == R.id.radio_button3) {
                return findViewById(R.id.ans_box_03);
            } else if (radioButton.getId() == R.id.radio_button4) {
                return findViewById(R.id.ans_box_04);
            } else {
                // Handle the case where the radioButton ID does not match any known IDs
                throw new IllegalArgumentException("Invalid RadioButton ID: " + radioButton.getId());
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error in getAnswerBox: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("Quiz", "Error in getAnswerBox", e);
            return null;  // Returning null if there is an error
        }
    }

    private void uncheckOtherRadioButtons(RadioButton selectedRadioButton) {
       try {
           RadioButton[] radioButtons = {radioButton1, radioButton2, radioButton3, radioButton4};
           for (RadioButton radioButton : radioButtons) {
               if (radioButton != selectedRadioButton) {
                   radioButton.setChecked(false);
                   radioButton.setEnabled(false);
               }
           }
           selectedRadioButton.setEnabled(false);
       }catch (Exception e){
           Toast.makeText( this, "uncheekRadio"+ e.getMessage(), Toast.LENGTH_SHORT).show();
       }
    }
    private void resetRadioButtons() {
        try {
            RadioButton[] radioButtons = {radioButton1, radioButton2, radioButton3, radioButton4};
            for (RadioButton radioButton : radioButtons) {
                radioButton.setChecked(false); // Uncheck the radio button
                radioButton.setEnabled(true);  // Enable the radio button
                RelativeLayout selectedBox = getAnswerBox(radioButton);
                selectedBox.setBackgroundResource(R.drawable.answare_box_vector);
            }
        }catch (Exception e){
            Toast.makeText(this,"resetRadio "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }
    private void startCountdown() {
        try{
        mediaPlayer.start();
        beginCountdown();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    private void beginCountdown() {
        try {
        // Cancel any existing countdown
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // Start a new countdown from 30 seconds
        countDownTimer = new CountDownTimer(30000, 1000) {  // 30 seconds countdown with 1-second intervals
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) ((maxProgress * (30000 - millisUntilFinished)) / 30000);
                circularProgressBar.setProgress(progress);
                progressText.setText(String.valueOf(maxProgress - progress));
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                circularProgressBar.setProgress(maxProgress);
                progressText.setText("end");
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            }
        }.start();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    private void showCustomDialog() {
        try{
        stopMusic();
        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.ready_for_next_question, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(customView);

        Button notNowButton = customView.findViewById(R.id.notnow);
        Button okButton = customView.findViewById(R.id.ok_button);
        TextView timerText = customView.findViewById(R.id.timer_text);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();

        CountDownTimer dialogCountDownTimer = new CountDownTimer(10000, 1000) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                timerText.setText(String.format("%ds", secondsRemaining));
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                timerText.setText("0s");
                QuestionSetUP();
                resetRadioButtons();
                mediaPlayer = MediaPlayer.create(Quiz.this, R.raw.countdown);
                startCountdown();
                alertDialog.dismiss();
                backbuttonenable=true;
            }
        };

        dialogCountDownTimer.start();
        notNowButton.setOnClickListener(v -> {
            dialogCountDownTimer.cancel();
            alertDialog.dismiss();
            showCustomDialog();
        });
        okButton.setOnClickListener(v -> {
            dialogCountDownTimer.cancel();
            alertDialog.dismiss();
            QuestionSetUP();
            resetRadioButtons();
            stopMusic();
            startMusic(this, R.raw.countdown);
            startProgressBarAndCountdown();
            backbuttonenable=true;
        });
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    private void showCongratulationDialog(){
        try{
        stopMusic();
        startMusic(this,R.raw.betterwish);
        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.congratulation_1st_dialogbox, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(customView);

        // Corrected this line to find the button within the customView
        View okButton = customView.findViewById(R.id.close_btn);
        AlertDialog alertDialog = dialogBuilder.create();

        // Disable the ability to cancel the dialog by touching outside it or pressing the back button
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();

        okButton.setOnClickListener(v -> {
            alertDialog.dismiss();
            stopMusic();
            finish();
        });
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        try{
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    private void findId(){
        try{
        // Initialize RelativeLayout and RadioButton
        radioButton1 = findViewById(R.id.radio_button1);
        radioButton2 = findViewById(R.id.radio_button2);
        radioButton3 = findViewById(R.id.radio_button3);
        radioButton4 = findViewById(R.id.radio_button4);

        question_box = findViewById(R.id.question_box);

        question_box_01 = findViewById(R.id.option01);
        question_box_02 = findViewById(R.id.option02);
        question_box_03 = findViewById(R.id.option03);
        question_box_04 = findViewById(R.id.option04);

        correctans_text = findViewById(R.id.correctans);
        wrongans_text = findViewById(R.id.wrongans);
        questiontitle_text = findViewById(R.id.questiontitle_id);

        back = findViewById(R.id.backbutton_id);

        circularProgressBar = findViewById(R.id.circularProgressBar);
        progressText = findViewById(R.id.progressText);
        circularProgressBar.setMax(maxProgress);
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    public void startMusic(Context context, int resId) {
        try{
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, resId);
            mediaPlayer.start();
        } else if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    public void stopMusic() {
        try{
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();  // Release resources
            }
            mediaPlayer = null;  // Nullify to avoid NullPointerException in the future
        }
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    private void onClick(View v) {
        try{
            onBackPressed();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    public void startProgressBarAndCountdown() {
        try{
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) ((maxProgress * (30000 - millisUntilFinished)) / 30000);
                circularProgressBar.setProgress(progress);
                progressText.setText(String.valueOf(maxProgress - progress));
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                circularProgressBar.setProgress(maxProgress);
                progressText.setText("end");
            }
        }.start();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("SetTextI18n")
    public void stopProgressBarAndCountdown() {
        try{
        // Stop the countdown timer if it's running
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;  // Clear the reference to allow garbage collection
        }
        // Reset progress bar and text
        circularProgressBar.setProgress(0);
        progressText.setText("end");
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        try{
        if (backbuttonenable) {
            if(currentquestion<totalquestion){
                showDialogAreYousure();
            }
        }
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialogAreYousure() {
        try{
            LayoutInflater inflater = LayoutInflater.from(this);
            View customView = inflater.inflate(R.layout.areyousure, null);

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setView(customView);
            ImageButton notNowButton = customView.findViewById(R.id.not_btn);
            ImageButton okButton = customView.findViewById(R.id.ok_btn);
            TextView timerText = customView.findViewById(R.id.timer_text);

            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.show();
            notNowButton.setOnClickListener(v -> {
                alertDialog.dismiss();
            });
            okButton.setOnClickListener(v -> {
                alertDialog.dismiss();
                super.onBackPressed();
                stopMusic();
                finish();
            });
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}