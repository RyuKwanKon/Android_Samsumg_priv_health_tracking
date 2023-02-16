package com.example.wear_os_sensor

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.wear_os_sensor.databinding.ActivityMainBinding

class MainActivity : Activity() {
    //xml과 연결
    //binding 타입 지정
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.startButton.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        binding.sensors.setOnClickListener{
            val intent = Intent(this, SensorListActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }
}