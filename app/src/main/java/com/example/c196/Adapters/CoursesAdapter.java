package com.example.c196.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.Entity.Courses;
import com.example.c196.R;
import com.example.c196.UI.CoursesList;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder> {
    class CoursesViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseNameItemView;
        private final TextView courseStartItemView;
        private final TextView courseEndItemView;
        private CoursesViewHolder(View itemView){
            super(itemView);
            courseNameItemView = itemView.findViewById(R.id.tcItemListNameTxt);
            courseStartItemView = itemView.findViewById(R.id.tcItemListStarttTxt);
            courseEndItemView = itemView.findViewById(R.id.tcItemListEndTxt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Courses current = mCourses.get(position);
                    Intent intent = new Intent(context, CoursesList.class);
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("courseTermID", current.getCourseTermID());
                    intent.putExtra("termID", current.getCourseTermID());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Courses> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;

    public CoursesAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }
    @NonNull
    @Override
    public CoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_and_course_list_item,parent,false);
        return new CoursesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesViewHolder holder, int position) {
        if(mCourses != null) {
            Courses current = mCourses.get(position);
            holder.courseNameItemView.setText(current.getCourseName());
            holder.courseStartItemView.setText(current.getCourseStartDate());
            holder.courseEndItemView.setText(current.getCourseEndDate());
        } else {
            holder.courseNameItemView.setText("No Course Name");
            holder.courseStartItemView.setText("No Start Date");
            holder.courseEndItemView.setText("No End Date");
        }
    }

    public void setCourses(List<Courses> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mCourses != null) {
            return mCourses.size();
        }
        else return 0;
    }
}

