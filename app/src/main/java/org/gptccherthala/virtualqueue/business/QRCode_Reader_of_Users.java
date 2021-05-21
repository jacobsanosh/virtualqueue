package org.gptccherthala.virtualqueue.business;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.gptccherthala.virtualqueue.R;
import org.jetbrains.annotations.NotNull;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

import static android.Manifest.permission.VIBRATE;
import static android.Manifest.permission_group.CAMERA;


public class QRCode_Reader_of_Users extends AppCompatActivity {
    ScannerLiveView camreaded;
    TextView readedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_reader_of_users);
        if(checkPermission()){
            Toast.makeText(getApplicationContext(),"Already allowed",Toast.LENGTH_LONG).show();
        }
        else{
            requestPermission();
        }

        //initialization
        camreaded=(ScannerLiveView) findViewById(R.id.camview);
        readedData=findViewById(R.id.scanned_data);

        camreaded.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {
                Toast.makeText(getApplicationContext(),"Scanning started",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {
                Toast.makeText(getApplicationContext(),"scanning has stopped",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerError(Throwable err) {
                Toast.makeText(getApplicationContext(),"cammera stopped due to some reasons"+err.getMessage(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeScanned(String data) {
                readedData.setText(data);
            }
        });
        
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        ZXDecoder decoder=new ZXDecoder();
        decoder.setScanAreaPercent(0.8);
        camreaded.setDecoder(decoder);
        camreaded.startScanner();
    }

    @Override
    protected void onPause() {
        camreaded.stopScanner();
        super.onPause();
    }

    private void requestPermission() {
        int PERMISSION_REQUEST_CODE=200;
        ActivityCompat.requestPermissions(this,new String[]{CAMERA,VIBRATE},PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermission() {
        int camera_permisiion= ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA);
        int vibrate_permisiion=ContextCompat.checkSelfPermission(getApplicationContext(),VIBRATE);
        return camera_permisiion== PackageManager.PERMISSION_GRANTED && vibrate_permisiion==PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0){

            int cameraaccepted= grantResults[0]=PackageManager.PERMISSION_GRANTED;
            int vibrateaccepted= grantResults[1]=PackageManager.PERMISSION_GRANTED;
            if(cameraaccepted==0 && vibrateaccepted==0){
                Toast.makeText(getApplicationContext(),"permission granted",Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(getApplicationContext(),"permission denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
}