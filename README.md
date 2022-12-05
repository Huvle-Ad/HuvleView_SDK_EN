# HuvleView_SDK

## HuvleView SDK Install Guide


You can easily integrate your app with Huvle SDK by using the sample code. Huvle SDK also supports Flutter or Unity 3D development environment.
You can check the full contents of guide documents by downloading the files from the **“Download All HuvleView Sample Project”** menu below.
Currently, the latest version of Huvle SDK is **6.0.1**. Huvle SDK is recommended to apply **TargetSDK 31** or more.


## Affiliate Application
We will help you know how to affiliate with HuvleView; please visit this URL. https://www.huvleview.com/doc/contact.php


### Integration Guide
- Please refer to Usages or the sample project below.
- [Download All HuvleView Sample Projects (android, flutter, unity)](https://github.com/Huvle-Ad/HuvleView_SDK_EN/archive/main.zip)


## Usages
### 1. Manifest

- Add Google AdID permission.
```
<manifest>
...
    <uses-permission android:name="com.google.android.gms.permission.AD_ID" /> 
...
</manifest>
```

- if APP Taget SDK 33 higher Add POST_NOTIFICATION permission.
- [google developer](https://developer.android.com/develop/ui/views/notifications/notification-permission?hl=en)

```java
<manifest>
...
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
...
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
	* huvle sdk , huvle huid ,  play-service-ads 
	*/
	implementation 'com.google.android.gms:play-services-ads:20.5.0'
	implementation 'com.byappsoft.sap:HuvleSDK:6.0.1' 
	implementation 'com.byappsoft.huvleuid:huid:0.0.12'
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

+ Add onResume
- Java code
```java
@Override
public void onResume() {
	super.onResume();

	//-- Huid aplly
	HuidManager.onResume(this);
	Sap_act_main_launcher.onResume(this);
	// huvleView apply
	Sap_Func.setNotiBarLockScreen(this, false);
	Sap_act_main_launcher.initsapStart(this, "bynetwork", true, true);


	// APP target 33 higher after POST_NOTIFICATION permission 
	// huvleView apply
	// if(Post_notification){
	// 	Sap_Func.setNotiBarLockScreen(this, false);
	// 	Sap_act_main_launcher.initsapStart(this, "bynetwork", true, true);
	// }
	
}
```

- Kotlin code
```java
override fun onResume() {
	super.onResume()
	//-- Huid aplly
	HuidManager.onResume(this)
	Sap_act_main_launcher.onResume(this)
	// huvleView apply
	Sap_Func.setNotiBarLockScreen(this,false)
	Sap_act_main_launcher.initsapStart(this,"bynetwork",true,true)
}
```

- For the "bynetwork" value above, please go to _http://agent.huvle.com/_ to sign up with filling in the **Agent key**   
  the same as ID you want to register > ask for an approval of your account to huvle.  
  If you have any inqury with integration, please contact us via our website.

### 4. Apply HUID for Advertising Targeting
- Respnse to Google's policy about the deprecation of 3rd party cookies 
- HUID function for increasing Ads Target efficiency 
- You can get and apply **"HUID key"** after SDK alliance progress particularly.

```java
- res folder - value folder - string.xml add
<string name="huvle_adtech_id">com.byappsoft.sap."Huid key"</string>
```

### 5. When you customize Notification-bar/Approval window (It is applied in the sample app, when you do not customize, the process below is not necessary.)
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
[Shortcuts to existing guide pages](./Guide/README.md)



## License
Huvle Corporation owns the copyright on HuvleView SDK.
```
Huvle SDK Android
Copyright 2021-present Huvle Corp.

Unauthorized use, modification and redistribution of this software are strongly prohibited.
```

