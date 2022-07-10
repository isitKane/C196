package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c196.Adapters.CoursesAdapter;
import com.example.c196.Database.Repository;
import com.example.c196.Entity.Courses;
import com.example.c196.Entity.Terms;
import com.example.c196.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class TermsList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CoursesAdapter coursesAdapter;
    Repository repo;

    private TextView termTitleTXT;
    private TextView termStartTXT;
    private TextView termEndTXT;

    int termID;
    String termName;
    String termStart;
    String termEnd;
    Terms currentTerm;
    List<Courses> allCourses;
    ArrayList<Courses> coursesForTerm;
    List<Terms> allTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        repo = new Repository(getApplication());
        termID = getIntent().getIntExtra("termID", -1);

        allTerms = repo.getAllTerms();
        allCourses = repo.getAllCourses();
        coursesForTerm = new ArrayList<>();
        for (Courses c : allCourses) {
            if (c.getCourseTermID() == termID) {
                coursesForTerm.add(c);
            }
        }

        recyclerView = findViewById(R.id.coursesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        coursesAdapter = new CoursesAdapter(this);
        recyclerView.setAdapter(coursesAdapter);
        coursesAdapter.setCourses(coursesForTerm);

        termTitleTXT = findViewById(R.id.termNameTXT);
        termStartTXT = findViewById(R.id.termStartTXT);
        termEndTXT = findViewById(R.id.termEndTXT);

        for (Terms t : allTerms) {
            if (t.getTermID() == termID) {
                currentTerm = t;
            }
            if (currentTerm != null) {
                termTitleTXT.setText(currentTerm.getTermName());
                termStartTXT.setText("Start Date: " + currentTerm.getTermStart());
                termEndTXT.setText("End Date: " + currentTerm.getTermEnd());
            } else {
                termTitleTXT.setText("No Term Name");
                termStartTXT.setText("No Start Date");
                termEndTXT.setText("No End Date");
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_terms, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addCourses(View view) {
        Intent intent = new Intent(TermsList.this, AddCourses.class);
        intent.putExtra("termID", termID);
        startActivity(intent);
    }

    public void editTerm(View view) {
        Intent intent = new Intent(TermsList.this, AddTerms.class);
        intent.putExtra("termID", termID);
        startActivity(intent);
    }

    public void deleteTerm(View view) {
        if (!(coursesForTerm.isEmpty())) {
            Toast.makeText(this, "Term has an assigned course. Cannot proceed with deletion.", Toast.LENGTH_LONG).show();
        } else {
            Terms t = new Terms(termID, termName, termStart, termEnd);
            repo.delete(t);
            Toast.makeText(this, "Term has been successfully deleted.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(TermsList.this, AllTerms.class);
            startActivity(intent);
        }
    }
}