package com.polda.ari.sepatu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MenuApps extends AppCompatActivity {
ImageButton im_mn1,im_mn2,im_mn3,im_mn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_apps);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_hpp);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Hypeclient Shoes");
        im_mn1 = (ImageButton) findViewById(R.id.btn_1);
        im_mn2 = (ImageButton) findViewById(R.id.btn_2);
        im_mn3 = (ImageButton) findViewById(R.id.btn_3);
        im_mn4 = (ImageButton) findViewById(R.id.btn_4);

        im_mn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuApps.this, Frm_pilihan.class);
                startActivity(intent);
            }
        });

        im_mn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuApps.this, Frm_trick.class);
                startActivity(intent);
            }
        });

        im_mn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuApps.this, Frm_info.class);
                startActivity(intent);
            }
        });

        im_mn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuApps.this, Frm_about.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.komplain){
            startActivity(new Intent(this, Frm_komplain.class));
        }

        return true;
    }


}
