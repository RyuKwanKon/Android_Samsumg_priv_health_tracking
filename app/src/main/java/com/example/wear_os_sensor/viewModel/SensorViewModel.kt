package com.example.wear_os_sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.example.wear_os_sensor.model.SensorModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SensorViewModel {
    private val sensorEventListener: SensorEventListener
    private val sensorModel: SensorModel
    private val sensorManagerUtil: SensorManagerUtil
    private val sensorListUtil: SensorListUtil = SensorListUtil()

    constructor(sensorManager: SensorManager, sensorEventListener: SensorEventListener){
        this.sensorModel = SensorModel
        this.sensorManagerUtil = SensorManagerUtil(sensorManager)
        this.sensorEventListener = sensorEventListener
    }

    fun register(): String{
        var checkedList: ArrayList<Int> = arrayListOf()
        for ((key, value ) in sensorModel.getCheckedSensorList()){
            if (value) checkedList.add(key)
        }
        val availableList: ArrayList<Int> = sensorManagerUtil.registerSensor(sensorEventListener, checkedList)
        sensorModel.setAvailableSensorList(availableList)
        return sensorModel.getAvailableSensorList().toString()
    }

    fun unRegister(){
        sensorManagerUtil.unregisterSensor(sensorEventListener, sensorModel.getAvailableSensorList())
    }

    fun sensorValue(event: SensorEvent): String{
        if (!sensorModel.getAvailableSensorList().contains(event.sensor.getType())) return "not found"

        var msg: String = ""
        //센서 축이 1개일 경우
        if (event.values.size == 1)
            msg = sensorListUtil.getSensorName(event.sensor.getType()) + " " +
                        event.values[0]
        //센서가 3축 데이터일 경우
        else msg = sensorListUtil.getSensorName(event.sensor.getType()) +
                " x: " + event.values[0] +
                " y: " + event.values[1] +
                " z: " + event.values[2]

        return msg
    }
}

