package org.gptccherthala.virtualqueue.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.WriterException;

import org.gptccherthala.virtualqueue.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class loadingqr {
    Activity activity;
    AlertDialog dialog;
    private ImageView img;

    public loadingqr(Context qractivity) {
        activity= (Activity) qractivity;
    }
    public void displayingqr(){
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        LayoutInflater inflater= activity.getLayoutInflater();
        //view will represent the xml in display format
        View view=inflater.inflate(R.layout.qrcodedisplayer,null);
        //using view getting the imageView
        ImageView img= view.findViewById(R.id.qrcode);
        //code for qr code
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        int smallerDimension = 350;

        QRGEncoder qrgEncoder = new QRGEncoder(userId,null, QRGContents.Type.TEXT, smallerDimension);
        try {

            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            img.setImageBitmap(bitmap);
        }
        catch (WriterException e){

        }
        builder.setView(view);
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }
    void dismissdialog()
    {
       dialog.dismiss();
    }
}
