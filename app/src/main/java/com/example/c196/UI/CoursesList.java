package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.Adapters.AssessmentsAdapter;
import com.example.c196.Database.Repository;
import com.example.c196.Entity.Assessments;
import com.example.c196.Entity.Courses;
import com.example.c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CoursesList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AssessmentsAdapter assessmentsAdapter;
    Repository repo;
    int courseID;
    int courseTermID;
    int termID;
    String courseName;
    String courseStartDate;
    String courseEndDate;
    String courseStatus;
    String courseNotes;
    int instructorID;
    String instructorName;
    String instructorEmail;
    String instructorPhone;
    SimpleDateFormat sdf;

    private TextView courseTitle;
    private TextView courseStart;
    private TextView courseEnd;
    private TextView courseMentorName;
    private TextView courseMentorPhone;
    private TextView courseMentorEmail;
    private TextView courseStatusTV;
    private TextView courseNotesTV;

    Courses currentCourse;
    List<Courses> allCourses;
    List<Assessments> allAssessments;
    List<Assessments> assessmentsForCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String myDateFormat = "MM/dd/yyyy";
        sdf = new SimpleDateFormat(myDateFormat, Locale.US);

        repo = new Repository(getApplication());
        courseID = getIntent().getIntExtra("courseID", -1);
        courseTermID = getIntent().getIntExtra("courseTermID", -1);
        termID = getIntent().getIntExtra("termID", -1);

        allCourses = repo.getAllCourses();
        allAssessments = repo.getAllAssessments();
        assessmentsForCourse = new ArrayList<>();

        for (Assessments a : allAssessments) {
            if (a.getAssessmentCourseID() == courseID) {
                assessmentsForCourse.add(a);
            }
        }

        recyclerView = findViewById(R.id.assRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentsAdapter = new AssessmentsAdapter(this);
        recyclerView.setAdapter(assessmentsAdapter);
        assessmentsAdapter.setAssessments(assessmentsForCourse);

        courseTitle = findViewById(R.id.cdCourseNameTXT);
        courseStart = findViewById(R.id.cdStartTXT);
        courseEnd = findViewById(R.id.cdEndTXT);
        courseMentorName = findViewById(R.id.cdMentorNameTXT);
        courseMentorPhone = findViewById(R.id.cdMentorPhoneTXT);
        courseMentorEmail = findViewById(R.id.cdMentorEmailTXT);
        courseStatusTV = findViewById(R.id.cdStatusTXT);
        courseNotesTV = findViewById(R.id.cdCourseNotesTXT);

        for (Courses c : allCourses) {
            if (c.getCourseID() == courseID) {
                currentCourse = c;
            }
            if (currentCourse != null) {
                courseTitle.setText(currentCourse.getCourseName());
                courseStart.setText("Start Date: " + currentCourse.getCourseStartDate());
                courseEnd.setText("End Date: " + currentCourse.getCourseEndDate());
                courseMentorName.setText("Instructor Name: " + currentCourse.getInstructorName());
                courseMentorPhone.setText("Instructor Phone Number: " + currentCourse.getInstructorPhone());
                courseMentorEmail.setText("Instructor Email: " + currentCourse.getInstructorEmail());
                courseStatusTV.setText("Status: " + currentCourse.getCourseStatus());
                courseNotesTV.setText("Notes: " + currentCourse.getCourseNotes());
            } else {
                courseTitle.setText("No Course Title");
                courseStart.setText("No Start Date");
                courseEnd.setText("No End Date");
                courseMentorName.setText("No Instructor Name");
                courseMentorPhone.setText("No Instructor Phone Number");
                courseMentorEmail.setText("No Instructor Email");
                courseStatusTV.setText("No Course Status");
                courseNotesTV.setText("No Course Notes");
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_courses, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.cdShareNotes:
                Intent notesIntent = new Intent();
                notesIntent.setAction(Intent.ACTION_SEND);
                notesIntent.putExtra(Intent.EXTRA_TEXT, courseNotesTV.getText().toString());
                notesIntent.putExtra(Intent.EXTRA_TITLE, "Sharing Course Notes");
                notesIntent.setType("text/plain");
                Intent noteIntentChooser = Intent.createChooser(notesIntent, null);
                startActivity(noteIntentChooser);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addAssessments(View view) {
        Intent intent = new Intent(CoursesList.this, AddAssessments.class);
        intent.putExtra("courseID", courseID);
        startActivity(intent);
    }

    public void editCourses(View view) {
        Intent intent = new Intent(CoursesList.this, AddCourses.class);
        intent.putExtra("courseID", courseID);
        intent.putExtra("courseTermID", courseTermID);
        startActivity(intent);
    }

    public void deleteCourse(View view) {
        if (!(assessmentsForCourse.isEmpty())) {
            Toast.makeText(this, "Course has an assigned assessment. Cannot proceed with deletion.", Toast.LENGTH_LONG).show();
        } else {
            Courses c = new Courses(courseID, courseTermID, courseName, courseStartDate, courseEndDate, courseStatus, courseNotes, instructorID, instructorName, instructorEmail, instructorPhone);
            repo.delete(c);
            Toast.makeText(this, "Course has been successfully deleted.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, TermsList.class);
            intent.putExtra("termID", termID);
            startActivity(intent);

        }
    }

}
