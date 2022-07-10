package com.example.c196.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.c196.Database.Repository;
import com.example.c196.Entity.Assessments;
import com.example.c196.Entity.Courses;
import com.example.c196.Entity.Terms;
import com.example.c196.R;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Repository repo = new Repository(getApplication());
        Terms t1 = new Terms(1,"Term 1","06/01/2022","12/31/2022");
        Terms t2 = new Terms(2,"Term 2","01/01/2023", "06/31/2023");
        Terms t3 = new Terms(3,"Term 3", "07/01/2023","01/31/2024");
        repo.insert(t1);
        repo.insert(t2);
        repo.insert(t3);
        Courses c1 = new Courses(1,1,"C482","06/01/2022","06/30/2022","In-Progress","Software 1: Beginner and Intermediate Java Concepts",1,"Carolyn Sher-DeCusatis",
                "carolyn.sher@wgu.edu","385-428-7192");
        Courses c2 = new Courses(2,2,"C195","01/01/2023","01/31/2023","Planned To Take","Software 2: Advanced Java Concepts",1,"Carolyn Sher-DeCusatis",
                "carolyn.sher@wgu.edu","385-428-7192");
        Courses c3 = new Courses(3,1,"C188","07/01/2022","07/31/2022","Planned To Take","",1,"Carolyn Sher-DeCusatis",
                "carolyn.sher@wgu.edu","385-428-7192");
        repo.insert(c1);
        repo.insert(c2);
        repo.insert(c3);
        Assessments c1a1 = new Assessments(1, "Software 1(QKM2)", "PA","06/01/2022","06/30/2022","",1 );
        Assessments c2a1 =new  Assessments(2, "Software II - Advanced Java Concepts(QAM2)", "PA","07/01/2022","07/31/2022","Software 2", 2);
        repo.insert(c1a1);
        repo.insert(c2a1);


    }

    public void enterButton(View view) {
        Intent intent=new Intent(MainActivity.this, AllTerms.class);
        startActivity(intent);
    }
}