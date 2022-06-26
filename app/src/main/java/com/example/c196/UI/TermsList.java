package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.c196.Adapters.TermsAdapter;
import com.example.c196.Database.Repository;
import com.example.c196.Entity.Terms;
import com.example.c196.R;

import java.util.List;

public class TermsList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.termRecyclerView);
        Repository repo = new Repository(getApplication());
        List<Terms> allTerms = repo.getAllTerms();
        final TermsAdapter termsAdapter = new TermsAdapter(this);
        recyclerView.setAdapter(termsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termsAdapter.setTerms(allTerms);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_terms, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addTerms(View view) {
        Intent intent=new Intent(TermsList.this, AddTerms.class);
        startActivity(intent);
    }

    public void addCourse(View view) {
        Intent intent=new Intent(TermsList.this,AddCourses.class);
        startActivity(intent);
    }

    public void editTerms(View view) {
        Intent intent=new Intent(TermsList.this, AddTerms.class);
        startActivity(intent);
    }



    public void viewCourse(View view) {
        Intent intent=new Intent(TermsList.this,CoursesList.class);
        startActivity(intent);
    }
}