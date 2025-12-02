package com.example.javacheatsheet;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EntryListActivity extends AppCompatActivity {

    public static final String EXTRA_CHAPTER_ID = "extra_chapter_id";

    private DatabaseHelper dbHelper;
    private ListView listView;
    private TextView tvChapterShortDesc;
    private int currentChapterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);

        listView = findViewById(R.id.listEntries);
        tvChapterShortDesc = findViewById(R.id.tvChapterShortDesc);
        dbHelper = new DatabaseHelper(this);

        currentChapterId = getIntent().getIntExtra(EXTRA_CHAPTER_ID, -1);

        if (currentChapterId != -1) {
            loadChapterShortDesc(currentChapterId);
            loadEntries(currentChapterId);
        }
    }

    private void loadChapterShortDesc(int chapterId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query(
                DatabaseHelper.TABLE_CHAPTER,
                new String[]{ DatabaseHelper.COL_SHORT_DESC },
                DatabaseHelper.COL_ID + "=?",
                new String[]{ String.valueOf(chapterId) },
                null, null, null
        );

        if (c.moveToFirst()) {
            String shortDesc = c.getString(0);
            tvChapterShortDesc.setText(shortDesc);
        } else {
            tvChapterShortDesc.setText("");
        }

        c.close();
        db.close();
    }

    private void loadEntries(int chapterId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_ENTRY,
                new String[] {
                        DatabaseHelper.COL_ID,
                        DatabaseHelper.COL_TITLE,
                        DatabaseHelper.COL_DESCRIPTION
                },
                DatabaseHelper.COL_CHAPTER_ID + "=?",
                new String[]{ String.valueOf(chapterId) },
                null, null, null
        );

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{ DatabaseHelper.COL_TITLE },
                new int[]{ android.R.id.text1 },
                0
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            int entryId = (int) id;

            Intent intent = new Intent(EntryListActivity.this, EntryDetailActivity.class);
            intent.putExtra(EntryDetailActivity.EXTRA_ENTRY_ID, entryId);
            intent.putExtra(EntryDetailActivity.EXTRA_CHAPTER_ID, currentChapterId);
            startActivity(intent);
        });
    }
}
