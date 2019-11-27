package com.techhousestudio.imagenotebook.ui;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.techhousestudio.imagenotebook.R;
import com.techhousestudio.imagenotebook.database.NoteViewModel;
import com.techhousestudio.imagenotebook.helpers.LinedEditText;
import com.techhousestudio.imagenotebook.models.Note;

import java.util.Date;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddDialogFragment extends DialogFragment {
    private ImageView ivPickUpImage;
    private LinedEditText etInput;
    private Toolbar toolbar;

    private NoteViewModel noteViewModel;
    private String image_uri;
    long note_id;
    boolean isUpdate = false;
    Note currentNote;
    public AddDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        toolbar = view.findViewById(R.id.toolbar);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);





        etInput = view.findViewById(R.id.etInput);
        ivPickUpImage = view.findViewById(R.id.ivPickUpImage);


        noteViewModel = ViewModelProviders.of(getActivity()).get(NoteViewModel.class);



        if (getArguments() != null) {
            ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar().setTitle("Update Note");

            isUpdate = true;
            note_id = getArguments().getLong("id");

            noteViewModel.getNoteFindById(note_id).observe(getActivity(), new Observer<Note>() {
                @Override
                public void onChanged(Note note) {
                    currentNote = note;
                    etInput.setText(note.content);
                    image_uri = note.imageUri;
                    ivPickUpImage.setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(note.imageUri).into(ivPickUpImage);

                }
            });


        } else {
            ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar().setTitle("New Note");

        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_dialog_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_pickup_image:

                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(pickPhoto, 110);

                break;
            case R.id.menu_save:
                // Validate

                if (isUpdate){
                    if (TextUtils.isEmpty(etInput.getText().toString())) {
                        Toast.makeText(getContext(), "Empty Content", Toast.LENGTH_SHORT).show();

                    } else {
                        if (image_uri != null && image_uri != "") {
                            String input = etInput.getText().toString();
                            Note note = new Note(note_id,image_uri, input,currentNote.created_at);
                            noteViewModel.updateNote(note);
                            dismiss();
                        } else {
                            Toast.makeText(getContext(), "Pick Image", Toast.LENGTH_SHORT).show();
                        }

                    }
                }else {
                    if (TextUtils.isEmpty(etInput.getText().toString())) {
                        Toast.makeText(getContext(), "Empty Content", Toast.LENGTH_SHORT).show();

                    } else {
                        if (image_uri != null && image_uri != "") {
                            String input = etInput.getText().toString();
                            Note note = new Note(image_uri, input);
                            noteViewModel.insertNote(note);
                            dismiss();
                        } else {
                            Toast.makeText(getContext(), "Pick Image", Toast.LENGTH_SHORT).show();
                        }

                    }
                }



                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 110:
                Uri imageUri = null;
                if (data != null) {
                    imageUri = data.getData();
                    image_uri = imageUri.toString();

                }
                ivPickUpImage.setVisibility(View.VISIBLE);
                Glide.with(Objects.requireNonNull(getActivity())).load(imageUri).into(ivPickUpImage);
                break;
        }
    }
}
