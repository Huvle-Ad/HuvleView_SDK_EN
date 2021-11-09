# HuvleView_SDK

## 허블뷰 (Huvle) SDK Install Guide

Huvle SDK의 연동 방식은 Gradle을 이용한 방법으로 샘플 예제를 이용해 간단하게 연동이 가능합니다.
또한 Flutter와 Unity 3D에서도 연동이 가능합니다. 현재 Huvle SDK 최신버전은 **5.0.0** 입니다.
아래 가이드 문서 내용은 본 문서 적용가이드의 **"모든 허블뷰 샘플 프로젝트 다운로드"** 하시면 모든 내용을 보실 수 있습니다.



## 제휴 신청
허블뷰 (Huvle) SDK 제휴 방법은 https://www.huvleview.com/doc/contact.php 에 절차를 안내 드리고 있습니다.


### 적용가이드
- Usages 를 참고하시거나 아래 샘플 프로젝트를 참고해주세요.
- [모든 샘플 프로젝트 다운로드(android,flutter,unity)](https://github.com/wootaeng/HuvleSDK_Guide/archive/main.zip)


## Usages
### 1. Manifest
- networkSecurityConfig 추가(Android 10(API 레벨 29) 이상을 타켓팅하는 경우 requestLegacyExternalStorage추가)
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
        tools:ignore="ScopedStorage" />

<application
	.
	.
	android:requestLegacyExternalStorage="true"
	.
	.
	
```
- 항상 귀사의 앱이 실행될 수 있도록 launchMode 및 clearTaskOnLaunch 추가
```
<activity
	android:name=".MainActivity"
	android:launchMode="singleInstance"
	android:clearTaskOnLaunch="true">
```

### 2. SDK 추가
HuvleView SDK 를 사용하기 위해서는 gradle에 SDK를 포함한 하위 라이브러리들을 추가해야합니다.
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

- Android Studio 4.1(com.android.tools.build:gradle:4.1.0)사용시 native-debug-symbols.zip자동생성 추가하고 이하버전은 아래 참조url을 참고해 주세요.
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
앱 업데이트시 네이티브 충돌 관련 워닝처리
- 위와같이 적용하시면 프로젝트\app\build\outputs\native-debug-symbols\debug\native-debug-symbols.zip 생성됩니다.
- 구글콘솔에서 앱 업데이트시 해당앱의 app bundle 탐색기 > 저작물 > 네이티브 디버그 기호 > native-debug-symbols.zip 파일 업로드
- 참조: https://developer.android.com/studio/build/shrink-code?hl=ko#native-crash-support

- proguard-rules.pro아래 코드 추가
```
-keep class com.byappsoft.sap.**{*;}
-dontwarn com.byappsoft.sap.**
```

### 3. 앱에 적용하기
- MainActivity(귀사의 MainActivity)
+ onCreate 퍼미션 확인
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

+ onResume 추가
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
Sap_act_main_launcher.initsapStart(this, "에이전트키", true, true); 에이전트 키 정보는 네트워크사에게 문의해주시거나 단독 앱사의 경우 agent.huvle.com에서 회원 가입 시 사용하셨던 아이디와 동일하게 입력하시면됩니다.

+ 권한설정 부분 추가
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

### 4. 노티바/동의창내용 커스텀시(샘플앱에 적용되어 있음, 커스텀 하지 않을경우 아래 작업은 불필요.)
```
- 귀사의 앱 내에 com\byappsoft\sap\CustomNotibarConfig.java 추가후 변경(기본모드 사용시에는 모두 주석처리 또는 추가하지 않음.)
- 동의창 관련 매소드
	getNotibarPopupBg()
- 노티바 관련 매소드
	노티바 아이콘 : getNotibarIcon1() ~ getNotibarIcon5()
	노티바 텍스트 : getNotibarString1() ~ getNotibarString5()
	해당 액션 : callNotibar1() ~ callNotibar5()
```

[이전 버전 가이드 페이지 바로가기](http://api.huvleview.com/ko/index.html)


## License
Huvle SDK 의 저작권은 (주)허블에 있습니다.
```
Huvle SDK Android
Copyright 2021-present Huvle Corp.

Unauthorized use, modification and redistribution of this software are strongly prohibited.
```

