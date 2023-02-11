package com.example.wear_os_sensor

class SensorListUtil {
    private val sensorName: HashMap<Int, String> = hashMapOf(
        21 to "Heart Rate",
        1 to "Accelerometer",
        4 to "GyroScope",
        9 to "Gravity",
        2 to "Magnetometer",
        15 to "Game Rotation" ,
        5 to "Light" ,
        6 to "Barometer",
        18 to "step detector",
    )

    fun getSensorName(num: Int): String? {
        return sensorName[num]
    }

    fun getSensorIDList(): MutableList<Int> {
        return sensorName.keys.toMutableList()
    }

}