package com.expensemanger.ibx.expense_manager;

import android.app.Activity;
import android.app.DatePickerDialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by ibx on 12/7/18.
 */

public class Add_Expense extends Activity {
    Spinner spin_category, spin_paymetn;
    Button btn_save, btn_cancel, btn_delete, btn_update;
    EditText et_date, et_ItemName, et_Itemdesc, et_Itemprice;
    DatabaseHelper databaseHelper;
    List<String> cat_list, pay_list;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    String current_date, item_name, item_details, category_name, payment_name;
    int item_price;
    boolean isUpdate = false;
    int _id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_categiri);
        databaseHelper = new DatabaseHelper(this);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_can);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_update = (Button) findViewById(R.id.btn_update);
        spin_category = (Spinner) findViewById(R.id.spinner_category);
        spin_paymetn = (Spinner) findViewById(R.id.spiner_payment);
        et_date = (EditText) findViewById(R.id.edit_date);
        et_ItemName = (EditText) findViewById(R.id.edit_itemName);
        et_Itemdesc = (EditText) findViewById(R.id.edit_itemdetails);
        et_Itemprice = (EditText) findViewById(R.id.edit_price);
        cat_list = new ArrayList<String>();
        cat_list.add("Books");
        cat_list.add("By Cheque");
        cat_list.add("Debit Card");
        cat_list.add("Credit Card");
        cat_list.add("EFT");
        cat_list.add("Vochers");

        pay_list = new ArrayList<String>();
        pay_list.add("By Cash");
        pay_list.add("Entertainment");
        pay_list.add("food");
        pay_list.add("Groceries");
        pay_list.add("Medical");
        pay_list.add("Travel");

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        try {
            isUpdate = getIntent().getExtras().getBoolean("isUpdate");

        } catch (Exception e) {
            e.getLocalizedMessage();
        }

        if (isUpdate == true) {
            _id = getIntent().getExtras().getInt("COL_Id");
            category_name = getIntent().getExtras().getString("COL_1");
            current_date = getIntent().getExtras().getString("COL_2");
            item_name = getIntent().getExtras().getString("COL_3");
            item_details = getIntent().getExtras().getString("COL_4");
            item_price = getIntent().getExtras().getInt("COL_5");
            payment_name = getIntent().getExtras().getString("COL_6");
            btn_delete.setVisibility(View.VISIBLE);
            btn_update.setVisibility(View.VISIBLE);
            update_expenceManager();
        } else {

            btn_save.setVisibility(View.VISIBLE);

        }


        final ArrayAdapter<String> category_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_gallery_item, cat_list);
        category_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_category.setAdapter(category_Adapter);
        int spinnerPosition2 = category_Adapter.getPosition(category_name);
        spin_category.setSelection(spinnerPosition2);
        spin_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_name = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> pay_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, pay_list);

        pay_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin_paymetn.setAdapter(pay_Adapter);
        int spinnerPosition = pay_Adapter.getPosition(payment_name);
        spin_paymetn.setSelection(spinnerPosition);
        spin_paymetn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                payment_name = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isUpdate==false){

                    item_name = et_ItemName.getText().toString();
                    item_details = et_Itemdesc.getText().toString();
                    item_price = Integer.parseInt(et_Itemprice.getText().toString());
                    long result = databaseHelper.insertValues(category_name, current_date, item_name, item_details, item_price, payment_name);
                    if (result != -1) {
                        Toast.makeText(getApplicationContext(), "inserted succesfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "something wrong", Toast.LENGTH_SHORT).show();
                    }
                }

            }


        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 boolean deleted=databaseHelper.deleteRow(_id);
                if(deleted==true){
//                    Toast.makeText(Add_Expense.this,"deleted succesfully",Toast.LENGTH_LONG).show();

                    et_date.getText().clear();
                    et_Itemprice.getText().clear();
                    et_Itemdesc.getText().clear();
                    et_ItemName.getText().clear();

                }
                else {
                    Toast.makeText(Add_Expense.this,"Something Wrong",Toast.LENGTH_LONG).show();

                }



            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                item_name = et_ItemName.getText().toString();
                item_details = et_Itemdesc.getText().toString();
                item_price = Integer.parseInt(et_Itemprice.getText().toString());

                    boolean updated=databaseHelper.updateValues(_id,category_name, current_date, item_name, item_details, item_price, payment_name);
                    if(updated==true){
                        Toast.makeText(Add_Expense.this,"Data Update",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(Add_Expense.this,"Data not Updated",Toast.LENGTH_LONG).show();

                    }

                }
        });



        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Add_Expense.this, Activity_ListAll.class);
                startActivity(intent);
            }
        });

        et_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Add_Expense.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        et_date.setText(sdf.format(myCalendar.getTime()));
        current_date = sdf.format(myCalendar.getTime());
    }
    private void update_expenceManager() {

        if (isUpdate == true) {
            et_date.setText(current_date);
            et_ItemName.setText(item_name);
            et_Itemprice.setText("" + item_price);
            et_Itemdesc.setText(item_details);
        }
    }
}
