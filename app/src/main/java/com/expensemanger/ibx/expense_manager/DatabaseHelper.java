package com.expensemanger.ibx.expense_manager;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ibx on 12/7/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "DailyExpence_db";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ONE = "Category";
    public static final String COLUMN_TWO = "Date";
    public static final String COLUMN_THREE = "Name";
    public static final String COLUMN_FOUR = "Details";
    public static final String COLUMN_FIVE = "Price";
    public static final String COLUMN_SIX = "Payment";

    public static final String TABLE_NAME = "Daily_Expence";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_ONE + " TEXT, "
                    + COLUMN_TWO + " TEXT, "
                    + COLUMN_THREE + " TEXT, "
                    + COLUMN_FOUR + " TEXT, "
                    + COLUMN_FIVE + " INTEGER, "
                    + COLUMN_SIX + " TEXT"
                    + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {


        try {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("bb", e.getLocalizedMessage().toString());
        }


    }


    public long insertValues(String category, String date, String item_Name, String item_details, int item_Price, String payment_Mode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ONE, category);
        values.put(COLUMN_TWO, date);
        values.put(COLUMN_THREE, item_Name);
        values.put(COLUMN_FOUR, item_details);
        values.put(COLUMN_FIVE, item_Price);
        values.put(COLUMN_SIX, payment_Mode);
        long result = db.insert(TABLE_NAME, null, values);
        return result;
    }

    public ArrayList<Note> getAllRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        String [] columns= new String[] {COLUMN_FIVE, COLUMN_ONE};
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        ArrayList<Note> notes = new ArrayList<Note>();
        Note note;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                note = new Note();
                note.set_id(cursor.getInt(0));
                note.setCategory(cursor.getString(1));
                note.setDate(cursor.getString(2));
                note.setName(cursor.getString(3));
                note.setDetails(cursor.getString(4));
                note.setPrice(cursor.getInt(5));
                note.setPayment(cursor.getString(6));
                notes.add(note);
            }
        }
        cursor.close();
        db.close();

        return notes;

    }

    public int getTotalExpence() {
        int total = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_FIVE + ") as Total FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst())
            total = cursor.getInt(cursor.getColumnIndex("Total"));
        return total;
    }

    public boolean updateValues(int _id, String category, String date, String item_Name, String item_details, int item_Price, String payment_Mode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, _id);
        values.put(COLUMN_ONE, category);
        values.put(COLUMN_TWO, date);
        values.put(COLUMN_THREE, item_Name);
        values.put(COLUMN_FOUR, item_details);
        values.put(COLUMN_FIVE, item_Price);
        values.put(COLUMN_SIX, payment_Mode);
        db.update(TABLE_NAME, values, COLUMN_ID + "= ?", new String[]{"" + _id});
        db.close();
        return true;
    }

    public boolean deleteRow(int _id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "= ?", new String[]{"" + _id});
     //   db.q
        return true;
    }



    public ArrayList<Note> remove_Duplicats(){

        SQLiteDatabase db = this.getWritableDatabase();
        String [] columns= new String[] {COLUMN_ONE,COLUMN_TWO,COLUMN_THREE,COLUMN_FOUR,COLUMN_FIVE};
      Cursor cursor=  db.query(true, TABLE_NAME, columns, null, null, COLUMN_SIX, null, null, null);
        ArrayList<Note> notes = new ArrayList<Note>();
        Note note;
        for(int i=0; i<notes.size();i++){
            cursor.moveToNext();
            note=new Note();
       //     note.set_id(cursor.getInt(0));
            note.setCategory(cursor.getString(1));
            note.setDate(cursor.getString(2));
            note.setName(cursor.getString(3));
            note.setDetails(cursor.getString(4));
            note.setPayment(cursor.getString(6));
            notes.add(note);

        }
        cursor.close();
        db.close();
        return notes;
    }




}