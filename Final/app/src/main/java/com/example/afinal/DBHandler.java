package com.example.afinal;

 import android.content.ContentValues;
        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "Contactdb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "ContactData";
    private static final String ID_COL = "id";
    private static final String Numero_COL = "numero";
    private static final String Message_COL = "message";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Numero_COL + " NUMBER,"
                + Message_COL + "TEXT)";

        db.execSQL(query);
    }

    public void addNewContact(String ContactNumber, String ContactMessage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Numero_COL, ContactNumber);
        values.put(Message_COL, ContactMessage);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

