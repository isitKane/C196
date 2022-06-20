package com.example.c196.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class Terms {
    @PrimaryKey(autoGenerate = true)

    private int termID;
    private String termName;

    public Terms(int termID, String termName) {
        this.termID = termID;
        this.termName = termName;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }
}
