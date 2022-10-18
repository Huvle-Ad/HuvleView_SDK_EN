package com.huvle.huvlesdk;

import android.os.Bundle;
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
        //-- HuvleView apply
        Sap_Func.setNotiBarLockScreen(this, false);
        Sap_act_main_launcher.initsapStart(this, "bynetwork", true, true);
    }

}
