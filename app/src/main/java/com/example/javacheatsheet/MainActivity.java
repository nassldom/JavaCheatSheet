package com.example.javacheatsheet;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", "onCreate started");

        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 1. Daten aus DB lesen
        Cursor cursor = db.rawQuery(
                "SELECT id, title FROM " + DatabaseHelper.TABLE_CHAPTER,
                null
        );
        ArrayList<String> chapterTitles = new ArrayList<>();

        while (cursor.moveToNext()) {
            String title = cursor.getString(1);  // Spalte 1 = title
            chapterTitles.add(title);
            Log.d("MainActivity", "Chapter: " + title);
        }

        cursor.close();

        // 2. ListView finden

        ListView listView = findViewById(R.id.listChapters);

        // 3. ArrayAdapter bauen (einfacher Standard Layout)

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                chapterTitles
        );

        // 4. Adapter an ListView übergeben

        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Kapitel-ID ist position+1, weil wir sie in Reihenfolge eingefügt haben
            int chapterId = position + 1;

            android.content.Intent intent =
                    new android.content.Intent(MainActivity.this, EntryListActivity.class);
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
