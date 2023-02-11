package com.example.wear_os_sensor.util.connect

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import java.io.IOException
import java.io.OutputStream
import java.lang.reflect.Method
import java.util.*


class BluetoothDataSender: DataSender {
    private var mBluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private lateinit var pairedDvices: Set<BluetoothDevice>
    private lateinit var mBluetoothDevice: BluetoothDevice
    private lateinit var mBluetoothSocket: BluetoothSocket
    private lateinit var mOutputStream: OutputStream

    @SuppressLint("MissingPermission")
    override fun connect(context: Context): String {
        pairedDvices = mBluetoothAdapter.bondedDevices
        val connected = getParingBluetoothDevice(pairedDvices)
        if (connected.equals("error")) return "error"
        mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(connected)
        val uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")

        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(uuid)
            mBluetoothSocket.connect()
            mOutputStream = mBluetoothSocket.outputStream
            return connected
        } catch (e: IOException) {
            Log.e("Bluetooth Connection", "Error opening socket", e)
            return "error"
        }
    }

    override fun sendData(data: String): String {
        try {
            mOutputStream.write(data.toByteArray(Charsets.UTF_8))
            return data.toByteArray(Charsets.UTF_8).toString(Charsets.UTF_8)
        } catch (e: IOException) {
            Log.e("Bluetooth Connection", "Error sending data", e)
        }
        return "error"
    }

    override fun disconnect() {
        try {
            mBluetoothSocket.close()
        } catch (e: IOException) {
            Log.e("Bluetooth Connection", "Error closing socket", e)
        }
    }

    //페어링된 기기를 찾는 함수
    @SuppressLint("MissingPermission")
    fun getParingBluetoothDevice(pairedDevice: Set<BluetoothDevice>): String{
        try {
            for (bluetoothDevice: BluetoothDevice in pairedDevice)
                if(isConnected(bluetoothDevice) as Boolean)
                    return bluetoothDevice.toString()
        } catch (e: IOException) {
            //블루투스 서비스 사용불가인 경우
        }
        return "error"
    }

    // 현재 연결되어있는지 유무
    fun isConnected(device: BluetoothDevice): Any? {
        val m: Method = device.javaClass.getMethod("isConnected")
        val connected = m.invoke(device)
        return connected
    }
}
