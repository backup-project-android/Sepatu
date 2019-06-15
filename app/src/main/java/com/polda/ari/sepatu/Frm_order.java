package com.polda.ari.sepatu;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class Frm_order extends AppCompatActivity {
EditText txt_alamat,txt_nama_p,txt_nohp,txt_jum1,txt_jum2,txt_jum3,txt_ket;
CheckBox cb_sepatu, cb_tas, cb_dompet;
String sepatu,tas,dompet;
TextView txt_lat, txt_long,txt_coba;
Spinner sp_name;
Button btn_simpan;
Integer bayar1=0,bayar2=0,bayar3=0;
public static String byr="",jenis_o="";
public static int kal1=0,kal2=0,kal3=0;
    NiftyDialogBuilder dialogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_order);
        getSupportActionBar().setTitle("Fast Cleaning");
        dialogs = NiftyDialogBuilder.getInstance(this);
        txt_alamat = (EditText) findViewById(R.id.txt_alamat);
        txt_alamat.setEnabled(false);
        txt_nama_p = (EditText) findViewById(R.id.txt_nama_p);
        txt_nohp = (EditText) findViewById(R.id.txt_nohp);
        //txt_jumlah = (EditText) findViewById(R.id.txt_jumlah);
        txt_lat = (TextView) findViewById(R.id.txt_lat);
        txt_long = (TextView) findViewById(R.id.txt_long);
        cb_sepatu = (CheckBox) findViewById(R.id.cb_sepatu);
        cb_tas = (CheckBox) findViewById(R.id.cb_tas);
        cb_dompet = (CheckBox) findViewById(R.id.cb_dompet);
        sp_name = (Spinner) findViewById(R.id.sp_name);
        txt_jum1 = (EditText) findViewById(R.id.txt_jum_1);
        txt_jum2 = (EditText) findViewById(R.id.txt_jum_2);
        txt_jum3 = (EditText) findViewById(R.id.txt_jum_3);
        txt_ket = (EditText) findViewById(R.id.txt_keterangan);
        btn_simpan = (Button) findViewById(R.id.btnSimpan);

        PlaceAutocompleteFragment places= (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        places.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                txt_alamat.setText(place.getAddress());
                txt_lat.setText(""+place.getLatLng().latitude);
                txt_long.setText(""+place.getLatLng().longitude);
                //Toast.makeText(getApplicationContext(),""+place.getLatLng().latitude, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        //int jumlah = Integer.parseInt(txt_jumlah.getText().toString().trim());

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_tas.isChecked()){
                    tas = "Tas";
                    bayar1 = 35000;
                    kal1 = Integer.parseInt(txt_jum1.getText().toString().trim());

                }else if(!cb_tas.isChecked()){
                    tas = "";
                    kal1 = 0;
                }

                if(cb_sepatu.isChecked()){
                    sepatu = "Sepatu";
                    bayar1 = 35000;
                    kal2 = Integer.parseInt(txt_jum2.getText().toString().trim());
                }else if(!cb_sepatu.isChecked()){
                    sepatu = "";
                    kal2 = 0;

                }

                if(cb_dompet.isChecked()){
                    dompet = "Dompet";
                    bayar1 = 35000;
                    kal3 = Integer.parseInt(txt_jum3.getText().toString().trim());
                }else if(!cb_dompet.isChecked()){
                    dompet = "";
                    kal3 = 0;

                }

                /*//Integer jumlah = Integer.parseInt(txt_jumlah.getText().toString().trim());
                Integer kalkulasi = kal1+kal2+kal3;
                Integer bay = (bayar1*kalkulasi)+10000;
                Integer totbayar = bay;
                //Toast.makeText(getApplication(),"Total Bayar "+bay,Toast.LENGTH_LONG).show();

                DecimalFormat mataUangIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

                formatRp.setCurrencySymbol("Rp.");
                formatRp.setMonetaryDecimalSeparator(',');
                formatRp.setGroupingSeparator('.');

                mataUangIndonesia.setDecimalFormatSymbols(formatRp);
                byr = mataUangIndonesia.format(totbayar);
                jenis_o = "Fast Cleaning";*/


                if(txt_nama_p.getText().toString().equals("")||txt_alamat.getText().toString().equals("")||
                        txt_nohp.getText().toString().equals("")){
                    Toast.makeText(getApplication(),"Data tidak boleh kosong",Toast.LENGTH_LONG).show();
                }else{
                    simpanOrder(txt_nama_p.getText().toString().trim(),txt_alamat.getText().toString().trim(),
                            txt_lat.getText().toString().trim(),txt_long.getText().toString().trim(),
                            txt_nohp.getText().toString().trim(),sepatu,tas,dompet,kal1,kal2,kal3,txt_ket.getText().toString().trim()
                            ,sp_name.getSelectedItem().toString().trim(),"Fast Cleaning","0");
                }

            }
        });

    }

    public void simpanOrder(final String Nama_p, final String Alamat_p,
                            final String Lati, final String Longi,
                            final String No_hp, final String Item1,
                            final String Item2, final String Item3,
                            final Integer Jumlah1,final Integer Jumlah2,final Integer Jumlah3,
                            final String Keterangan,final String Tipe,
                            final String Status,final String Bayar){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_NAMA_P, Nama_p));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_ALAMAT_P, Alamat_p));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_LAT, Lati));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_LONG, Longi));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_NO_HP, No_hp));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_ITEM1, Item1));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_ITEM2, Item2));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_ITEM3, Item3));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_JUMLAH1, String.valueOf(Jumlah1)));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_JUMLAH2, String.valueOf(Jumlah2)));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_JUMLAH3, String.valueOf(Jumlah3)));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_KETERANGAN, Keterangan));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_TIPE, Tipe));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_STATUS, Status));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_BAYAR, Bayar));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            Config.URL_ORDER1);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                } catch (ClientProtocolException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                }
                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result.equalsIgnoreCase("success")){
                    dialogs
                            .withTitle("Informasi")
                            .withMessage("Order berhasil")
                            .withDialogColor("#3da8c9")
                            .withButton1Text("OK")
                            .withEffect(Effectstype.Fall);
                    dialogs.isCancelableOnTouchOutside(true);
                    dialogs.setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogs.dismiss();
                            if(sp_name.getSelectedItem().toString().equals("Transfer Rekening")){
                                Intent intent = new Intent(Frm_order.this, Frm_invoice.class);
                                startActivity(intent);
                            }else if(sp_name.getSelectedItem().toString().equals("Tunai")){
                                Intent intent = new Intent(Frm_order.this, Frm_invoice2.class);
                                startActivity(intent);
                            }

                        }
                    });
                    dialogs.show();

                }else{
                    Toast.makeText(getApplication(),"Terjadi kesalahan Sistem",Toast.LENGTH_LONG).show();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(Nama_p,Alamat_p,Lati,Longi,No_hp,Item1,Item2,Item3,String.valueOf(Jumlah1),String.valueOf(Jumlah2),String.valueOf(Jumlah3),Keterangan,Tipe,Status,Bayar);

    }


}
