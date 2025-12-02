package com.example.javacheatsheet;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EntryDetailActivity extends AppCompatActivity {

    public static final String EXTRA_ENTRY_ID = "entry_id";

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_detail);

        int entryId = getIntent().getIntExtra(EXTRA_ENTRY_ID, -1);

        TextView textTitle = findViewById(R.id.textTitle);
        TextView textDescription = findViewById(R.id.textDescription);
        TextView textCode = findViewById(R.id.textCode);

        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 1. Entry laden
        Cursor cursor = db.rawQuery(
                "SELECT title, description FROM " + DatabaseHelper.TABLE_ENTRY +
                        " WHERE id = ?",
                new String[]{String.valueOf(entryId)}
        );

        String title = "";
        String description = "";

        if (cursor.moveToFirst()) {
            title = cursor.getString(0);
            description = cursor.getString(1);
        }
        cursor.close();

        // 2. Code-Beispiele laden (code + output)
        Cursor codeCursor = db.rawQuery(
                "SELECT code, output FROM " + DatabaseHelper.TABLE_CODE +
                        " WHERE entry_id = ?",
                new String[]{String.valueOf(entryId)}
        );

        StringBuilder codeBuilder = new StringBuilder();

        while (codeCursor.moveToNext()) {
            String code = codeCursor.getString(0);
            String output = codeCursor.getString(1);

            codeBuilder.append(code);
            if (output != null && !output.isEmpty()) {
                codeBuilder.append("\n\n// ").append(output);
            }
            codeBuilder.append("\n\n");
        }
        codeCursor.close();


        // 3. In Views anzeigen
        textTitle.setText(title);
        textDescription.setText(description);
        textCode.setText(codeBuilder.toString());
    }
}
