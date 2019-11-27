package com.techhousestudio.imagenotebook.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.techhousestudio.imagenotebook.ui.DetailActivity;
import com.techhousestudio.imagenotebook.R;
import com.techhousestudio.imagenotebook.models.Note;

import java.text.SimpleDateFormat;
import java.util.List;

public class ImageNoteRecyclerAdapter extends RecyclerView.Adapter<ImageNoteRecyclerAdapter.ImageNoteViewHolder> {

    private List<Note> notes;


    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageNoteRecyclerAdapter.ImageNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageNoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_image_note_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageNoteRecyclerAdapter.ImageNoteViewHolder holder, int position) {
        final Note note = notes.get(position);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" DD/MM/YYYY");
        holder.tvDateTime.setText("created at : " + simpleDateFormat.format(note.created_at));


        holder.tvContent.setText(note.content.trim());

        Glide.with(holder.itemView.getContext())
                .load(note.imageUri)
                .transform(new CenterCrop(), new RoundedCorners(10))
                .into(holder.ivImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), DetailActivity.class);
                Bundle b = new Bundle();
                b.putLong("note_id", note.id);

                i.putExtras(b);

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext(),
                        Pair.create((View) holder.tvContent, "note_content"),
                        Pair.create((View) holder.ivImage, "note_image"));


                v.getContext().startActivity(i, options.toBundle());


            }
        });


    }

    @Override
    public int getItemCount() {
        if (notes == null) {
            return -1;
        }
        return notes.size();
    }

    public class ImageNoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent, tvDateTime;
        ImageView ivImage;

        public ImageNoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
        }
    }
}
