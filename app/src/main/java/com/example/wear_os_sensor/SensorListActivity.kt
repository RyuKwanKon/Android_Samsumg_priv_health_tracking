package com.example.wear_os_sensor

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.wear_os_sensor.databinding.ActivitySensorListBinding
import com.example.wear_os_sensor.model.SensorModel

class SensorListActivity : Activity() {
    private lateinit var binding: ActivitySensorListBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewModel: RecyclerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySensorListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerViewModel = RecyclerViewModel(SensorModel)

        recyclerView = binding.sensorRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = recyclerViewModel.getSensorAdapter()
    }
}