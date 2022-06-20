package com.example.c196.Database;

import android.app.Application;

import com.example.c196.DAO.AssessmentsDAO;
import com.example.c196.DAO.CoursesDAO;
import com.example.c196.DAO.TermsDAO;
import com.example.c196.Entity.Assessments;
import com.example.c196.Entity.Courses;
import com.example.c196.Entity.Terms;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private TermsDAO mTermsDAO;
    private CoursesDAO mCoursesDAO;
    private AssessmentsDAO mAssessmentsDAO;

    private List<Terms> mAllTerms;
    private List<Courses> mAllCourses;
    private List<Assessments> mAllAssessments;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        StudentAppDatabaseBuilder db = StudentAppDatabaseBuilder.getDatabase(application);
        mTermsDAO = db.termsDAO();
        mCoursesDAO = db.coursesDAO();
        mAssessmentsDAO = db.assessmentsDAO();
    }

    //terms
    public List<Terms> getAllTerms(){
        databaseExecutor.execute(()-> {
            mAllTerms = mTermsDAO.getAllTerms();
        });

        try{
        Thread.sleep(1000);
        }
        catch(InterruptedException e){
        e.printStackTrace();
        }
        return mAllTerms;
    }

    public void insert(Terms term) {
        databaseExecutor.execute(()-> {
            mTermsDAO.insert(term);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Terms term){
        databaseExecutor.execute(()->{
            mTermsDAO.update(term);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Terms term){
        databaseExecutor.execute(()->{
            mTermsDAO.delete(term);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    //courses

    public List<Courses> getAllCourses(){
        databaseExecutor.execute(()-> {
            mAllCourses = mCoursesDAO.getAllCourses();
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAllCourses;
    }

    public void insert(Courses course) {
        databaseExecutor.execute(()-> {
            mCoursesDAO.insert(course);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Courses course) {
        databaseExecutor.execute(()-> {
            mCoursesDAO.update(course);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    public void delete(Courses course) {
        databaseExecutor.execute(()-> {
            mCoursesDAO.delete(course);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    //assessments
    public List<Assessments> getAllAssessments(){
        databaseExecutor.execute(()-> {
            mAllAssessments = mAssessmentsDAO.getAllAssessments();
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    public void insert(Assessments assessment) {
        databaseExecutor.execute(()-> {
            mAssessmentsDAO.insert(assessment);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Assessments assessment) {
        databaseExecutor.execute(()-> {
            mAssessmentsDAO.update(assessment);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Assessments assessment) {
        databaseExecutor.execute(()-> {
            mAssessmentsDAO.delete(assessment);
        });

        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
