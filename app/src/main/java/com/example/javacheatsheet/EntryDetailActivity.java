package com.example.javacheatsheet;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EntryDetailActivity extends AppCompatActivity {

    public static final String EXTRA_ENTRY_ID = "extra_entry_id";
    public static final String EXTRA_CHAPTER_ID = "extra_chapter_id";

    private DatabaseHelper dbHelper;
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvCode;
    private TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_detail);

        tvTitle       = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvCode        = findViewById(R.id.tvCode);
        tvOutput      = findViewById(R.id.tvOutput);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        int entryId   = intent.getIntExtra(EXTRA_ENTRY_ID, -1);
        int chapterId = intent.getIntExtra(EXTRA_CHAPTER_ID, -1);

        if (entryId != -1) {
            loadEntry(entryId, chapterId);
        }
    }

    private void loadEntry(int entryId, int chapterId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query(
                DatabaseHelper.TABLE_ENTRY,
                new String[] { DatabaseHelper.COL_TITLE, DatabaseHelper.COL_DESCRIPTION },
                DatabaseHelper.COL_ID + "=?",
                new String[] { String.valueOf(entryId) },
                null, null, null
        );

        String title = "";
        String longDescription = "";

        if (c.moveToFirst()) {
            title = c.getString(0);
            longDescription = c.getString(1);
        }
        c.close();

        String shortDescription = "";
        if (chapterId != -1) {
            Cursor chapCursor = db.query(
                    DatabaseHelper.TABLE_CHAPTER,
                    new String[]{ DatabaseHelper.COL_SHORT_DESC },
                    DatabaseHelper.COL_ID + "=?",
                    new String[]{ String.valueOf(chapterId) },
                    null, null, null
            );

            if (chapCursor.moveToFirst()) {
                shortDescription = chapCursor.getString(0);
            }
            chapCursor.close();
        }

        tvTitle.setText(title);

        String fullText;
        if (!shortDescription.isEmpty()) {
            fullText = "<p><i>" + shortDescription + "</i></p><br/>" + longDescription;
        } else {
            fullText = longDescription;
        }

        tvDescription.setText(Html.fromHtml(fullText, Html.FROM_HTML_MODE_LEGACY));

        Cursor codeCursor = db.query(
                DatabaseHelper.TABLE_CODE,
                new String[] { DatabaseHelper.COL_CODE, DatabaseHelper.COL_OUTPUT },
                DatabaseHelper.COL_ENTRY_ID + "=?",
                new String[] { String.valueOf(entryId) },
                null, null, null
        );

        if (codeCursor.moveToFirst()) {
            String code = codeCursor.getString(0);
            String output = codeCursor.getString(1);

            tvCode.setText(code);
            tvOutput.setText(output);
        } else {
            tvCode.setText("");
            tvOutput.setText("");
        }
        codeCursor.close();

        db.close();
    }
}
