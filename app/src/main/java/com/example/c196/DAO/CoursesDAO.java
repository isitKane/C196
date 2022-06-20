package com.example.c196.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.c196.Entity.Courses;

import java.util.List;

@Dao
public interface CoursesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Courses course);

    @Update
    void update(Courses course);

    @Delete
    void delete(Courses course);

    @Query("SELECT * FROM courses ORDER BY courseID ASC")
    List<Courses> getAllCourses();
}
