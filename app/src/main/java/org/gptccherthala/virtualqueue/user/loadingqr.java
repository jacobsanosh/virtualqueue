package org.gptccherthala.virtualqueue.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;

import org.gptccherthala.virtualqueue.R;

public class loadingqr {
    Activity activity;
    AlertDialog dialog;

    public loadingqr(Context qractivity) {
        activity= (Activity) qractivity;
    }
    public void displayingqr(){
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        LayoutInflater inflater= activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.qrcodedisplayer,null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }
    void dismissdialog()
    {
       dialog.dismiss();
    }
}
