package com.example.afinal;

 import android.content.ContentValues;
 import android.content.Context;
 import android.database.Cursor;
 import android.database.sqlite.SQLiteDatabase;
 import android.database.sqlite.SQLiteOpenHelper;
 import java.util.ArrayList;
 import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ContactInfo";
    private static final String TABLE_NAME = "labels";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NUM = "number";
    private static final String COLUMN_MSG = "message";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Category table create query
        String CREATE_NUM_TABLE="CREATE TABLE "+TABLE_NAME+" (" +COLUMN_ID+" INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, " + COLUMN_NUM+" TEXT, " + COLUMN_MSG+" TEXT);";
        db.execSQL(CREATE_NUM_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insert(String Num,String Msg){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COLUMN_NUM, Num);
        values.put(COLUMN_MSG, Msg);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public List<String> getNumLabels(){
        List<String> list = new ArrayList<String>();
        String selectQuery = "SELECT number FROM labels";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                list.add(c.getString(0));
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return list;
    }
    public String get(String Num,String Column,String Filter,String Table){
        String Result="";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT "+Column+" FROM "+ Table+" Where "+  Filter + "="+"'"+Num+"'";
        Cursor  c = db.rawQuery(selectQuery,null);
        if (c.moveToFirst()) {
            Result= c.getString((0));
        }
        c.close();
        db.close();

        return Result;
    }

    public boolean checkdupli(String number){
        String selectQuery = "SELECT number FROM labels";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                if(number.equals(c.getString(0))){
                    return true;
                }
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return false;
    }
}

