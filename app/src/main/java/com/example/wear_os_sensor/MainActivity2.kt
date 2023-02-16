package com.example.wear_os_sensor

import android.app.Activity
import android.app.ActivityManager
import android.content.Context.*
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.wear_os_sensor.databinding.ActivityMain2Binding
import com.example.wear_os_sensor.model.Constant
import com.example.wear_os_sensor.util.connect.DataSender


class MainActivity2 : Activity() {
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)

        setContentView(binding.root)

        startLocationService()

        binding.stopButton.setOnClickListener {
            stopLocationService()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun isLocationServiceRunning(): Boolean {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        if (activityManager != null) {
            for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
                if (SensorService::class.java.getName() == service.service.className) {
                    if (service.foreground) {
                        return true
                    }
                }
            }
            return false
        }
        return false
    }

    private fun startLocationService() {
        if (!isLocationServiceRunning()) {
            val intent = Intent(applicationContext, SensorService::class.java)
            intent.action = Constant.ACTION_START_LOCATION_SERVICE
            startService(intent)
            updateTextStatus()
            Toast.makeText(this, "service started", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopLocationService() {
        if (isLocationServiceRunning()) {
            val intent = Intent(applicationContext, SensorService::class.java)
            intent.action = Constant.ACTION_STOP_LOCATION_SERVICE
            startService(intent)
            updateTextStatus()
            Toast.makeText(this, "service stopped", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTextStatus() {
        if(isLocationServiceRunning()){
            findViewById<TextView>(R.id.status_text)?.text = "Service is Running"
        }else{
            findViewById<TextView>(R.id.status_text)?.text = "Service isn't Running"
        }
    }

}