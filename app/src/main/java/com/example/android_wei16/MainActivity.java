package com.example.android_wei16;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.audiofx.EnvironmentalReverb;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private ImageView img;
    private TextView mesg;
    private String[] permission={Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    permission,123);

        }else {
            init();
        }
    }

    public void init(){
        img=findViewById(R.id.img);
        mesg=findViewById(R.id.mesg);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {//對於權限回應後要做何處理
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
        }

    }

    public void test1(View view) {}

    public void test2(View view) {}

    public void test3(View view) {

    }

    public void test4(View view) {
        JsonArrayRequest request=new JsonArrayRequest("",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void parseJson2(JSONArray jsonArray){
        mesg.setText("");
        try {
            for (int i=0; i<jsonArray.length(); i++){
                JSONObject row = jsonArray.getJSONObject(i);
                mesg.append(row.getString("Name") + ":"
                        + row.getString("Address") + "\n");
            }
        }catch (Exception e){
            Log.v("brad", e.toString());
        }
    }

    public void test5(View view){
        WeiInputStreamRequest request=new WeiInputStreamRequest(
                Request.Method.GET,
                "https://pdfmyurl.com/?url=https://www.gamer.com.tw/",
                null,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        savePDF(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("wei",error.toString());
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
           20*1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        MainApp.queue.add(request);
    }

    private void savePDF(byte[] data) {

        File savefile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"savedPDF.pdf");
        try {
            BufferedOutputStream bout=new BufferedOutputStream(
                    new FileOutputStream(savefile)
            );
            bout.write(data);
            bout.flush();
            bout.close();
            Toast.makeText(this,"saveOK",Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Log.v("wei",e.toString());
        }



}
}
