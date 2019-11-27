package com.techhousestudio.imagenotebook.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.techhousestudio.imagenotebook.R;
import com.techhousestudio.imagenotebook.database.NoteViewModel;
import com.techhousestudio.imagenotebook.models.Note;

import timber.log.Timber;

public class DetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvContent;
    ImageView ivImage;


    NoteViewModel noteViewModel;

    long note_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = findViewById(R.id.toolbar);
        ivImage = findViewById(R.id.ivImage);
        tvContent = findViewById(R.id.tvContent);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("About Post");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        note_id = b.getLong("note_id");

        noteViewModel.getNoteFindById(note_id).observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                Timber.i("Image Uri " + note.imageUri);
                Glide.with(DetailActivity.this).load(Uri.parse(note.imageUri)).into(ivImage);
                tvContent.setText(note.content);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.menu_edit:

                AddDialogFragment addDialogFragment = new AddDialogFragment();

                Bundle b = new Bundle();
                b.putLong("id", note_id);

                addDialogFragment.setArguments(b);

                addDialogFragment.show(getSupportFragmentManager(),"update_frag");


                break;
            case android.R.id.home:
                finishAfterTransition();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
