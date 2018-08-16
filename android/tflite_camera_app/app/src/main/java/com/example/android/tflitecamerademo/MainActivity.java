package com.example.android.tflitecamerademo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ListView listView1;
    private Toolbar toolbar;
    String[] list = {"Mobilenet_0.50_224","NASNet mobile" };
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Select a model below:");
        listView1 = (ListView)findViewById(R.id.listView1);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,list);
        listView1.setAdapter(arrayAdapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {


                Intent i = new Intent(MainActivity.this, CameraActivity.class);

                if (position ==0){
                    i.putExtra("MODEL", "mobile_net_graph.lite");
                    i.putExtra("LABELS", "mobile_net_labels.txt");

                }

                else {
                    i.putExtra("MODEL", "nasnet_mobile_graph.tflite");
                    i.putExtra("LABELS", "nasnet_mobile_labels.txt");
                }
                startActivity(i);
            }
        });
    }


}
