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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.c196.Database.Repository;
import com.example.c196.Entity.Assessments;
import com.example.c196.Entity.Courses;
import com.example.c196.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddAssessments extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Repository repo;
    int assessmentID;
    int assCourseID;
    int courseID;
    int newAssID;
    String assessmentName;
    String assessmentType;
    String assessmentStartDate;
    String assessmentEndDate;
    String assessmentDesc;
    Assessments selectedAssessment;
    List<Assessments> allAssessments;

    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDatePicker;
    DatePickerDialog.OnDateSetListener endDatePicker;
    SimpleDateFormat sdf;

    Spinner assTypeSpinner;
    EditText assNameTF;
    EditText assStartTF;
    EditText assEndTF;
    EditText assDescTF;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String myDateFormat = "MM/dd/yyyy";
        sdf = new SimpleDateFormat(myDateFormat, Locale.US);

        repo = new Repository(getApplication());
        allAssessments = repo.getAllAssessments();
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        assCourseID = getIntent().getIntExtra("assCourseID", -1);
        courseID = getIntent().getIntExtra("courseID", -1);
        for (Assessments a : allAssessments) {
            if (a.getAssessmentCourseID() == assCourseID) {
                selectedAssessment = a;
            }
        }

        assNameTF = findViewById(R.id.addAssNameTF);
        assStartTF = findViewById(R.id.addAssStartTF);
        assTypeSpinner = findViewById(R.id.assTypeSpinner);
        assEndTF = findViewById(R.id.addAssEndTF);
        assDescTF = findViewById(R.id.addAssDescTF);

        if (selectedAssessment != null) {
            assNameTF.setText(selectedAssessment.getAssessmentName());
            assStartTF.setText(selectedAssessment.getAssessmentStartDate());
            assEndTF.setText(selectedAssessment.getAssessmentEndDate());
            assDescTF.setText(selectedAssessment.getAssessmentDesc());
        }

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,R.array.assType, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assTypeSpinner.setAdapter(typeAdapter);
        assTypeSpinner.setOnItemSelectedListener(this);

        startDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, month);
                calendarStart.set(Calendar.DAY_OF_MONTH, day);
                updateLabelStart();
            }
        };
        assStartTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddAssessments.this, startDatePicker, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH)
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
        assEndTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddAssessments.this, endDatePicker, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH)
                        , calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabelStart() {
        String format = "MM/dd/yyyy";
        sdf = new SimpleDateFormat(format, Locale.US);
        assStartTF.setText(sdf.format(calendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String format = "MM/dd/yyyy";
        sdf = new SimpleDateFormat(format, Locale.US);
        assEndTF.setText(sdf.format(calendarEnd.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.cdStart:
                String assStartDate = assStartTF.getText().toString();
                Date start = null;

                try{
                    start = sdf.parse(assStartDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long startTrigger = start.getTime();
                Intent startIntent = new Intent(AddAssessments.this, MyReceiver.class);
                startIntent.putExtra("key", selectedAssessment.getAssessmentName() + " assessment ends today.");
                Toast.makeText(AddAssessments.this, "Start notification current assessment set", Toast.LENGTH_SHORT).show();
                PendingIntent startSend = PendingIntent.getBroadcast(AddAssessments.this, MainActivity.numAlert++, startIntent, 0  );
                AlarmManager startAM = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAM.set(AlarmManager.RTC_WAKEUP,startTrigger,startSend);
                return true;
            case R.id.cdEnd:
                String assEndDate = assEndTF.getText().toString();
                Date end = null;

                try{
                    end = sdf.parse(assEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long endTrigger = end.getTime();
                Intent endIntent = new Intent(AddAssessments.this, MyReceiver.class);
                endIntent.putExtra("key", selectedAssessment.getAssessmentName() + " assessment ends today.");
                Toast.makeText(AddAssessments.this, "End notification for current assessment set", Toast.LENGTH_SHORT).show();
                PendingIntent endSend = PendingIntent.getBroadcast(AddAssessments.this, MainActivity.numAlert++, endIntent, 0  );
                AlarmManager endAM = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAM.set(AlarmManager.RTC_WAKEUP,endTrigger,endSend);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void submitAssessmentButton(View view) {
        assessmentName = assNameTF.getText().toString();
        assessmentType = assTypeSpinner.getSelectedItem().toString();
        assessmentStartDate = assStartTF.getText().toString();
        assessmentEndDate = assEndTF.getText().toString();
        assessmentDesc = assDescTF.getText().toString();

        if(assessmentName.trim().isEmpty()  || assessmentType.trim().isEmpty() ||  assessmentStartDate.trim().isEmpty() ||
                assessmentEndDate.trim().isEmpty() || assessmentDesc.trim().isEmpty()) {
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

        if(assCourseID != -1) {
            Assessments u = new Assessments(assessmentID, assessmentName, assessmentType, assessmentStartDate, assessmentEndDate, assessmentDesc, assCourseID);
            repo.update(u);
        } else {
            newAssID = allAssessments.size();
            for(Assessments assessment : allAssessments) {
                if(assessment.getAssessmentID() >= newAssID) {
                    newAssID = assessment.getAssessmentID();
                }
            }
            Assessments na = new Assessments(assessmentID, assessmentName, assessmentType, assessmentStartDate, assessmentEndDate, assessmentDesc, assCourseID);
            repo.insert(na);
        }
        Intent intent = new Intent(this, CoursesList.class);
        intent.putExtra("courseID", courseID);
        startActivity(intent);
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