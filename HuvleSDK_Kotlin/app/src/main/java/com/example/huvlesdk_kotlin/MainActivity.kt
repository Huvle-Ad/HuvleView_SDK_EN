package com.example.huvlesdk_kotlin

import android.Manifest
import android.app.AlarmManager
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.byappsoft.sap.launcher.Sap_act_main_launcher
import com.byappsoft.sap.utils.Sap_Func

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Android OS >= 13 Post_NOTIFICATION permission check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!checkPermission()) {
                requestSapPermissions()
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    checkExactAlarm()
                }
            }
        }

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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (checkPermission()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    checkExactAlarm()
                }
            }
        }
    }

    private fun checkPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else true
    }

    private fun requestSapPermissions() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) requestPermissions(
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS
                ), 0
            )
        } catch (ignored: Exception) {
        }
    }

}