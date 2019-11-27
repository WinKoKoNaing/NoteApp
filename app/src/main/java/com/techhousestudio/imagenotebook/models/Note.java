package com.techhousestudio.imagenotebook.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "note")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @NonNull
    @ColumnInfo(name = "image_uri")
    public String imageUri;
    @NonNull
    public String content;
    public Date created_at;
    public Date updated_at;

    @Ignore
    public Note(@NonNull String imageUri, @NonNull String content) {
        this.imageUri = imageUri;
        this.content = content;
        this.updated_at = new Date();
        this.created_at = new Date();
    }
    // Update


    @Ignore
    public Note(long id, @NonNull String imageUri, @NonNull String content, Date created_at) {
        this.id = id;
        this.imageUri = imageUri;
        this.content = content;
        this.created_at = created_at;
        this.updated_at = new Date();
    }

    public Note(long id, @NonNull String imageUri, @NonNull String content, Date created_at, Date updated_at) {
        this.id = id;
        this.imageUri = imageUri;
        this.content = content;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
