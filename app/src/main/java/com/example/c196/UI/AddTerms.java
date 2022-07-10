package com.example.c196.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.c196.Adapters.CoursesAdapter;
import com.example.c196.Database.Repository;
import com.example.c196.Entity.Courses;
import com.example.c196.Entity.Terms;
import com.example.c196.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddTerms extends AppCompatActivity {
    Repository repo;
    int termID;
    int newTermID;
    String termName;
    String termStart;
    String termEnd;
    Terms selectedTerm;
    List<Terms> allTerms;

    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDatePicker;
    DatePickerDialog.OnDateSetListener endDatePicker;

    EditText termNameTF;
    EditText termStartTF;
    EditText termEndTF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_terms);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        repo = new Repository(getApplication());
        allTerms = repo.getAllTerms();
        termID = getIntent().getIntExtra("termID", -1);
        for (Terms t : allTerms) {
            if (t.getTermID() == termID) {
                selectedTerm = t;
            }
        }

        termNameTF = findViewById(R.id.termNameTF);
        termStartTF = findViewById(R.id.termStartTF);
        termEndTF = findViewById(R.id.termEndTF);

        if (selectedTerm != null) {
            termNameTF.setText(selectedTerm.getTermName());
            termStartTF.setText(selectedTerm.getTermStart());
            termEndTF.setText(selectedTerm.getTermEnd());
        }

        startDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, month);
                calendarStart.set(Calendar.DAY_OF_MONTH, day);
                updateLabelStart();
            }
        };
        termStartTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTerms.this, startDatePicker, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH)
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
        termEndTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddTerms.this, endDatePicker, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH)
                        , calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabelStart() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        termStartTF.setText(sdf.format(calendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        termEndTF.setText(sdf.format(calendarEnd.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_terms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void submitTermButton(View view) {
        termName = termNameTF.getText().toString();
        termStart = termStartTF.getText().toString();
        termEnd = termEndTF.getText().toString();

        if (termName.trim().isEmpty() || termStart.trim().isEmpty() || termEnd.trim().isEmpty()) {
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

        if (termID != -1) {
            Terms update = new Terms(termID, termName, termStart, termEnd);
            repo.update(update);

        } else {
            newTermID = allTerms.size();
            for (Terms term : allTerms) {
                if (term.getTermID() >= newTermID) {
                    newTermID = term.getTermID();
                }
            }

            Terms newTerm = new Terms(newTermID + 1, termName, termStart, termEnd);
            repo.insert(newTerm);
        }

        Intent intent = new Intent(this, AllTerms.class);
        startActivity(intent);
    }
}