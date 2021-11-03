package com.huvle.huvleflutter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;

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
    // TODO - Huvle Library Start
    if(!checkPermission()){
      requestSapPermissions();
    }
    // TODO - Huvle Library End
  }

  @Override
  public void onResume() {
    super.onResume();
    // TODO - Huvle Library Start
    if(checkPermission()){
      Sap_Func.setNotiBarLockScreen(this, false);
      Sap_act_main_launcher.initsapStart(this, "bynetwork", true, true);
    }
    // TODO - Huvle Library End
  }

  // TODO - Huvle Library Start
  private boolean checkPermission(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      return checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    return true;
  }

  private void requestSapPermissions() {
    try{
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
    }catch (Exception ignored){
    }
  }
  // TODO - Huvle Library End
}