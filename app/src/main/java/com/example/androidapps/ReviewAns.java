package com.example.androidapps;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReviewAns extends AppCompatActivity {
int index =0;
TextView Question_Title, Option1, Option2, Option3, Option4;
RelativeLayout box1,box2,box3,box4;
View nextbtn,prebtn;
int Total_Review_Question =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_ans);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FindId();
        QuestionSetUP();
        QuizSetup(0);
        nextbtn.setOnClickListener(this::onClick);
        prebtn.setOnClickListener(this::onClick);
    }
    @SuppressLint("SetTextI18n")
    private void QuizSetup(int Index) {
        if (0 <= Index && Index <= Total_Review_Question) {
            Question_Title.setText(QuizArray.ReviewQuiz[Index][0]);
            Option1.setText(QuizArray.ReviewQuiz[Index][1]);
            Option2.setText(QuizArray.ReviewQuiz[Index][2]);
            Option3.setText(QuizArray.ReviewQuiz[Index][3]);
            Option4.setText(QuizArray.ReviewQuiz[Index][4]);
            // Loop through options to identify correct and wrong answers
            for (int x = 1; x <= 4 && QuizArray.ReviewQuiz[Index][0] != null; x++) {
                if (QuizArray.ReviewQuiz[Index][x].equalsIgnoreCase(QuizArray.ReviewQuiz[Index][6])) {
                    SetWrongAns(x);
                }
                if (QuizArray.ReviewQuiz[Index][x].equalsIgnoreCase(QuizArray.ReviewQuiz[Index][5])) {
                    SetCorrectAns(x);
                }
            }
            Index++;
            // Increment the index for the next question
            TextView question_position = findViewById(R.id.questiontitle_id);
            question_position.setText("Question " + Index + "/" + QuizArray.ReviewQuiz.length);
        }
    }
    private void SetWrongAns(int x){
        if(x==1){
            box1.setBackgroundResource(R.drawable.wrong_ans_vector);
        } else if (x==2) {
            box2.setBackgroundResource(R.drawable.wrong_ans_vector);
        } else if (x==3) {
            box3.setBackgroundResource(R.drawable.wrong_ans_vector);
        } else if (x==4) {
            box4.setBackgroundResource(R.drawable.wrong_ans_vector);
        }
}
    private void SetCorrectAns(int x){
        if(x==1){
            box1.setBackgroundResource(R.drawable.correct_ans_vector);
        } else if (x==2) {
            box2.setBackgroundResource(R.drawable.correct_ans_vector);
        } else if (x==3) {
            box3.setBackgroundResource(R.drawable.correct_ans_vector);
        } else if (x==4) {
            box4.setBackgroundResource(R.drawable.correct_ans_vector);
        }
    }
    @SuppressLint("SetTextI18n")
    private void QuestionSetUP() {
        int correctQuestion = 0, wrongQuestion = 0, totalQuestion = 0, currentQuestion = 0;

        for (int i = 0; i < QuizArray.ReviewQuiz.length && QuizArray.ReviewQuiz[i][0] != null; i++) {
            totalQuestion++;
            if (QuizArray.ReviewQuiz[i][5] != null && QuizArray.ReviewQuiz[i][6] != null &&
                    QuizArray.ReviewQuiz[i][5].equalsIgnoreCase(QuizArray.ReviewQuiz[i][6])) {
                correctQuestion++;
            } else {
                wrongQuestion++;
            }
        }
        TextView correct_question = findViewById(R.id.correctans);
        correct_question.setText(Integer.toString(correctQuestion));

        TextView wrong_question = findViewById(R.id.wrongans);
        wrong_question.setText(Integer.toString(wrongQuestion));

        TextView question_position = findViewById(R.id.questiontitle_id);
        question_position.setText("Question " + currentQuestion + "/" + totalQuestion);
        Total_Review_Question = totalQuestion;
    }
    private  void  FindId(){
        Question_Title = findViewById(R.id.question_box);
        Option1 = findViewById(R.id.option01);
        Option2 = findViewById(R.id.option02);
        Option3 = findViewById(R.id.option03);
        Option4 = findViewById(R.id.option04);

        box1 = findViewById(R.id.ans_box_01);
        box2 = findViewById(R.id.ans_box_02);
        box3 = findViewById(R.id.ans_box_03);
        box4 = findViewById(R.id.ans_box_04);
        nextbtn = findViewById(R.id.next_btn);
        prebtn = findViewById(R.id.previous_btn);
    }
    private  void  ResetAnsButton(){
        box1.setBackgroundResource(R.drawable.answare_box_vector);
        box2.setBackgroundResource(R.drawable.answare_box_vector);
        box3.setBackgroundResource(R.drawable.answare_box_vector);
        box4.setBackgroundResource(R.drawable.answare_box_vector);
    }
    private void onClick(View v) {
        if (QuizArray.ReviewQuiz[0][0] != null) {
            if (v.equals(prebtn)) {
                prebtn.setBackgroundResource(R.drawable.privious_clickable_vector);
                new Handler().postDelayed(() -> prebtn.setBackgroundResource(R.drawable.previous_vector), 200);
                index--;
                if (index < 0) {
                    index = Total_Review_Question - 1;
                }
                ResetAnsButton();
                QuizSetup(index);
            }
            if (v.equals(nextbtn)) {
                nextbtn.setBackgroundResource(R.drawable.next_clickable_vector);
                new Handler().postDelayed(() -> nextbtn.setBackgroundResource(R.drawable.next_vector), 200);
                index++;
                index = index % Total_Review_Question;
                ResetAnsButton();
                QuizSetup(index);
            }
        }
    }
}
