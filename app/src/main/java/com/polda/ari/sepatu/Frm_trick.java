package com.polda.ari.sepatu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Frm_trick extends AppCompatActivity {
ImageButton im_tra,im_trb,im_trc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_trick);
        getSupportActionBar().setTitle("Tips & tricks");
        im_tra = (ImageButton) findViewById(R.id.btn_tra);
        im_trb = (ImageButton) findViewById(R.id.btn_trb);
        im_trc = (ImageButton) findViewById(R.id.btn_trc);

        im_tra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Frm_trick.this, Frm_det_trick.class);
                startActivity(intent);
            }
        });

        im_trb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Frm_trick.this, Frm_dt_trick1.class);
                startActivity(intent);
            }
        });

        im_trc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Frm_trick.this, Frm_dt_trick2.class);
                startActivity(intent);
            }
        });

    }
}
