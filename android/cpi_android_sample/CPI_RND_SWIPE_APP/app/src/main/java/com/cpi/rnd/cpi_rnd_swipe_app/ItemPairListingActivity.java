package com.cpi.rnd.cpi_rnd_swipe_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ItemPairListingActivity extends AppCompatActivity{
    private ListView listView1;
    ArrayAdapter arrayAdapter;
    private String criteria;
    DatabaseHandler db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_items_of_select_pair);
        db = new DatabaseHandler(getApplicationContext());

        listView1 = findViewById(R.id.listView);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            criteria = extras.getString("criteria");

        }
        setTitle(String.format("%s Listing",criteria));

        int criteraRowCount = db.getTableRowCountByResult(criteria);


        if (criteraRowCount>0) {
//            String[] rowData = new String[criteraRowCount];
            List<String> rowListByCriteria = db.getRowListByResult(criteria);
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, rowListByCriteria);
            listView1.setAdapter(arrayAdapter);
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra("criteria",criteria);

                    String rowIDText = ((String)((TextView)view).getText()).split(" ",2)[1];
                    i.putExtra("rowID",String.valueOf(rowIDText));
                    startActivity(i);
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                }
            });
        }
        else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
            alertDialogBuilder.setTitle("Error!!");

            // set dialog message
            alertDialogBuilder
                    .setMessage("This section is empty.")
                    .setCancelable(false)
                    .setPositiveButton("Back",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            finish();
                            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                        }
                    }).setNegativeButton("Home",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int id) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                }
            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }

        ImageButton fab = findViewById(R.id.homeButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
