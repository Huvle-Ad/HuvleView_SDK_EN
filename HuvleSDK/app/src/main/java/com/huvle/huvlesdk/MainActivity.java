package com.huvle.huvlesdk;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.byappsoft.sap.launcher.Sap_act_main_launcher;
import com.byappsoft.sap.utils.Sap_Func;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        // 미디어 권한 후 동의창 띄우기 반대인경우 주석처리.
//        if(!checkPermission()){
//            requestSapPermissions();
//        }

        //-- Notification On Event.
        findViewById(R.id.noti_on_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermission()){
                    if (NotificationManagerCompat.from(MainActivity.this).areNotificationsEnabled() == false) {
                        Intent intent = new Intent();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            intent.setAction( Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                            intent.putExtra("app_package", getPackageName());
                            intent.putExtra("app_uid", getApplicationInfo().uid);
                        } else {
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setData( Uri.parse("package:" + getPackageName()));
                        }
                        startActivity(intent);
                    } else {
                        Sap_Func.notiUpdate(getApplicationContext());
                    }
                }
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
        // TODO - Huvle Library
        // 동의창 후 미디어 권한 순서인 경우 아래 코드 주석처리.
//        if(checkPermission()){
//            Sap_Func.setNotiBarLockScreen(this, false);
//            Sap_act_main_launcher.initsapStart(this, "bynetwork", true, true);
//        }


        // 동의창 후 미디어 권한 순서인 경우 아래 코드 주석해제.
        Sap_Func.setNotiBarLockScreen(this, false);
        Sap_act_main_launcher.initsapStart(this, "bynetwork", true, true, new Sap_act_main_launcher.OnLauncher() {

            @Override
            public void onDialogOkClicked() {
                // 동의창 확인후 퍼미션까지 정상 허용된경우 노티바 띄우기.
                if(!checkPermission()){
                    requestSapPermissions();
                }
            }

            @Override
            public void onDialogCancelClicked() {
                // 동의창 취소후 다음 앱 접속시 동의창을 계속 띄울때는 아래코드 주석제거 할것.
                // Sap_Func.setNotibarPopState(getBaseContext(), false);
            }

            @Override
            public void onInitSapStartapp() {
                // 노티바를 띄우지 않는경우(NOTIBA : false) 미디어 권한 여부만 호출.
                if(!checkPermission()){
                    requestSapPermissions();
                }
            }
            @Override
            public void onUnknown() {}
        });

        // TODO - Huvle Library
    }

    // TODO - Huvle Library
    private boolean checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestSapPermissions() {
        try{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }catch (Exception ignored){
        }
    }
    // TODO - Huvle Library


}
