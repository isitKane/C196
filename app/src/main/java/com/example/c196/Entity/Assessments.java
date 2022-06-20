package com.example.c196.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class Assessments {
    @PrimaryKey(autoGenerate = true)

    private int assessmentID;
    private String assessmentName;

    public Assessments(int assessmentID, String assessmentName) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentsID) {
        this.assessmentID = assessmentsID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentsName) {
        this.assessmentName = assessmentsName;
    }
}
