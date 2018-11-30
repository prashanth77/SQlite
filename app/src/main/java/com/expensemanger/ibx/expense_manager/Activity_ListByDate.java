package com.expensemanger.ibx.expense_manager;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ibx on 17/7/18.
 */

public class Activity_ListByDate extends Activity {

    DatabaseHelper databaseHelper;
    String getDates;
    String myFormat = "yyyy-MM-dd"; //In which you need put here
    SimpleDateFormat  sdf = new SimpleDateFormat(myFormat, Locale.US);;

    ArrayList<String> list_getDates=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listall);
        databaseHelper=new DatabaseHelper(this);

        ArrayList<Note> listall = databaseHelper.getAllRecords();
        for(int i=0; i<listall.size();i++){

            Log.e("#aa   ", "Date   "+getDates);
            list_getDates.add(getDates);

        }


for(int j=0; j<list_getDates.size(); j++){

}



    }
}
