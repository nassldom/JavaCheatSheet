package com.example.javacheatsheet;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EntryListActivity extends AppCompatActivity {

    public static final String EXTRA_CHAPTER_ID = "chapter_id";

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);

        int chapterId = getIntent().getIntExtra(EXTRA_CHAPTER_ID, -1);

        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 1. Kapitel-Erklärung oben anzeigen
        TextView textChapterDescription = findViewById(R.id.textChapterDescription);

        Cursor chapterCursor = db.rawQuery(
                "SELECT short_description FROM " + DatabaseHelper.TABLE_CHAPTER +
                        " WHERE id = ?",
                new String[]{String.valueOf(chapterId)}
        );

        if (chapterCursor.moveToFirst()) {
            String desc = chapterCursor.getString(0);
            textChapterDescription.setText(desc);
        }
        chapterCursor.close();

        // 2. Einträge für dieses Kapitel laden
        ArrayList<Integer> entryIds = new ArrayList<>();
        ArrayList<String> entryTitles = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT id, title FROM " + DatabaseHelper.TABLE_ENTRY +
                        " WHERE chapter_id = ?",
                new String[]{String.valueOf(chapterId)}
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            entryIds.add(id);
            entryTitles.add(title);
        }

        cursor.close();

        ListView listView = findViewById(R.id.listEntries);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                entryTitles
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, itemId) -> {
            int entryId = entryIds.get(position);

            Intent intent = new Intent(EntryListActivity.this, EntryDetailActivity.class);
            intent.putExtra(EntryDetailActivity.EXTRA_ENTRY_ID, entryId);
            startActivity(intent);
        });
    }
}
