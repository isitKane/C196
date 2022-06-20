package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonTerms(View view) {
        Intent intent=new Intent(MainActivity.this, TermsList.class);
        startActivity(intent);
    }

    public void buttonCourses(View view) {
        Intent intent=new Intent(MainActivity.this, CoursesList.class);
        startActivity(intent);
    }

    public void buttonAssessments(View view) {
        Intent intent=new Intent(MainActivity.this, AssessmentsList.class);
        startActivity(intent);
    }
}