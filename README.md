# HuvleView_SDK

## HuvleView SDK Install Guide
#### [Guide(KR)](guide/guide(KR).md)

You can easily integrate your app with Huvle SDK by using the sample code. Huvle SDK also supports Flutter or Unity 3D development environment.
You can check the full contents of guide documents by downloading the files from the **“Download All HuvleView Sample Project”** menu below.
Currently, the latest version of Huvle SDK is **5.0.0**.


## Affiliate Application
We will help you know how to affiliate with HuvleView; please visit this URL. https://www.huvleview.com/doc/contact.php


### Integration Guide
- Please refer to Usages or the sample project below.
- [Download All HuvleView Sample Projects (android, flutter, unity)](https://github.com/wootaeng/HuvleSDK_Guide/archive/main.zip)


## Usages
### 1. Manifest
- Add networkSecurityConfig (If you target Android 10 (API level 29) or higher, set the value of android: requestLegacyExternalStorage.
```
<uses-permission android:name="android.permission.INTERNET" />

<application
	.
	.
	android:requestLegacyExternalStorage="true"
	.
	.
	
```
- Add launchMode and clearTaskOnLaunch.
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
        jcenter()
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
	implementation 'com.google.android.gms:play-services-ads:20.4.0'
	implementation 'com.byappsoft.sap:HuvleSDK:5.0.0' 
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
+ Check the permission of onCreate.
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate( savedInstanceState );
  setContentView( R.layout.activity_main );

  if(!checkPermission()){
		requestSapPermissions();
	}
}
```

+ Add onResume
```java
@Override
public void onResume() {
	super.onResume();

	if(checkPermission()){
		Sap_Func.setNotiBarLockScreen(this, false);
		Sap_act_main_launcher.initsapStart(this, "bynetwork", true, true);
	}
}
```
Sap_act_main_launcher.initsapStart(this, “AgentKey”, true, True); You can ask Agent Key info to the network company, or if you run an independent company just enter the same ID you used to register at agent.huvle.com.

+ Add permission settings.
```java
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
```

### 4. When you customize Notification-bar/Approval window (It is applied in the sample app, when you do not customize, the process below is not necessary.)
```
- Add com\byappsoft\sap\CustomNotibarConfig.java into your app and then change. (When you use normal mode, all comment out or do not add it.)
- Method regarding to Approval window
	getNotibarPopupBg()
- Method regarding to Notification-bar
	Notification-bar icon : getNotibarIcon1() ~ getNotibarIcon5()
	Notification-bar text : getNotibarString1() ~ getNotibarString5()
	Corresponding action : callNotibar1() ~ callNotibar5()
```

[Shortcut to the previous guide page.](http://api.huvleview.com/ko/index.html)


## License
Huvle Corporation owns the copyright on HuvleView SDK.
```
Huvle SDK Android
Copyright 2021-present Huvle Corp.

Unauthorized use, modification and redistribution of this software are strongly prohibited.
```

