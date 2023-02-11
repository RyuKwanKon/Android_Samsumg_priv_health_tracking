package com.example.wear_os_sensor.model

import android.hardware.SensorManager
import com.example.wear_os_sensor.recyclerView.Item

//Kotlin sington 지원
object SensorModel {
    private val checkedSensorList: ArrayList<Item> = arrayListOf()
    private lateinit var availableSensorList: ArrayList<Int>

    fun setAvailableSensorList(availableSensorList: ArrayList<Int>){
        this.availableSensorList = availableSensorList
    }

    fun getAvailableSensorList(): ArrayList<Int>{
        return availableSensorList
    }

    fun getCheckedSensorList():ArrayList<Item>{
        return this.checkedSensorList
    }

}