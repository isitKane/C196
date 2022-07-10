package com.example.c196.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.c196.Database.Repository;
import com.example.c196.Entity.Courses;
import com.example.c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddCourses extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Repository repo;
    int courseID;
    int courseTermID;
    int termID;
    int newCourseID;
    String courseName;
    String courseStartDate;
    String courseEndDate;
    String courseStatus;
    String courseNotes;
    int instructorID;
    String instructorName;
    String instructorEmail;
    String instructorPhone;
    Courses selectedCourse;
    List<Courses> allCourses;
    SimpleDateFormat sdf;

    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDatePicker;
    DatePickerDialog.OnDateSetListener endDatePicker;

    EditText courseNameTF;
    EditText courseStartTF;
    EditText courseEndTF;
    Spinner courseStatusSpinner;
    EditText courseMentorNameTF;
    EditText courseMentorPhoneTF;
    EditText courseMentorEmailTF;
    EditText courseNotesTF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String myDateFormat = "MM/dd/yyyy";
        sdf = new SimpleDateFormat(myDateFormat, Locale.US);

        repo = new Repository(getApplication());
        allCourses = repo.getAllCourses();
        courseID = getIntent().getIntExtra("courseID", -1);
        courseTermID = getIntent().getIntExtra("courseTermID", -1);
        termID = getIntent().getIntExtra("termID", -1);
        for (Courses c : allCourses) {
            if (c.getCourseID() == courseID) {
                selectedCourse = c;
            }
        }

        courseNameTF = findViewById(R.id.addCourseNameTF);
        courseStartTF = findViewById(R.id.addCourseStartTF);
        courseEndTF = findViewById(R.id.addCourseEndTF);
        courseStatusSpinner = findViewById(R.id.addCourseStatusSpinner);
        courseMentorNameTF = findViewById(R.id.addCourseMentorNameTF);
        courseMentorPhoneTF = findViewById(R.id.addCourseMentorPhoneTF);
        courseMentorEmailTF = findViewById(R.id.addCourseMentorEmailTF);
        courseNotesTF = findViewById(R.id.addCourseNotesTF);

        if(selectedCourse != null) {
            courseNameTF.setText(selectedCourse.getCourseName());
            courseStartTF.setText(selectedCourse.getCourseStartDate());
            courseEndTF.setText(selectedCourse.getCourseEndDate());
            //courseStatusSpinner.setText(selectedCourse.getCourseStatus());
            courseMentorNameTF.setText(selectedCourse.getInstructorName());
            courseMentorPhoneTF.setText(selectedCourse.getInstructorPhone());
            courseMentorEmailTF.setText(selectedCourse.getInstructorEmail());
            courseNotesTF.setText(selectedCourse.getCourseNotes());
        }

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,R.array.course_status, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(statusAdapter);
        courseStatusSpinner.setOnItemSelectedListener(this);

        startDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, month);
                calendarStart.set(Calendar.DAY_OF_MONTH, day);
                updateLabelStart();
            }
        };
        courseStartTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddCourses.this, startDatePicker, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH)
                        , calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendarEnd.set(Calendar.YEAR, year);
                calendarEnd.set(Calendar.MONTH, month);
                calendarEnd.set(Calendar.DAY_OF_MONTH, day);
                updateLabelEnd();

            }
        };
        courseEndTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddCourses.this, endDatePicker, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH)
                        , calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabelStart() {
        String format = "MM/dd/yyyy";
        sdf = new SimpleDateFormat(format, Locale.US);
        courseStartTF.setText(sdf.format(calendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String format = "MM/dd/yyyy";
        sdf = new SimpleDateFormat(format, Locale.US);
        courseEndTF.setText(sdf.format(calendarEnd.getTime()));
    }

    public void submitCourseButton(View view) {
        courseName = courseNameTF.getText().toString();
        courseStartDate = courseStartTF.getText().toString();
        courseEndDate = courseEndTF.getText().toString();
        courseStatus = courseStatusSpinner.getSelectedItem().toString();
        courseNotes  = courseNotesTF.getText().toString();
        instructorName = courseMentorNameTF.getText().toString();
        instructorEmail = courseMentorEmailTF.getText().toString();
        instructorPhone = courseMentorPhoneTF.getText().toString();

        if(courseName.trim().isEmpty()  || courseStartDate.trim().isEmpty() ||  courseEndDate.trim().isEmpty() ||
                courseStatus.trim().isEmpty() || instructorName.trim().isEmpty() || instructorEmail.trim().isEmpty() || instructorPhone.trim().isEmpty()) {
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setTitle("Alert");
            ad.setMessage("One or more fields has been left blank. Please fill before submitting.");
            ad.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }
            );
            ad.show();
            return;
        }

        if (courseTermID != -1) {
            Courses u = new Courses(courseID, courseTermID, courseName, courseStartDate, courseEndDate, courseStatus, courseNotes, instructorID, instructorName, instructorEmail, instructorPhone);
            repo.update(u);
        } else {
            newCourseID = allCourses.size();
            for(Courses course : allCourses) {
                if(course.getCourseID() >= newCourseID) {
                    newCourseID = course.getCourseID();
                }
            }
            Courses nc = new Courses(newCourseID + 1, courseTermID, courseName, courseStartDate, courseEndDate, courseStatus, courseNotes, instructorID, instructorName, instructorEmail, instructorPhone);
            repo.insert(nc);
        }
        Intent intent = new Intent(this, TermsList.class);
        intent.putExtra("termID", termID);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addcourses, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.cdShareNotes:
                Intent notesIntent = new Intent();
                notesIntent.setAction(Intent.ACTION_SEND);
                notesIntent.putExtra(Intent.EXTRA_TEXT, courseNotesTF.getText().toString());
                notesIntent.putExtra(Intent.EXTRA_TITLE, "Sharing Course Notes");
                notesIntent.setType("text/plain");
                Intent noteIntentChooser = Intent.createChooser(notesIntent, null);
                startActivity(noteIntentChooser);
                return true;
            case R.id.cdStart:
                String courseStartDate = courseStartTF.getText().toString();
                Date start = null;

                try{
                    start = sdf.parse(courseStartDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long startTrigger = start.getTime();
                Intent startIntent = new Intent(AddCourses.this, MyReceiver.class);
                startIntent.putExtra("key", selectedCourse.getCourseName() + " course ends today.");
                Toast.makeText(AddCourses.this, "Start notification current course set", Toast.LENGTH_SHORT).show();
                PendingIntent startSend = PendingIntent.getBroadcast(AddCourses.this, MainActivity.numAlert++, startIntent, 0  );
                AlarmManager startAM = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAM.set(AlarmManager.RTC_WAKEUP,startTrigger,startSend);
                return true;
            case R.id.cdEnd:
                String courseEndDate = courseEndTF.getText().toString();
                Date end = null;

                try{
                    end = sdf.parse(courseEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long endTrigger = end.getTime();
                Intent endIntent = new Intent(AddCourses.this, MyReceiver.class);
                endIntent.putExtra("key", selectedCourse.getCourseName() + " course ends today.");
                Toast.makeText(AddCourses.this, "End notification for current course set", Toast.LENGTH_SHORT).show();
                PendingIntent endSend = PendingIntent.getBroadcast(AddCourses.this, MainActivity.numAlert++, endIntent, 0  );
                AlarmManager endAM = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAM.set(AlarmManager.RTC_WAKEUP,endTrigger,endSend);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String choice = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(), choice, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
