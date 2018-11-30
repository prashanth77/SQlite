package com.expensemanger.ibx.expense_manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ibx on 13/7/18.
 */

public class Activity_ListAll extends Activity {
    ArrayList<Note> notes;

    ListView listView;
    DatabaseHelper databaseHelper;
    private static listAdapter adapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listall);
        databaseHelper = new DatabaseHelper(this);
        notes = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView_all);
        ArrayList<Note> listall = databaseHelper.getAllRecords();
        ArrayList<Note> listall1 = databaseHelper.remove_Duplicats();
        try {
            for(int i=0; i<=listall1.size(); i++){


                String aaCat=listall1.get(i).getCategory();
                String aaDate=listall1.get(i).getDate();
                String aaName=listall1.get(i).getName();
                int aaPri=listall1.get(i).getPrice();

                Log.e("aaa", ""+aaCat+ ""+aaDate+ ""+aaName+""+aaPri);
            }

        }
catch (Exception e){
         Log.e("eeeeee",""+e.getLocalizedMessage());
}


        int total_amount = databaseHelper.getTotalExpence();
        adapter = new listAdapter(listall, getApplicationContext());
        listView.setAdapter(adapter);


    }

    public class listAdapter extends ArrayAdapter<Note> {

        private ArrayList<Note> dataSet;
        Context mContext;

        private class ViewHolder {
            TextView txtName;
            TextView txtcategory;
            TextView txtdate;
            TextView txtprice;
        }

        public listAdapter(ArrayList<Note> data, Context context) {
            super(context, R.layout.custom_listall, data);
            this.dataSet = data;
            this.mContext = context;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Note note = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag

            final View result;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.custom_listall, parent, false);
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.la_name);
                viewHolder.txtcategory = (TextView) convertView.findViewById(R.id.la_category);
                viewHolder.txtdate = (TextView) convertView.findViewById(R.id.la_date);
                viewHolder.txtprice = (TextView) convertView.findViewById(R.id.la_price);

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Activity_ListAll.this, Add_Expense.class);
                        intent.putExtra("isUpdate", true);
                        intent.putExtra("COL_Id", note.get_id());
                        intent.putExtra("COL_1", note.getCategory());
                        intent.putExtra("COL_2", note.getDate());
                        intent.putExtra("COL_3", note.getName());
                        intent.putExtra("COL_4", note.getDetails());
                        intent.putExtra("COL_5", note.getPrice());
                        intent.putExtra("COL_6", note.getPayment());
                        startActivity(intent);
                    }
                });

                result = convertView;
                convertView.setTag(viewHolder);


            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            viewHolder.txtName.setText(note.getName());
            viewHolder.txtcategory.setText(note.getDetails());
            viewHolder.txtdate.setText(note.getDate());
            viewHolder.txtprice.setText("" + note.getPrice());

            return convertView;
        }
    }


}
