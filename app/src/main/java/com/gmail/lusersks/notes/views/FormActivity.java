package com.gmail.lusersks.notes.views;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gmail.lusersks.notes.models.NotesData;
import com.gmail.lusersks.notes.R;

import static com.gmail.lusersks.notes.MainActivity.EXTRA_CONTENT;
import static com.gmail.lusersks.notes.MainActivity.EXTRA_FORM_TITLE;
import static com.gmail.lusersks.notes.MainActivity.EXTRA_NOTE;
import static com.gmail.lusersks.notes.presenters.NotesActions.EXTRA_ID;
import static com.gmail.lusersks.notes.presenters.NotesActions.EXTRA_TYPE;
import static com.gmail.lusersks.notes.provider.Constants.COL_BODY;
import static com.gmail.lusersks.notes.provider.Constants.COL_TITLE;
import static com.gmail.lusersks.notes.provider.Constants.COL_TYPE;
import static com.gmail.lusersks.notes.provider.Constants.NOTES_CONTENT_URI;
import static com.gmail.lusersks.notes.provider.NotesProvider.TAG_LOG;

public class FormActivity extends AppCompatActivity {
    private EditText etNoteTitle, etNoteContent;
    private String oldTitle, itemType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        initViews();
        readExtraFields();
        setButtonsBehavior();
    }

    private void readExtraFields() {
        Intent intent = getIntent();

        setTitle(intent.getStringExtra(EXTRA_FORM_TITLE));

        oldTitle = intent.getStringExtra(EXTRA_NOTE);
        String content = intent.getStringExtra(EXTRA_CONTENT);

        itemType = intent.getStringExtra(EXTRA_TYPE);
        if (itemType.equals("todo")) {
            if (content.isEmpty()) content = "- ";
        }

        etNoteTitle.setText(oldTitle);
        etNoteContent.setText(content);
    }

    private void initViews() {
        etNoteTitle = (EditText) findViewById(R.id.et_note_title);
        etNoteContent = (EditText) findViewById(R.id.et_note_content);
    }

    private void setButtonsBehavior() {
        (findViewById(R.id.btn_save_note)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                String title = etNoteTitle.getText().toString();
                String content = etNoteContent.getText().toString();

                if (getTitle().equals("New Note")) {
                    NotesData.addItem(title, content, itemType);

                } else if (getTitle().equals("Edit Note")) {
                    NotesData.editItem(oldTitle, title, content, itemType);
                    intent.putExtra(EXTRA_NOTE, title);
                    intent.putExtra(EXTRA_CONTENT, content);
                }

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        (findViewById(R.id.btn_cancel_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        });
    }
}
