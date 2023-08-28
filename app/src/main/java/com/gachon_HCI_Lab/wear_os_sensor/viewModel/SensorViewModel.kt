package com.example.wear_os_sensor

import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.gachon_HCI_Lab.wear_os_sensor.model.SDKSensor
import com.gachon_HCI_Lab.wear_os_sensor.model.SensorModel
import java.nio.ByteBuffer
import java.nio.ByteOrder

class SensorViewModel {
    private val sensorEventListener: SensorEventListener
    private val sensorModel: SensorModel
    private val sensorManagerUtil: SensorManagerUtil
    private val sensorListUtil: SensorListUtil = SensorListUtil()

    constructor(sensorManager: SensorManager, sensorEventListener: SensorEventListener) {
        this.sensorModel = SensorModel
        this.sensorManagerUtil = SensorManagerUtil(sensorManager)
        this.sensorEventListener = sensorEventListener
    }

    fun register(): String {
        var checkedList: ArrayList<Int> = arrayListOf()
        var sdkCheckedList: ArrayList<Any> = arrayListOf()
        for ((key, value) in sensorModel.getCheckedSensorList()) {
            if ((key !is Int) && value) {
                sdkCheckedList.add(key)
                continue
            }
            if (value) checkedList.add(key as Int)
        }
        if (sdkCheckedList.size > 0) SDKSensor.setCheckedSensorList(sdkCheckedList)
        val availableList: ArrayList<Int> =
            sensorManagerUtil.registerSensor(sensorEventListener, checkedList)
        sensorModel.setAvailableSensorList(availableList)
        return sensorModel.getAvailableSensorList().toString()
    }

    fun unRegister() {
        sensorManagerUtil.unregisterSensor(
            sensorEventListener,
            sensorModel.getAvailableSensorList()
        )
    }

    fun sensorValue(event: SensorEvent): ByteBuffer {
        if (!sensorModel.getAvailableSensorList().contains(event.sensor.getType()))
            return ByteBuffer.allocate(0)
        val timestamp: Long = System.currentTimeMillis()
        return getSensorData(event, timestamp)
    }

    private fun getSensorData(event: SensorEvent, timestamp: Long): ByteBuffer {
        if (event.sensor.type == 5 || event.sensor.type == 18 || event.sensor.type == 21) {
            return getOneAxisSensorData(event.sensor.type, timestamp, event.values)
        }
        return getThreeAxisSensorData(event.sensor.type, timestamp, event.values)
    }

    private fun getOneAxisSensorData(
        sensorType: Int,
        timestamp: Long,
        data: FloatArray
    ): ByteBuffer {
        val byteBuffer = ByteBuffer.allocate(16)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        byteBuffer.putInt(sensorType)
        byteBuffer.putLong(timestamp)
        byteBuffer.putFloat(data[0])
        byteBuffer.position(0)
        return byteBuffer
    }

    private fun getThreeAxisSensorData(
        sensorType: Int,
        timestamp: Long,
        data: FloatArray
    ): ByteBuffer {
        val byteBuffer = ByteBuffer.allocate(24)
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN)
        byteBuffer.putInt(sensorType)
        byteBuffer.putLong(timestamp)
        byteBuffer.putFloat(data[0])
        byteBuffer.putFloat(data[1])
        byteBuffer.putFloat(data[2])
        byteBuffer.position(0)
        return byteBuffer
    }
}

