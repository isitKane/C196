package com.example.c196.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.c196.DAO.AssessmentsDAO;
import com.example.c196.DAO.CoursesDAO;
import com.example.c196.DAO.TermsDAO;
import com.example.c196.Entity.Assessments;
import com.example.c196.Entity.Courses;
import com.example.c196.Entity.Terms;

@Database(entities={Terms.class, Courses.class, Assessments.class}, version=1, exportSchema = false)
public abstract class StudentAppDatabaseBuilder extends RoomDatabase {
    public abstract TermsDAO termsDAO();
    public abstract CoursesDAO coursesDAO();
    public abstract AssessmentsDAO assessmentsDAO();

    private static volatile StudentAppDatabaseBuilder INSTANCE;

    static StudentAppDatabaseBuilder getDatabase(final Context context){
        synchronized (StudentAppDatabaseBuilder.class){
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), StudentAppDatabaseBuilder.class,
                        "StudentAppDatabase").fallbackToDestructiveMigration().build();
            }
        }
        return INSTANCE;
    }
}
