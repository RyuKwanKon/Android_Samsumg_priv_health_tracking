package com.example.wear_os_sensor

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.wear_os_sensor.model.Constant

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        if(isLocationServiceRunning()){
            // 프래그먼트 매니저를 가져옵니다.
            val fragmentManager: FragmentManager = supportFragmentManager

            // 프래그먼트 트랜잭션을 시작합니다.
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

            // 초기 프래그먼트를 추가합니다.
            val connectFragment = ConnectFragment()

            fragmentTransaction.replace(R.id.fragment_container, connectFragment)
            // 트랜잭션을 커밋합니다.
            fragmentTransaction.commit()
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
}
