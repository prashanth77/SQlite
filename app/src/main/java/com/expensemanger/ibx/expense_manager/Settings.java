package com.expensemanger.ibx.expense_manager;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibx on 23/7/18.
 */

public class Settings extends Activity {
    CheckBox check_date;
    DatabaseHelper helper;
    TextView shareExpenses,shareExpenses1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);
       helper = new DatabaseHelper(this);
       shareExpenses=(TextView)findViewById(R.id.shareexpense);
        shareExpenses1=(TextView)findViewById(R.id.shareexpense1);


        final   SQLiteDatabase sqldb = helper.getReadableDatabase(); //My Database class

        shareExpenses.setOnClickListener(new View.OnClickListener() {
            Cursor c = null;

            @Override
            public void onClick(View v) { //main code begins here
                try {
                    c = sqldb.rawQuery("select * from Daily_Expence", null);
                    int rowcount = 0;
                    int colcount = 0;
                    File sdCardDir = Environment.getExternalStorageDirectory();
                    String filename = "MyBackUp1p.pdf";
                    // the name of the file to export with
                    File saveFile = new File(sdCardDir, filename);
                    FileWriter fw = new FileWriter(saveFile);


                    BufferedWriter bw = new BufferedWriter(fw);
                    rowcount = c.getCount();
                    colcount = c.getColumnCount();
                    if (rowcount > 0) {
                        c.moveToFirst();

                        for (int i = 0; i < colcount; i++) {
                            if (i != colcount - 1) {

                                bw.write(c.getColumnName(i) + ",");

                            } else {

                                bw.write(c.getColumnName(i));

                            }
                        }
                        bw.newLine();

                        for (int i = 0; i < rowcount; i++) {
                            c.moveToPosition(i);

                            for (int j = 0; j < colcount; j++) {
                                if (j != colcount - 1)
                                    bw.write(c.getString(j) + ",");
                                else
                                    bw.write(c.getString(j));
                            }
                            bw.newLine();
                        }
                        bw.flush();
//                        infotext.setText("Exported Successfully.");

                        Toast.makeText(getApplicationContext(),"Exported Successfully", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    if (sqldb.isOpen()) {
                        sqldb.close();
                        Log.e("exxxxxxxxxx",""+ex.getLocalizedMessage());
                    }

                } finally {

                }

            }
        });
        shareExpenses1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createPDF();

            }
        });



    }

}