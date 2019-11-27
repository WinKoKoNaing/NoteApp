package com.techhousestudio.imagenotebook.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.techhousestudio.imagenotebook.models.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("Select * from note")
    LiveData<List<Note>> getAllNotes();

    @Query("Select * from note where id =:id")
    LiveData<Note> findNoteById(long id);

    @Insert
    void insertNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);
}
