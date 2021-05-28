package org.gptccherthala.virtualqueue.business;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.gptccherthala.virtualqueue.R;
import org.gptccherthala.virtualqueue.user.USER_QUEUE;
import org.jetbrains.annotations.NotNull;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

import static android.Manifest.permission.VIBRATE;
import static android.Manifest.permission_group.CAMERA;


public class QRCode_Reader_of_Users extends AppCompatActivity {
    ScannerLiveView camerareaded;
    TextView readedData;
    String Uid,bid;
    DatabaseReference userRef;


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
        camerareaded=(ScannerLiveView) findViewById(R.id.camview);
        readedData=findViewById(R.id.scanned_data);

        camerareaded.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
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
                Toast.makeText(getApplicationContext(),"camera stopped due to some reasons"+err.getMessage(),Toast.LENGTH_SHORT).show();
                finish();
            }
            //for scanning
            @Override
            public void onCodeScanned(String data) {
                readedData.setText(data);
                //getting the top user using shared preferences from joinedUserQueueAdapter
                SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Uid=preferences.getString("Uid","0");
                bid=preferences.getString("Bid","0");
                System.out.println("uid"+Uid);
                //checking whether both of their are matched
                if(data.equals(Uid)){
                    Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                    userRef =  FirebaseDatabase.getInstance().getReference().child("user").child(Uid).child(bid);
                    userRef.removeValue();
                    userRef = FirebaseDatabase.getInstance().getReference().child("business").child(bid).child(Uid);
                    userRef.removeValue();
                    camerareaded.stopScanner();
                    finish();

                }


            }
        });
        
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        ZXDecoder decoder=new ZXDecoder();
        decoder.setScanAreaPercent(0.8);
        camerareaded.setDecoder(decoder);
        camerareaded.startScanner();
    }

    @Override
    protected void onPause() {
        camerareaded.stopScanner();
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