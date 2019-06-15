package com.polda.ari.sepatu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

public class Frm_info extends AppCompatActivity {
    NiftyDialogBuilder dialogs;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private String[] info={
            "Fast Cleaning","Deep Cleaning","Repaint"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_info);
        getSupportActionBar().setTitle("Info Treatment");
        dialogs = NiftyDialogBuilder.getInstance(this);
        listView=(ListView) findViewById(R.id.listViewInfo);
        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,info);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == 0) {

                    dialogs
                            .withTitle("FAST CLEANING")
                            .withMessage("- Semua jenis sepatu: sneakers, boots, flat shoes, high heels, wedges, kid shoes\n" +
                                    "- Semua jenis bahan: canvas, suede, nubuck, mesh, knit, nylon, textile\n" +
                                    "- Membersihkan bagian outsole dan midsole\n" +
                                    "- Durasi 1 hari pengerjaan")
                            .withDialogColor("#6fc6ef")
                            .withButton1Text("OK")
                            .withEffect(Effectstype.SlideBottom);
                    dialogs.isCancelableOnTouchOutside(true);
                    dialogs.setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogs.dismiss();
                        }
                    });
                    dialogs.show();

                }

                if (position == 1) {

                    dialogs
                            .withTitle("DEEP CLEANING")
                            .withMessage("- Semua jenis sepatu : sneakers, boots, mountain boots, safety shoes, sport shoes,\n" +
                                    "flat shoes, high heels, wedges, kid shoes\n" +
                                    "- Semua jenis bahan: canvas, suede, nubuck, mesh, knit, nylon, textile\n" +
                                    "- Membersihkan semua bagian sepatu: midsole, outsole, insole, upper, dan lace\n" +
                                    "- Penghilangan noda : aspal, lumpur, oli, minyak, saus, cat, dll\n" +
                                    "- Durasi 2 – 3 hari")
                            .withDialogColor("#6fc6ef")
                            .withButton1Text("OK")
                            .withEffect(Effectstype.SlideBottom);
                    dialogs.isCancelableOnTouchOutside(true);
                    dialogs.setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogs.dismiss();
                        }
                    });
                    dialogs.show();

                }

                if (position == 2) {

                    dialogs
                            .withTitle("REPAINT")
                            .withMessage("- Pewarnaan ulang warna sepatu yang telah pudar\n" +
                                    "- Dilakukan special treatment sebelum di-repaint\n" +
                                    "- Material yang bisa di-repaint: canvas, suede dan kulit\n" +
                                    "- Durasi 1- 2 minggu")
                            .withDialogColor("#6fc6ef")
                            .withButton1Text("OK")
                            .withEffect(Effectstype.SlideBottom);
                    dialogs.isCancelableOnTouchOutside(true);
                    dialogs.setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogs.dismiss();
                        }
                    });
                    dialogs.show();

                }

            }
        });
    }
}
