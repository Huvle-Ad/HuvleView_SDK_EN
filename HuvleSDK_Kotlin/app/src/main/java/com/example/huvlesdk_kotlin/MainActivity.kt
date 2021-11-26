package com.example.huvlesdk_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.byappsoft.sap.launcher.Sap_act_main_launcher
import com.byappsoft.sap.utils.Sap_Func

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //-- Notification On Event.
        findViewById<Button>(R.id.noti_on_btn).setOnClickListener {
            Sap_Func.notiUpdate(applicationContext)
        }
        //-- Notification Off Event.
        findViewById<Button>(R.id.noti_off_btn).setOnClickListener {
            Sap_Func.notiCancel(applicationContext)
        }

        val txt = findViewById<TextView>(R.id.txt)
        txt.text = "Package : " + baseContext.packageName

    }

    override fun onResume() {
        super.onResume()
        // huvleView apply
        Sap_Func.setNotiBarLockScreen(this,false)
        Sap_act_main_launcher.initsapStart(this,"bynetwork",true,true)
    }
}