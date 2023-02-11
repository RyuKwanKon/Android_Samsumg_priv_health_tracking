package com.example.wear_os_sensor.util.connect

import android.content.Context

interface DataSender {
    fun connect(context: Context): String
    fun sendData(data: String): String
    fun disconnect()
}