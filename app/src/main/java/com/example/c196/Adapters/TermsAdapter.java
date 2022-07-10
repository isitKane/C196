package com.example.c196.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.Entity.Terms;
import com.example.c196.R;
import com.example.c196.UI.TermsList;

import java.util.List;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.TermsViewHolder>{
    class TermsViewHolder extends RecyclerView.ViewHolder{
        private final TextView termNameItemView;
        private final TextView termStartItemView;
        private final TextView termEndItemView;
        private TermsViewHolder(View itemView){
            super(itemView);
            termNameItemView = itemView.findViewById(R.id.tcItemListNameTxt);
            termStartItemView = itemView.findViewById(R.id.tcItemListStarttTxt);
            termEndItemView = itemView.findViewById(R.id.tcItemListEndTxt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Terms current = mTerms.get(position);
                    Intent intent = new Intent(context, TermsList.class);
                    intent.putExtra("termID", current.getTermID());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Terms> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    public TermsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }
    @NonNull
    @Override
    public TermsAdapter.TermsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_and_course_list_item,parent,false);
        return new TermsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermsAdapter.TermsViewHolder holder, int position) {
        if(mTerms != null) {
            Terms current = mTerms.get(position);
            holder.termNameItemView.setText(current.getTermName());
            holder.termStartItemView.setText(current.getTermStart());
            holder.termEndItemView.setText(current.getTermEnd());
        } else {
            holder.termNameItemView.setText("No Term Name");
            holder.termStartItemView.setText("No Start Date");
            holder.termEndItemView.setText("No End Date");
        }
    }

    public void setTerms(List<Terms> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mTerms != null){
            return mTerms.size();
        }
        else return 0;
    }
}
