package com.example.javacheatsheet;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT " + DatabaseHelper.COL_ID + ", " + DatabaseHelper.COL_TITLE +
                        " FROM " + DatabaseHelper.TABLE_CHAPTER,
                null
        );

        ArrayList<String> chapterTitles = new ArrayList<>();

        while (cursor.moveToNext()) {
            String title = cursor.getString(1);  // Spalte 1 = title
            chapterTitles.add(title);
        }

        cursor.close();
        db.close();

        ListView listView = findViewById(R.id.listChapters);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                chapterTitles
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            int chapterId = position + 1;

            Intent intent = new Intent(MainActivity.this, EntryListActivity.class);
            intent.putExtra(EntryListActivity.EXTRA_CHAPTER_ID, chapterId);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
