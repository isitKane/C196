package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.Database.Repository;
import com.example.c196.Entity.Assessments;
import com.example.c196.Entity.Terms;
import com.example.c196.R;

import java.util.List;

public class AssessmentsList extends AppCompatActivity {
    Repository repo;
    int assessmentID;
    int assCourseID;
    int courseID;
    String assessmentName;
    String assessmentType;
    String assessmentStartDate;
    String assessmentEndDate;
    String assessmentDesc;

    TextView assName;
    TextView assType;
    TextView assStart;
    TextView assEnd;
    TextView assDesc;

    Assessments currentAssessment;
    List<Assessments> allAssessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        repo = new Repository(getApplication());
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        assCourseID = getIntent().getIntExtra("assCourseID", -1);
        courseID = getIntent().getIntExtra("courseID", -1);
        allAssessments = repo.getAllAssessments();

        assName = findViewById(R.id.adAssTitleTxt);
        assType = findViewById(R.id.adAssTypeTxt);
        assStart = findViewById(R.id.adAssStartDateTxt);
        assEnd = findViewById(R.id.adAssEndDateTxt);
        assDesc = findViewById(R.id.adAssDescText);

        for (Assessments a : allAssessments) {
            if (a.getAssessmentID() == assessmentID) {
                currentAssessment = a;
            }
        }
        if (currentAssessment != null) {
            assName.setText(currentAssessment.getAssessmentName());
            assType.setText("Type " + currentAssessment.getAssessmentType());
            assStart.setText("Start Date " + currentAssessment.getAssessmentStartDate());
            assEnd.setText("End Date " + currentAssessment.getAssessmentEndDate());
            assDesc.setText("Description " + currentAssessment.getAssessmentDesc());
        } else {
            assName.setText("No Assessment Title");
            assType.setText("No Assessment Type");
            assStart.setText("No Assessment Start Date");
            assEnd.setText("No Assessment End Date");
            assDesc.setText("No Assessment Description");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_terms, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void editAssessments(View view) {
        Intent intent = new Intent(AssessmentsList.this, AddAssessments.class);
        intent.putExtra("assessmentID", assessmentID);
        intent.putExtra("assCourseID", assCourseID);
        startActivity(intent);
    }

    public void deleteAssessments(View view) {
        Assessments a = new Assessments(assessmentID, assessmentName, assessmentType, assessmentStartDate, assessmentEndDate, assessmentDesc, assCourseID);
        repo.delete(a);
        Toast.makeText(this, "Assessment has been successfully deleted.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, CoursesList.class);
        intent.putExtra("courseID", courseID);
        startActivity(intent);

    }
}



