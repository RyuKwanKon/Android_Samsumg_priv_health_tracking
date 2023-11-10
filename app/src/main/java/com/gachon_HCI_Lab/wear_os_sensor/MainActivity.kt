package com.gachon_HCI_Lab.wear_os_sensor

import android.Manifest
import android.animation.ObjectAnimator
import android.app.ActivityManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.wear_os_sensor.R
import com.gachon_HCI_Lab.wear_os_sensor.util.step.Permissions
import com.gachon_HCI_Lab.wear_os_sensor.util.step.StepsReader
import com.gachon_HCI_Lab.wear_os_sensor.util.step.StepsReaderUtil
import com.google.android.libraries.healthdata.HealthDataService
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.samsung.android.sdk.healthdata.HealthPermissionManager
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private val SENSORS_PERMISSION_REQUEST_CODE = 1001
    private val fragmentManager: FragmentManager = supportFragmentManager
    private val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler())
        checkPermission()
        try {
            val healthDataClient = HealthDataService.getClient(this)
            StepsReaderUtil.addContext(this)
            StepsReader.addHealthDataClient(healthDataClient)
            Permissions.addHealthDataClietn(healthDataClient)
            StepsReaderUtil.readStepsWithPermissionsCheck()
            if (isLocationServiceRunning()) {
                // 초기 프래그먼트를 추가합니다.
                val connectFragment = ConnectFragment()
                fragmentTransaction.replace(R.id.fragment_container, connectFragment)
                // 트랜잭션을 커밋합니다.
                fragmentTransaction.commit()
            }

            if (!HealthDataService.isHealthDataApiSupported()) {
                Toast.makeText(
                    this,
                    "Health Platform not available, make sure you're on Samsung device running Android"
                            + " Watch 4 and above",
                    Toast.LENGTH_LONG
                )
                    .show();
                finish();
            }
        }catch (e: IllegalStateException){
            val connectFragment = ConnectFragment()
            fragmentTransaction.replace(R.id.fragment_container, connectFragment)
            fragmentTransaction.replace(R.id.fragment_container, ExceptFragment()).commit()
        }
    }

    private fun isLocationServiceRunning(): Boolean {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        if (activityManager != null) {
            for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
                if (SensorService::class.java.name == service.service.className) {
                    if (service.foreground) {
                        return true
                    }
                }
            }
            return false
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SENSORS_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용된 경우 실행할 작업들을 여기에 추가합니다.
                // 예: 센서 사용 시작 등
            } else {
                // 권한이 거부된 경우 처리할 작업들을 여기에 추가합니다.
                // 안드로이드는 2번 이상 거부되었을 경우 재요청을 할 수 없다.
                checkPermission()
            }
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BODY_SENSORS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 거부된 경우 권한 확인 다이얼로그를 다시 표시합니다.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.BODY_SENSORS,
                    Manifest.permission.ACTIVITY_RECOGNITION
                ),
                SENSORS_PERMISSION_REQUEST_CODE
            )
        }
    }

    inner class ExceptionHandler : Thread.UncaughtExceptionHandler {
        override fun uncaughtException(p0: Thread?, p1: Throwable?) {
            Log.d("Exception", "비정상 종료")
            p1?.printStackTrace()

//            android.os.Process.killProcess(android.os.Process.myPid())
//            exitProcess(10)
        }
    }
}
