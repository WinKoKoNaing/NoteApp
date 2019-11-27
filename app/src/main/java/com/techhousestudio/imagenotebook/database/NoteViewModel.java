package com.techhousestudio.imagenotebook.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.techhousestudio.imagenotebook.models.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRoomDatabase database;

    private LiveData<List<Note>> noteList;


    public NoteViewModel(@NonNull Application application) {
        super(application);
        database = NoteRoomDatabase.getInstance(application);

    }



    // MainActivity
    public LiveData<List<Note>> getNoteList() {
        return database.noteDao().getAllNotes();
    }

    // AddDialogFragment
    public void insertNote(Note note){
        database.noteDao().insertNote(note);
    }

    public void updateNote(Note note){
        database.noteDao().updateNote(note);
    }
    // DetailActivity
    public LiveData<Note> getNoteFindById(long id){
        return database.noteDao().findNoteById(id);
    }


}
