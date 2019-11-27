package com.techhousestudio.imagenotebook.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.techhousestudio.imagenotebook.R;
import com.techhousestudio.imagenotebook.adapters.ImageNoteRecyclerAdapter;
import com.techhousestudio.imagenotebook.database.NoteViewModel;
import com.techhousestudio.imagenotebook.models.Note;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler_note_list;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler_note_list = findViewById(R.id.recycler_note_list);
        toolbar = findViewById(R.id.toolbar);

        fab = findViewById(R.id.fab);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialogFragment addDialogFragment = new AddDialogFragment();


                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                addDialogFragment.show(ft, "add_dialog");
            }
        });


        // recycler layout manager
        recycler_note_list.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        final ImageNoteRecyclerAdapter adapter = new ImageNoteRecyclerAdapter();
        recycler_note_list.setAdapter(adapter);


        noteViewModel.getNoteList().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
            }
        });

    }
}
