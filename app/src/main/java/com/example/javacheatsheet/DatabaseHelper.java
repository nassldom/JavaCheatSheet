package com.example.javacheatsheet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CHAPTER = "chapter";
    public static final String TABLE_ENTRY   = "entry";
    public static final String TABLE_CODE    = "code_example";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createChapterTable =
                "CREATE TABLE " + TABLE_CHAPTER + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "title TEXT NOT NULL," +
                        "short_description TEXT" +
                        ");";

        String createEntryTable =
                "CREATE TABLE " + TABLE_ENTRY + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "chapter_id INTEGER NOT NULL," +
                        "title TEXT NOT NULL," +
                        "description TEXT," +
                        "FOREIGN KEY(chapter_id) REFERENCES " + TABLE_CHAPTER + "(id)" +
                        ");";

        String createCodeTable =
                "CREATE TABLE " + TABLE_CODE + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "entry_id INTEGER NOT NULL," +
                        "code TEXT," +
                        "output TEXT," +
                        "FOREIGN KEY(entry_id) REFERENCES " + TABLE_ENTRY + "(id)" +
                        ");";

        db.execSQL(createChapterTable);
        db.execSQL(createEntryTable);
        db.execSQL(createCodeTable);

        // --- Kapitel (DEINE Texte in short_description anpassen) ---

        db.execSQL("INSERT INTO " + TABLE_CHAPTER +
                " (title, short_description) VALUES (" +
                "'Variablen'," +
                "'Grundlagen zu Variablen in Java.');");

        db.execSQL("INSERT INTO " + TABLE_CHAPTER +
                " (title, short_description) VALUES (" +
                "'Strings'," +
                "'Arbeiten mit Zeichenketten in Java.');");

        db.execSQL("INSERT INTO " + TABLE_CHAPTER +
                " (title, short_description) VALUES (" +
                "'Primitive Datentypen'," +
                "'Grundlegende Zahlentypen, char und boolean.');");

        db.execSQL("INSERT INTO " + TABLE_CHAPTER +
                " (title, short_description) VALUES (" +
                "'Nicht-primitive Datentypen'," +
                "'Strings, Arrays, Klassen, Collections, Wrapper.');");

        db.execSQL("INSERT INTO " + TABLE_CHAPTER +
                " (title, short_description) VALUES (" +
                "'Numbers'," +
                "'Wrapper-Klassen, Math-Methoden und Konvertierungen.');");

        // --- Entries zu Kapitel 1: Variablen (chapter_id = 1) ---

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (chapter_id, title, description) VALUES (" +
                "1, 'Variable'," +
                "'String name = \"Hallo Dominic\"; ist eine änderbare Variable.');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (chapter_id, title, description) VALUES (" +
                "1, 'Konstante'," +
                "'final String name = \"Hello World\"; ist nicht änderbar (Konstante).');");

        // --- Entries zu Kapitel 2: Strings (chapter_id = 2) ---

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (chapter_id, title, description) VALUES (" +
                "2, 'String-Literale'," +
                "'String-Literale werden im String-Pool gespeichert und wiederverwendet.');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (chapter_id, title, description) VALUES (" +
                "2, 'Neues String-Objekt'," +
                "'new String(\"Hello\") erzeugt ein neues Objekt im Heap, auch wenn derselbe Text schon im Pool existiert.');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (chapter_id, title, description) VALUES (" +
                "2, 'Immutable'," +
                "'Strings sind unveränderlich. Änderungen erzeugen neue Objekte; bei vielen Änderungen besser StringBuilder verwenden.');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (chapter_id, title, description) VALUES (" +
                "2, 'Wichtige Methoden'," +
                "'length(), charAt(), substring(), toUpperCase(), toLowerCase(), indexOf(), contains().');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (chapter_id, title, description) VALUES (" +
                "2, 'Textblöcke'," +
                "'Textblöcke (ab Java 15) sind mehrzeilige Strings mit drei doppelten Anführungszeichen, bei denen Zeilenumbrüche und Formatierung erhalten bleiben.');");

        // --- Entries zu Kapitel 3: Primitive Datentypen (chapter_id = 3) ---

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (chapter_id, title, description) VALUES (" +
                "3, 'byte'," +
                "'Kleine Ganzzahlen von -128 bis 127, gut für speichersparende Arrays.');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (chapter_id, title, description) VALUES (" +
                "3, 'int'," +
                "'Standard-Ganzzahltyp, ca. -2 Milliarden bis +2 Milliarden.');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (chapter_id, title, description) VALUES (" +
                "3, 'double'," +
                "'Gleitkommazahl mit ca. 15–16 Stellen Genauigkeit.');");

        // --- Entries zu Kapitel 4: Nicht-primitive Datentypen (chapter_id = 4) ---

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (chapter_id, title, description) VALUES (" +
                "4, 'String'," +
                "'Objekt-Typ für Zeichenketten, unveränderlich (immutable).');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (chapter_id, title, description) VALUES (" +
                "4, 'Array'," +
                "'Feld fester Größe für Elemente eines Typs, z.B. int[] numbers = {1,2,3,4};');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (chapter_id, title, description) VALUES (" +
                "4, 'Klasse/Objekt'," +
                "'Klasse = Bauplan, Objekt = konkrete Instanz, z.B. Car myCar = new Car();');");

        // --- Entries zu Kapitel 5: Numbers (chapter_id = 5) ---

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (chapter_id, title, description) VALUES (" +
                "5, 'Math-Methoden'," +
                "'abs(), round(), floor(), ceil(), pow(), sqrt(), random().');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (chapter_id, title, description) VALUES (" +
                "5, 'Wrapper-Klassen'," +
                "'Integer, Double, Boolean – Wrapper machen primitive Typen zu Objekten, nützlich in Collections.');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (chapter_id, title, description) VALUES (" +
                "5, 'Konvertierungen'," +
                "'parseInt(), parseDouble(), toString(), valueOf(), Umwandlung zwischen String und Zahl.');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CODE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAPTER);
        onCreate(db);
    }
}
