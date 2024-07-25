package com.huvle.huvlesdk;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.byappsoft.sap.launcher.Sap_act_main_launcher;
import com.byappsoft.sap.utils.Sap_Func;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        // android OS >= 13 Post_NOTIFICATION permission check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(!checkPermission()){
                requestSapPermissions();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    checkExactAlarm();
                }
            }
        }

        //-- Notification On Event.
        findViewById(R.id.noti_on_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sap_Func.notiUpdate(getApplicationContext());
            }
        });
        //-- Notification Off Event.
        findViewById(R.id.noti_off_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sap_Func.notiCancel(getApplicationContext());
            }
        });


        TextView txt = findViewById(R.id.txt);
        txt.setText("Package : "+getBaseContext().getPackageName());
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO -- HuvleView apply
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkPermission()) {
                if (Build.VERSION.SDK_INT >= 34) {
                    Sap_Func.setServiceState(this,true);
                }
                huvleView();
            }
        } else {
            huvleView();
        }
    }

    public void huvleView() {
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

    public boolean checkExactAlarm() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) {
            return true;
        }

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        boolean canScheduleExactAlarms = alarmManager.canScheduleExactAlarms();

        if (!canScheduleExactAlarms) {
            new AlertDialog.Builder(this)
                    .setTitle("Allow notifications and reminders")
                    .setMessage("Please allow notification and reminder permissions.")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                            intent.setData(Uri.parse("package:" + getPackageName()));
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (checkPermission()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    checkExactAlarm();
                }
            }
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
