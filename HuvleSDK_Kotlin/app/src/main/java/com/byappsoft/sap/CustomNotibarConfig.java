package com.byappsoft.sap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.byappsoft.sap.launcher.NotibarConfig;

public class CustomNotibarConfig extends NotibarConfig {

/*
    동의창 이미지 수정시 아래 주석 해제후 앱사 현황에 맞게 수정.
    동의창 내용은 언어별로 총 5가지가 제공되며 사이즈는 800(w)X650(h) 입니다.

    또는 return R.drawable.res_sap_notiba_img;
    drawable-ko, drawable-vi...... 폴더 만들어서 동일한 이름으로 언어별 셋팅
*/
//    @Override
//    public int getNotibarPopupBg() {
//        switch (Locale.getDefault().getLanguage()) {
//            case "ko":
//                return R.drawable.res_sap_notiba_img_cn;
//            case "zh":
//                return R.drawable.res_sap_notiba_img_cn;
//            case "vi":
//                return R.drawable.res_sap_notiba_img_vi;
//            case "ja":
//                return R.drawable.res_sap_notiba_img_ja;
//            default:
//                return R.drawable.res_sap_notiba_img_en;
//        }
//    }


/*
    노티바 수정시 1번~5번 아이콘까지 수정이 가능합니다.
    아래 주석 해제후 실행하시면 수정된 노티바를 확인하실 수 있습니다.
*/
//
//    public int getNotibarIcon1() { return com.example.huvlesdk_kotlin.R.drawable.res_sap_ic_action_forward; }
//    public int getNotibarString1() { return com.example.huvlesdk_kotlin.R.string.custom_noti_icon_1; }
//    public void callNotibar1(Activity activity, String nt) {
//        try{
//            Intent callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.naver.com"));
//            activity.startActivity(callIntent);
//            activity.finish();
//        }catch (Exception e){
//            showNotfoundPackagePopup(activity, nt);
//        }
//    }




//    public int getNotibarIcon2() { return com.huvle.huvlesdk.R.drawable.res_sap_ic_action_forward_dark; }
//    public int getNotibarString2() { return com.huvle.huvlesdk.R.string.custom_noti_icon_1; }
//    public void callNotibar2(Activity activity, String nt) {
//        try{
//            Intent callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.google.net"));
//            activity.startActivity(callIntent);
//            activity.finish();
//        }catch (Exception e){
//            showNotfoundPackagePopup(activity, nt);
//        }
//    }
//
//    public int getNotibarIcon3() { return com.huvle.huvlesdk.R.drawable.input_box; }
//    public int getNotibarString3() { return com.huvle.huvlesdk.R.string.custom_noti_icon_1; }
//    public void callNotibar3(Activity activity, String nt) {
//        try{
//            Intent callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.daum.net"));
//            activity.startActivity(callIntent);
//            activity.finish();
//        }catch (Exception e){
//            showNotfoundPackagePopup(activity, nt);
//        }
//    }
//
//    public int getNotibarIcon4() { return com.huvle.huvlesdk.R.drawable.input_box; }
//    public int getNotibarString4() { return com.huvle.huvlesdk.R.string.custom_noti_icon_1; }
//    public void callNotibar4(Activity activity, String nt) {
//        try{
//            Intent callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.daum.net"));
//            activity.startActivity(callIntent);
//            activity.finish();
//        }catch (Exception e){
//            showNotfoundPackagePopup(activity, nt);
//        }
//    }
//
//    public int getNotibarIcon5() { return com.huvle.huvlesdk.R.drawable.input_box; }
//    public int getNotibarString5() { return com.huvle.huvlesdk.R.string.custom_noti_icon_1; }
//    public void callNotibar5(Activity activity, String nt) {
//        try{
//            Intent callIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.daum.net"));
//            activity.startActivity(callIntent);
//            activity.finish();
//        }catch (Exception e){
//            showNotfoundPackagePopup(activity, nt);
//        }
//    }
}
