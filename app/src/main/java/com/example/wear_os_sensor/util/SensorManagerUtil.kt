package com.example.wear_os_sensor

import android.hardware.Sensor
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class SensorManagerUtil {
    private val sensorManager: SensorManager
    constructor(sensorManager: SensorManager){
        this.sensorManager = sensorManager
    }

    fun registerSensor(listener: SensorEventListener, list: ArrayList<Int>): ArrayList<Int>{
        var sensorList: ArrayList<Int> = arrayListOf()
        for (sensor: Sensor in sensorManager.getSensorList(Sensor.TYPE_ALL)){
            //안드로이드에서 api를 열어둔 센서
            if (sensor.getType() >= Sensor.TYPE_DEVICE_PRIVATE_BASE) continue
            //장비가 센서를 지원하는지 여부
            if (sensorManager.getDefaultSensor(sensor.getType()) == null) continue
            //측정하기 원하는 센서의 여부
            if (!list.contains(sensor.getType())) continue

            sensorManager.registerListener(
                listener,
                sensorManager.getDefaultSensor(sensor.getType()),
                SensorManager.SENSOR_DELAY_NORMAL
            )
            sensorList.add(sensor.getType())
        }
        return sensorList
    }

    fun unregisterSensor(listener: SensorEventListener, list: ArrayList<Int>){
        sensorManager.unregisterListener(listener)
        list.clear()
    }
}