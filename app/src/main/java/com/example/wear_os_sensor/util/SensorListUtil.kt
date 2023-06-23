package com.example.wear_os_sensor

import com.samsung.android.service.health.tracking.data.HealthTrackerType

class SensorListUtil {
    private val sensorName: HashMap<Any, String> = hashMapOf(
        21 to "Heart Rate",
//        1 to "Accelerometer",
//        4 to "GyroScope",
//        9 to "Gravity",
//        2 to "Magnetometer",
//        15 to "Game Rotation" ,
//        5 to "Light" ,
//        6 to "Barometer",
//        18 to "step detector",
        HealthTrackerType.PPG_GREEN to "ppg Green",
//        HealthTrackerType.PPG_RED to "ppg Red",
//        HealthTrackerType.PPG_IR to "ppg Ir"
    )

    fun getSensorName(num: Any): String? {
        return sensorName[num]
    }

    fun getSensorIDList(): MutableList<Any> {
        return sensorName.keys.toMutableList()
    }

}