package com.example.c196.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.Entity.Assessments;
import com.example.c196.Entity.Courses;
import com.example.c196.Entity.Terms;
import com.example.c196.R;
import com.example.c196.UI.AssessmentsList;

import java.util.List;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsAdapter.AssessmentsViewHolder> {
    class AssessmentsViewHolder extends RecyclerView.ViewHolder{
        private final TextView assNameItemView;
        private final TextView assTypeItemView;
        private final TextView assStartItemView;
        private final TextView assEndItemView;
        private AssessmentsViewHolder(View itemView) {
            super(itemView);
            assNameItemView = itemView.findViewById(R.id.tcItemListNameTxt);
            assTypeItemView = itemView.findViewById(R.id.tcItemListStarttTxt);
            assStartItemView = itemView.findViewById(R.id.assItemListStartDateTxt);
            assEndItemView = itemView.findViewById(R.id.assItemListEndDateTxt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessments current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentsList.class);
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("assCourseID",current.getAssessmentCourseID());
                    intent.putExtra("courseID",current.getAssessmentCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Assessments> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentsAdapter.AssessmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item,parent,false);
        return new AssessmentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentsViewHolder holder, int position) {
        if(mAssessments != null) {
            Assessments current = mAssessments.get(position);
            holder.assNameItemView.setText(current.getAssessmentName());
            holder.assTypeItemView.setText(current.getAssessmentType());
            holder.assStartItemView.setText(current.getAssessmentStartDate());
            holder.assEndItemView.setText(current.getAssessmentEndDate());
        } else {
            holder.assNameItemView.setText("No Assessment Name");
            holder.assTypeItemView.setText("No Assessment Type");
            holder.assStartItemView.setText("No Assessment Start Date");
            holder.assEndItemView.setText("No Assessment End Date");
        }
    }

    public void setAssessments(List<Assessments> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(mAssessments != null){
            return mAssessments.size();
        }
        else return 0;
    }
}
