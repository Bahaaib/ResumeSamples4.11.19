package com.resumesamples.resumesamples.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.resumesamples.resumesamples.MyApp;
import com.resumesamples.resumesamples.R;
import com.resumesamples.resumesamples.activity.MainActivity;
import com.resumesamples.resumesamples.activity.WebActivity;
import com.resumesamples.resumesamples.model.Title;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private OnClickListener listener;
    private List<Title> titles;

    public RecyclerAdapter(ArrayList<Title> titles, OnClickListener listener) {
        this.listener = listener;
        this.titles = titles;
    }



    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bind(titles.get(position));
        holder.questionName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(titles.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public interface OnClickListener {
        void onClick(Title title);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView questionName;

        public ViewHolder(View v) {
            super(v);
            questionName = (TextView) v.findViewById(R.id.recycler_item);
        }

        public void bind(Title title) {
            this.questionName.setText(title.getName());
        }
    }
}