package com.example.javacheatsheet;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "javacheatsheet.db";
    private static final int DATABASE_VERSION = 3; // Version erhöht, damit onUpgrade läuft

    public static final String TABLE_CHAPTER = "chapter";
    public static final String TABLE_ENTRY   = "entry";
    public static final String TABLE_CODE    = "code_example";

    public static final String COL_ID = "_id";
    public static final String COL_CHAPTER_ID = "chapter_id";
    public static final String COL_ENTRY_ID = "entry_id";
    public static final String COL_TITLE = "title";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_SHORT_DESC = "short_description";
    public static final String COL_CODE = "code";
    public static final String COL_OUTPUT = "output";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String CREATE_CHAPTER_TABLE =
            "CREATE TABLE " + TABLE_CHAPTER + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_TITLE + " TEXT NOT NULL," +
                    COL_SHORT_DESC + " TEXT" +
                    ");";

    private static final String CREATE_ENTRY_TABLE =
            "CREATE TABLE " + TABLE_ENTRY + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_CHAPTER_ID + " INTEGER NOT NULL, " +
                    COL_TITLE + " TEXT NOT NULL, " +
                    COL_DESCRIPTION + " TEXT, " +
                    "FOREIGN KEY(" + COL_CHAPTER_ID + ") REFERENCES " +
                    TABLE_CHAPTER + "(" + COL_ID + ")" +
                    ");";

    private static final String CREATE_CODE_TABLE =
            "CREATE TABLE " + TABLE_CODE + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_ENTRY_ID + " INTEGER NOT NULL, " +
                    COL_CODE + " TEXT, " +
                    COL_OUTPUT + " TEXT, " +
                    "FOREIGN KEY(" + COL_ENTRY_ID + ") REFERENCES " +
                    TABLE_ENTRY + "(" + COL_ID + ")" +
                    ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CHAPTER_TABLE);
        db.execSQL(CREATE_ENTRY_TABLE);
        db.execSQL(CREATE_CODE_TABLE);

        // ==== CHAPTER ====
        db.execSQL("INSERT INTO " + TABLE_CHAPTER +
                " (" + COL_TITLE + ", " + COL_SHORT_DESC + ") VALUES " +
                "('1.00 Primitive Datensätze und Objekte', 'Grundlagen: primitive Daten vs. Objekte.')," +
                "('1.01 Numerische primitive Datensätze', 'Ganzzahlen, Gleitkommazahlen, Bitbreiten.')," +
                "('1.02 Gleitkommazahl-Typen', 'float vs. double, Literale, Genauigkeit.')," +
                "('1.03 Der primitive Datentyp char', '16-Bit-Zeichen, Unicode, Steuerzeichen.')," +
                "('1.04 Zeichenliterale', 'Einzelne Zeichen, Escape-Sequenzen wie ''\\n''.')," +
                "('1.05 Primitiver Datentyp boolean', 'Wahrheitswerte true/false.')," +
                "('2.00 Variablen und Zuweisungsanweisungen', 'Speicher, Variablen als benannte Bereiche.')," +
                "('2.01 Variablen', 'Vorstellung als Kästchen im Speicher.')," +
                "('2.02 Deklaration einer Variable', 'Beispiel long payAmount = 123;')," +
                "('2.03 Syntax der Variablendeklaration', 'Formen: Typ Name;, Typ Name = Wert; usw.');");

        // ==== ENTRY ====

        // 1.00 - HTML-basiert für Fett + Listen
        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (" + COL_CHAPTER_ID + ", " + COL_TITLE + ", " + COL_DESCRIPTION + ") VALUES (" +
                "1, '1.00 Primitive Datensätze und Objekte', " +
                "'" +
                "<p>In Java fallen <b>alle Daten</b> in eine von zwei Kategorien: <b>primitive Daten</b> und <b>Objekte</b>.</p>" +
                "<p>Es gibt nur <b>acht primitive Datentypen</b>.<br/>" +
                "Java besitzt jedoch viele verschiedene <b>Objekt-Typen</b>, und du kannst so viele weitere <b>selbst definieren</b>, wie du brauchst.</p>" +
                "<p>Jeder Datentyp, den du selbst erstellst, ist ein <b>Objekttyp</b>.<br/>" +
                "Da Java eine <b>objektorientierte Programmiersprache</b> ist, wirst du später noch viel ausführlicher über Objekte lernen.</p>" +
                "<p>Im Moment ist Folgendes wichtig:</p>" +
                "<ul>" +
                "<li>Ein <b>primitiver Datenwert</b> verwendet nur eine <b>kleine, feste Anzahl von Bytes</b>.</li>" +
                "<li>Es gibt genau <b>acht primitive Datentypen</b>.</li>" +
                "<li>Programmierer können keine neuen primitiven Datentypen erstellen.</li>" +
                "<li>Ein <b>Objekt</b> ist ein <b>größerer Datenblock</b>, der viele Bytes Speicher nutzen kann.</li>" +
                "<li>Ein Objekt besteht meist aus <b>mehreren internen Bestandteilen</b>.</li>" +
                "<li>Der <b>Datentyp</b> eines Objekts wird seine <b>Klasse</b> genannt.</li>" +
                "<li>Viele Klassen sind in Java bereits <b>vordefiniert</b>.</li>" +
                "<li>Programmierer können <b>eigene Klassen definieren</b>, um spezielle Anforderungen eines Programms zu erfüllen.</li>" +
                "</ul>" +
                "'" +
                ");");

        // 1.01 ff. – einfache Fließtexte, wie bisher
        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (" + COL_CHAPTER_ID + ", " + COL_TITLE + ", " + COL_DESCRIPTION + ") VALUES (" +
                "2, '1.01 Numerische primitive Datensätze', " +
                "'Sechs der acht primitiven Datentypen sind numerisch. Integer-Typen (byte, short, int, long) " +
                "haben keinen Nachkommateil, Gleitkomma-Typen (float, double) können Nachkommastellen darstellen. " +
                "Jeder Typ hat feste Bitbreite und damit einen festen Wertebereich.');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (" + COL_CHAPTER_ID + ", " + COL_TITLE + ", " + COL_DESCRIPTION + ") VALUES (" +
                "3, '1.02 Gleitkommazahl-Typen', " +
                "'float (32 Bit, einfache Genauigkeit) und double (64 Bit, doppelte Genauigkeit) speichern Gleitkommazahlen. " +
                "Gleitkomma-Literale enthalten einen Dezimalpunkt und sind standardmäßig vom Typ double. In der Praxis " +
                "solltest du fast immer double verwenden.');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (" + COL_CHAPTER_ID + ", " + COL_TITLE + ", " + COL_DESCRIPTION + ") VALUES (" +
                "4, '1.03 Der primitive Datentyp char', " +
                "'char repräsentiert ein einzelnes Zeichen und verwendet 16 Bit. Java nutzt Unicode, " +
                "sodass praktisch alle Zeichen der Welt dargestellt werden können. Dasselbe Bitmuster kann je nach Datentyp " +
                "als Zahl oder als Zeichen interpretiert werden.');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (" + COL_CHAPTER_ID + ", " + COL_TITLE + ", " + COL_DESCRIPTION + ") VALUES (" +
                "5, '1.04 Zeichenliterale', " +
                "'Ein Zeichenliteral ist ein einzelnes Zeichen in einfachen Anführungszeichen, z.B. ''m''. " +
                "Steuerzeichen wie Zeilenumbruch und Tabulator werden als Escape-Sequenzen ''\\n'' und ''\\t'' geschrieben. " +
                "Strings wie \"Hello\" sind keine char-Literale, sondern Objekte des Typs String.');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (" + COL_CHAPTER_ID + ", " + COL_TITLE + ", " + COL_DESCRIPTION + ") VALUES (" +
                "6, '1.05 Primitiver Datentyp boolean', " +
                "'boolean repräsentiert genau zwei Werte: true oder false. Der Name geht auf George Boole zurück. " +
                "Logisch enthält ein boolean ein Bit Information, intern verwendet Java aber mehr als ein Bit.');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (" + COL_CHAPTER_ID + ", " + COL_TITLE + ", " + COL_DESCRIPTION + ") VALUES (" +
                "7, '2.00 Variablen und Zuweisungsanweisungen', " +
                "'Im Hauptspeicher werden Befehle und Daten beide als Bitmuster gespeichert. " +
                "Eine Variable ist ein benannter Speicherbereich mit festem Datentyp, in dem ein Wert liegt.');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (" + COL_CHAPTER_ID + ", " + COL_TITLE + ", " + COL_DESCRIPTION + ") VALUES (" +
                "8, '2.01 Variablen', " +
                "'Stell dir eine Variable als kleines Kästchen aus Bytes vor, das einen Wert eines bestimmten Datentyps enthält. " +
                "Variablen entstehen und verschwinden zur Laufzeit; der Speicher kann später für andere Zwecke genutzt werden.');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (" + COL_CHAPTER_ID + ", " + COL_TITLE + ", " + COL_DESCRIPTION + ") VALUES (" +
                "9, '2.02 Deklaration einer Variable', " +
                "'Eine Deklaration wie long payAmount = 123; teilt dem Programm mit, dass es eine Variable dieses Typs braucht " +
                "und ggf. mit einem Anfangswert belegt werden soll. Hardwaredetails übernimmt der Compiler.');");

        db.execSQL("INSERT INTO " + TABLE_ENTRY +
                " (" + COL_CHAPTER_ID + ", " + COL_TITLE + ", " + COL_DESCRIPTION + ") VALUES (" +
                "10, '2.03 Syntax der Variablendeklaration', " +
                "'Formen: Datentyp Name; Datentyp Name = Wert; Datentyp v1, v2; Datentyp v1 = w1, v2 = w2; " +
                "Für verschiedene Datentypen nutzt man separate Deklarationen.');");

        // ==== CODE ====
        db.execSQL("INSERT INTO " + TABLE_CODE +
                " (" + COL_ENTRY_ID + ", " + COL_CODE + ", " + COL_OUTPUT + ") VALUES " +
                "(9, 'public class Example {\\n    public static void main(String[] args) {\\n        long payAmount = 123;\\n        System.out.println(\"Die Variable enthält: \" + payAmount);\\n    }\\n}', " +
                "'Deklaration und Initialisierung einer long-Variablen.' );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CODE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAPTER);
        onCreate(db);
    }
}
