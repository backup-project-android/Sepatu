package com.polda.ari.sepatu;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Frm_invoice extends AppCompatActivity {
Button btn_ok;
TextView txt_totpesanan,txt_grand,txt_kode,txt_grand2;
public String JSON_STRING="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_invoice);
        getSupportActionBar().setTitle("Invoice");
        txt_totpesanan = (TextView) findViewById(R.id.txt_totpesanan);
        txt_grand = (TextView) findViewById(R.id.txt_grand);
        txt_kode = (TextView) findViewById(R.id.txt_kode);
        txt_grand2 = (TextView) findViewById(R.id.txt_grand2);

        /*if(Frm_order.jenis_o == "Fast Cleaning"){
            txt_bayar.setText(Frm_order.byr);
        }else if(Form_order2.jenis_o == "Deep Cleaning"){
            //Toast.makeText(getApplication(),"Data "+Frm_order3.jenis_o,Toast.LENGTH_LONG).show();
            txt_bayar.setText(Form_order2.byr);
        }else if(Frm_order3.jenis_o == "Repaint"){
            txt_bayar.setText(Frm_order3.byr);
            //Toast.makeText(getApplication(),"Data "+Frm_order3.jenis_o,Toast.LENGTH_LONG).show();
        }*/

        btn_ok = (Button) findViewById(R.id.btnOk);

        getJSON();

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanTransaksi(txt_kode.getText().toString().trim(),txt_grand2.getText().toString().trim());
            }
        });

    }


    private void showOrder(String response){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String kode = jo.getString("id_order");
                String nama = jo.getString("nama_lengkap");
                String tot_pesanan = jo.getString("tot_pesanan");
                //String alamat = jo.getString("status");
                Integer tot = Integer.parseInt(tot_pesanan);
                int grand = tot+10000;

                Locale localeID = new Locale("in", "ID");
                NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

                txt_totpesanan.setText(formatRupiah.format((double)tot));
                txt_grand.setText(formatRupiah.format((double)grand));
                txt_kode.setText(kode);
                txt_grand2.setText(""+grand);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getJSON(){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httppost = new HttpPost("http://pstiubl.com/api_sepatu/det_invoice.php");

                // Depends on your web service
                httppost.setHeader("Content-type", "application/json");

                InputStream inputStream = null;
                String result = null;
                try {
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();

                    inputStream = entity.getContent();
                    // json is UTF-8 by default
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                } catch (Exception e) {
                    // Oops
                }
                finally {
                    try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result){
                JSON_STRING=result;
                showOrder(JSON_STRING);
                //Toast.makeText(getApplicationContext(),"Data Penyakit = "+JSON_STRING,Toast.LENGTH_LONG).show();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute();
    }


    public void simpanTransaksi(final String Kode, final String Bayar){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_KODE, Kode));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_BAYAR, Bayar));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            Config.URL_TRANSAKSI);
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
                    Toast.makeText(getApplication(),"Transaksi Berhasil",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Frm_invoice.this, MenuApps.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplication(),"Terjadi kesalahan Sistem",Toast.LENGTH_LONG).show();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(Kode,Bayar);

    }


}
