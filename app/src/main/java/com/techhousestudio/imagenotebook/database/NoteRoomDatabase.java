package com.techhousestudio.imagenotebook.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.techhousestudio.imagenotebook.models.Note;

@Database(entities = {Note.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class NoteRoomDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();


    private volatile static NoteRoomDatabase instance;


    public static NoteRoomDatabase getInstance(Context context)
    {
        if (instance == null)
        {
            // To make thread safe
            synchronized (NoteRoomDatabase.class)
            {
                // check again as multiple threads
                // can reach above step
                if (instance ==null)
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteRoomDatabase.class, "note_db")
                            .allowMainThreadQueries()
                            .build();
            }
        }
        return instance;
    }

}
