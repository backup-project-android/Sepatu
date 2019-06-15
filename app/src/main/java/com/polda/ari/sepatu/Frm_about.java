package com.polda.ari.sepatu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Frm_about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_about);
        getSupportActionBar().setTitle("About");
    }
}
