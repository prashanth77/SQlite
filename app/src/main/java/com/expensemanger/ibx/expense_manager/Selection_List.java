package com.expensemanger.ibx.expense_manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ibx on 13/7/18.
 */

public class Selection_List extends Activity {
    TextView listall, listbydate, listbymonth, listbyyear;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_expenses);
        listall = (TextView) findViewById(R.id.listall);
        listbydate = (TextView) findViewById(R.id.listbydate);
        listbymonth = (TextView) findViewById(R.id.listbymonth);
        listbyyear = (TextView) findViewById(R.id.listbyyear);
        databaseHelper = new DatabaseHelper(this);


        listall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Selection_List.this, Activity_ListAll.class);
                startActivity(intent);
            }
        });


        listbydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Selection_List.this, Activity_ListByDate.class);
                startActivity(intent);
            }
        });


    }
}
