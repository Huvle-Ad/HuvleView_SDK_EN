package com.huvle.huvlesdk.huvleflutter;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;

import com.byappsoft.sap.launcher.Sap_act_main_launcher;
import com.byappsoft.sap.utils.Sap_Func;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);
    }

    @java.lang.Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // android OS >= 13 Post_NOTIFICATION permission check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(!checkPermission()){
                requestSapPermissions();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO -- HuvleView apply
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkPermission()) {
                Sap_Func.setNotiBarLockScreen(this, false);
                Sap_act_main_launcher.initsapStart(this, "bynetwork", true, true, new Sap_act_main_launcher.OnLauncher() {

                    @Override
                    public void onDialogOkClicked() { //허블뷰 동의창 확인 후 작업
                        checkDrawOverlayPermission();
                    }

                    @Override
                    public void onDialogCancelClicked() {
                    }

                    @Override
                    public void onInitSapStartapp() {
                    }

                    @Override
                    public void onUnknown() {
                    }
                });
            }
        } else {
            Sap_Func.setNotiBarLockScreen(this, false);
            Sap_act_main_launcher.initsapStart(this, "bynetwork", true, true, new Sap_act_main_launcher.OnLauncher() {

                @Override
                public void onDialogOkClicked() { //허블뷰 동의창 확인 후 작업
                    checkDrawOverlayPermission();
                }

                @Override
                public void onDialogCancelClicked() {
                }

                @Override
                public void onInitSapStartapp() {
                }

                @Override
                public void onUnknown() {
                }
            });
        }
    }


    public boolean checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (!Settings.canDrawOverlays(this)) {
            new AlertDialog.Builder(this)
                    .setTitle("Display over other apps")
                    .setMessage("Please allow permission to Display over other apps")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                            Uri uri = Uri.parse("package:" + getPackageName());
                            intent.setData(uri);
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .create()
                    .show();
            return false;
        } else {
            return true;
        }
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestSapPermissions() {
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 0);
        }catch (Exception ignored){
        }
    }

}
