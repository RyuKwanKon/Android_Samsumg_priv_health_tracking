package com.example.wear_os_sensor

import android.app.Activity
import android.content.Intent

import android.os.Bundle
import java.util.*
import android.hardware.Sensor;
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager;
import android.util.Log
import com.example.wear_os_sensor.databinding.ActivityMain2Binding
import kotlin.collections.ArrayList


class MainActivity2 : Activity(), SensorEventListener {
    private lateinit var binding: ActivityMain2Binding

    private lateinit var sensorViewModel: SensorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)

        sensorViewModel = SensorViewModel(getSystemService(SENSOR_SERVICE) as SensorManager, this)
        setContentView(binding.root)

        binding.stopButton.setOnClickListener {
            sensorViewModel.unRegister()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val msg: String = sensorViewModel.register()
        Log.d("register", msg)
    }

    //센서 값이 변경되면 호출
    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return
        val msg: String = sensorViewModel.sensorValue(event)
        Log.d("heart-rate", msg)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //
    }
}