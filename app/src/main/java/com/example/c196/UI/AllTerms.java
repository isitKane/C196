package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.c196.Adapters.TermsAdapter;
import com.example.c196.Database.Repository;
import com.example.c196.Entity.Terms;
import com.example.c196.R;

import java.util.List;

public class AllTerms extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TermsAdapter termsAdapter;
    Repository repo;
    List<Terms> allTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_terms);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        repo = new Repository(getApplication());
        allTerms = repo.getAllTerms();

        recyclerView = findViewById(R.id.allTermsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termsAdapter = new TermsAdapter(this);
        recyclerView.setAdapter(termsAdapter);
        termsAdapter.setTerms(allTerms);
    }


    public void addTerms(View view) {
        Intent intent = new Intent(AllTerms.this, AddTerms.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_terms, menu);
        return true;
    }

}