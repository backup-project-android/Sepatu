package com.polda.ari.sepatu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Frm_pilihan extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private String[] layanan={
            "Fast Cleaning","Deep Cleaning","Repaint"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_pilihan);
        getSupportActionBar().setTitle("Treatment");
        listView=(ListView) findViewById(R.id.listViewLayanan);
        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,layanan);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0) {
                    Intent myIntent = new Intent(view.getContext(), Frm_order.class);
                    startActivityForResult(myIntent, 0);
                }

                if (position == 1) {
                    Intent myIntent = new Intent(view.getContext(), Form_order2.class);
                    startActivityForResult(myIntent, 0);
                }

                if (position == 2) {
                    Intent myIntent = new Intent(view.getContext(), Frm_order3.class);
                    startActivityForResult(myIntent, 0);
                }

            }
        });


    }
}
