package com.polda.ari.sepatu;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Frm_komplain extends AppCompatActivity {
EditText txt_nama_k, txt_nohp_k, txt_isis_k;
Button btn_submit_k;
ImageView foto_komp;
    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST_CODE = 7777;
    private static final int CAMERA_REQUEST_CODE2 = 7778;
    NiftyDialogBuilder dialogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_komplain);
        getSupportActionBar().setTitle("Komplain");
        dialogs = NiftyDialogBuilder.getInstance(this);
        txt_nama_k = (EditText) findViewById(R.id.txt_nama_pl);
        txt_nohp_k = (EditText) findViewById(R.id.txt_hp_pl);
        txt_isis_k = (EditText) findViewById(R.id.txt_isi_k);
        foto_komp = (ImageView) findViewById(R.id.imageView);
        btn_submit_k = (Button) findViewById(R.id.btnSubmmits);

        Camerapermission();

        foto_komp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });

        btn_submit_k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String image_foto = getStringImage(bitmap);
               simpanKomp(txt_nama_k.getText().toString().trim(),txt_nohp_k.getText().toString().trim(),
                       txt_isis_k.getText().toString().trim(),image_foto);
            }
        });

    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    public void simpanKomp(final String Nama_k, final String Hp_k, final String Isis_k, final String Foto){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_NAMA_K, Nama_k));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_HP_K, Hp_k));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_ISI_K, Isis_k));
                nameValuePairs.add(new BasicNameValuePair(Config.TAG_FOTO, Foto));

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            Config.URL_KOMP);
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
                            .withMessage("Komplain berhasil")
                            .withDialogColor("#3da8c9")
                            .withButton1Text("OK")
                            .withEffect(Effectstype.Fall);
                    dialogs.isCancelableOnTouchOutside(true);
                    dialogs.setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogs.dismiss();
                            Intent intent = new Intent(Frm_komplain.this, MenuApps.class);
                            startActivity(intent);
                        }
                    });
                    dialogs.show();

                }else{
                    Toast.makeText(getApplication(),"Terjadi kesalahan Sistem",Toast.LENGTH_LONG).show();
                }
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(Nama_k,Hp_k,Isis_k);
    }



    private void Camerapermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(Frm_komplain.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Frm_komplain.this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(Frm_komplain.this,
                        new String[]{Manifest.permission.CAMERA},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        switch (requestCode) {
            case(CAMERA_REQUEST_CODE) :
                if(resultCode == Activity.RESULT_OK)
                {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    foto_komp.setImageBitmap(bitmap);
                }
                break;
        }

    }


}
