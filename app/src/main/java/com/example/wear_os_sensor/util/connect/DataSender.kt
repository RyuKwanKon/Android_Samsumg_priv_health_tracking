package com.example.wear_os_sensor.util.connect

import android.content.Context
import java.io.IOException

interface DataSender {
    fun connect(context: Context): String
    fun searchDevice(): String
    @Throws(Exception::class)
    fun sendData(data: String)
    fun disconnect()
}