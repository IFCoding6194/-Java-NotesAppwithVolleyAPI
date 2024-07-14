package com.example.notesappjava.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesappjava.R;

import java.util.List;

public class NotesAdapterActivity extends RecyclerView.Adapter<NotesAdapterActivity.MyViewHolder> {
    private Context context;
    private List<NotesBeanClass> notelist;
    private OnItemClickListener listener;

    public NotesAdapterActivity(Context context, List<NotesBeanClass> notelist) {
        this.context = context;
        this.notelist = notelist;
    }

    @NonNull
    @Override
    public NotesAdapterActivity.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_items,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapterActivity.MyViewHolder holder, int position) {
        NotesBeanClass notesBeanClass = notelist.get(position);
        holder.title.setText(notesBeanClass.getTitle());
        holder.des.setText(notesBeanClass.getDec());

        // Set Click Listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notelist.size();
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public  interface OnItemClickListener{
        void onItemClick(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title,des;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_tv);
            des = itemView.findViewById(R.id.description_tv);
        }
    }
}
