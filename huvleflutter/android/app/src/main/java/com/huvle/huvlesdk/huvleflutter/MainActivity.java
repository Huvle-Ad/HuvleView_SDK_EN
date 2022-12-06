package com.huvle.huvlesdk.huvleflutter;

import androidx.annotation.NonNull;

import com.byappsoft.huvleuid.HuidManager;
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

    }

    @Override
    public void onResume() {
        super.onResume();
        //-- Huid aplly
        HuidManager.onResume(this);
        Sap_act_main_launcher.onResume(this);
        // TODO - Huvle Library Start
        Sap_Func.setNotiBarLockScreen(this, false);
        Sap_act_main_launcher.initsapStart(this, "bynetwork", true, true);
        // TODO - Huvle Library End
    }

    @Override
    protected void onStop() {
        super.onStop();
        // TODO -- Huid
        HuidManager.onStop(this);
        Sap_act_main_launcher.onStop(this);
    }

}
