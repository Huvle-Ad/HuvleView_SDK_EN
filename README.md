# HuvleView_SDK

## HuvleView SDK Install Guide


You can easily integrate your app with Huvle SDK by using the sample code. Huvle SDK also supports Flutter or Unity 3D development environment.
You can check the full contents of guide documents by downloading the files from the **“Download All HuvleView Sample Project”** menu below.
Huvle SDK is recommended to apply **TargetSDK 34** or more.


## Affiliate Application
We will help you know how to affiliate with HuvleView; please visit this URL. https://www.huvleview.com/doc/contact.php


### Integration Guide
- Please refer to Usages or the sample project below.
- [Download All HuvleView Sample Projects (android, flutter, unity)](https://github.com/Huvle-Ad/HuvleView_SDK_EN/archive/main.zip)


## Usages
### 1. Manifest

- Add Google AdID permission.
- if APP Taget SDK 33 higher Add POST_NOTIFICATION permission.
- [google developer](https://developer.android.com/develop/ui/views/notifications/notification-permission?hl=en)

```
<manifest>
...
    <uses-permission android:name="com.google.android.gms.permission.AD_ID" />
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS"/> 
...
</manifest>
```
- if APP Taget SDK 34 higher Specify ForegroundSERVICE TYPE in Manifest
- [google developer](https://developer.android.com/about/versions/14/changes/fgs-types-required?hl=en#use-cases)
```
<manifest>
'''
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE_SPECIAL_USE" />

    <service
        android:name="com.byappsoft.sap.service.HuvleNotiBarService"
        android:exported="true"
        android:foregroundServiceType="specialUse">
        <property android:name="android.app.PROPERTY_SPECIAL_USE_FGS_SUBTYPE"
            android:value="explanation_for_special_use"/>
    </service>
'''
</manifest>
```


- Your app always runs first, Add launchMode and clearTaskOnLaunch.
  
```

<activity
	android:name=".MainActivity"
	android:launchMode="singleInstance"
	android:clearTaskOnLaunch="true">
```

### 2. Add SDK 
Please add sub library including SDK into Gradle to use HuvleView SDK.
- build.gradle(Project)
```
allprojects {
    repositories {
        google()
        maven {
            name "Huvle"
            url "https://sdk.huvle.com/repository/internal"
        }
    }
}
```

- build.gradle(app)
```

dependencies {
	.
	.
	/**
	* huvle sdk , play-service-ads 
	*/
	implementation 'com.google.android.gms:play-services-ads-identifier:18.0.1'
	implementation 'com.byappsoft.sap:HuvleSDK:$last_version' 
	//Please contact us for details on the version.
	.
	.
}
```

- If you use Anroid Studio 4.1 (com.android.tools.build:gradle:4.1.0), please add ‘native-debug-symbols.zip’ on Automatic Generation and refer to the URL below for the lower versions.
```
buildTypes {
	...
    debug {
        ndk {
            debugSymbolLevel 'SYMBOL_TABLE'
        }
    release {
        ndk {
            debugSymbolLevel 'SYMBOL_TABLE'
        }
	...
}
```
Updating the app, how to process an Alert (warning sign) related with Native Conflict
- When you apply it like the code above, ‘project\app\build\outputs\native-debug-symbols\debug\native-debug-symbols.zip’ will be generated.
- When you update the app on Google Console, please upload the file ‘app bundle seeker > copyrights > native-debug-symbols > native-debug-symbols.zip’ of the app.
- Reference: https://developer.android.com/studio/build/shrink-code?hl=ko#native-crash-support

- Add the code below into proguard-rules.pro.
```
-keep class com.byappsoft.sap.**{*;}
-dontwarn com.byappsoft.sap.**
```

### 3. Apply to your app
- MainActivity

- Java code
```java

@Override
protected void onCreate(Bundle savedInstanceState) {
    // android OS >= 13 Post_NOTIFICATION permission check
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if(!checkPermission()){
            requestSapPermissions();
        } else { // android OS >= 14 notifications and reminders check
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                checkExactAlarm();
            }
        }
    }
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
        public void onDialogOkClicked() { 
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
```

- Kotlin code
```java
override fun onCreate(savedInstanceState: Bundle?) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (!checkPermission()) {
            requestSapPermissions()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                checkExactAlarm()
            }
        }
    }
}

override fun onResume() {
    super.onResume()
    // TODO-- huvleView apply
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (checkPermission()) {
            if (Build.VERSION.SDK_INT >= 34) {
                Sap_Func.setServiceState(this,true)
            }
            huvleView()
        }
    } else {
        huvleView()
    }
}

private fun huvleView() {
    Sap_Func.setNotiBarLockScreen(this,false)
    Sap_act_main_launcher.initsapStart(this,"bynetwork",true,true,
        object : Sap_act_main_launcher.OnLauncher {
            override fun onDialogOkClicked() {
                checkDrawOverlayPermission()
            }

            override fun onDialogCancelClicked() {
            }

            override fun onInitSapStartapp() {
            }

            override fun onUnknown() {
            }

        })
}

private fun checkExactAlarm(): Boolean {
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S) {
        return true
    }

    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val canScheduleExactAlarms = alarmManager.canScheduleExactAlarms()

    if (!canScheduleExactAlarms) {
        AlertDialog.Builder(this)
            .setTitle("Allow notifications and reminders")
            .setMessage("Please allow notification and reminder permissions.")
            .setPositiveButton("ok") { _, _ ->
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
            .setNegativeButton("cancle") { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()
        return false
    } else {
        return true
    }
}

private fun checkDrawOverlayPermission(): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return true
    }
    return if (!Settings.canDrawOverlays(this)) {
        AlertDialog.Builder(this)
            .setTitle("Display over other apps")
            .setMessage("Please allow permission to Display over other apps.")
            .setPositiveButton("ok") { dialog, which ->
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_OVERLAY_PERMISSION
                val uri = Uri.parse("package:$packageName")
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton(
                "cancle"
            ) { dialog, which -> dialog.cancel() }
            .create()
            .show()
        false
    } else {
        true
    }
}
```

- For the "bynetwork" value above, please go to _http://agent.huvle.com/_ to sign up with filling in the **Agent key**   
  the same as ID you want to register > ask for an approval of your account to huvle.  
  If you have any inqury with integration, please contact us via our website.


### 4. When you customize Notification-bar/Approval window (It is applied in the sample app, when you do not customize, the process below is not necessary.)
```
- Add com\byappsoft\sap\CustomNotibarConfig.java into your app and then change. (When you use normal mode, all comment out or do not add it.)
- Method regarding to Approval window
	getNotibarPopupBg()
- Method regarding to Notification-bar
	Notification-bar icon : getNotibarIcon1() ~ getNotibarIcon5()
	Notification-bar text : getNotibarString1() ~ getNotibarString5()
	Corresponding action : callNotibar1() ~ callNotibar5()
-When you activate Night theme, Notification bar's background color is automatically changed (It is able to be applied for Android OS 10 above)
	add textColor style ("HuvleStatusbar") to valuse folder - themes folder - thems.xml / thems.xml(night) 
	for Android Studio 4.1 below, add textColor style to values - styles folder - styles.xml / styles.xml(night)
	add layout folder - lay_sap_act_noti.xml
	apply HuvleStatusbar Style to all the portions of TextView in lay_sap_act_noti.xml
```




## License
Huvle Corporation owns the copyright on HuvleView SDK.
```
Huvle SDK Android
Copyright 2021-present Huvle Corp.

Unauthorized use, modification and redistribution of this software are strongly prohibited.
```

