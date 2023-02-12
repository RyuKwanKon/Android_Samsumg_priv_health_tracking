package com.example.wear_os_sensor

import android.Manifest.permission.BLUETOOTH_CONNECT
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Context.*
import android.content.Intent
import android.content.pm.PackageManager

import android.os.Bundle
import android.hardware.Sensor;
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager;
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.wear_os_sensor.databinding.ActivityMain2Binding
import com.example.wear_os_sensor.util.connect.BluetoothDataSender
import com.example.wear_os_sensor.util.connect.DataSender
import android.app.Activity.BLUETOOTH_SERVICE as BLUETOOTH_SERVICE1


class MainActivity2 : Activity(), SensorEventListener {
    private lateinit var binding: ActivityMain2Binding

    private lateinit var sensorViewModel: SensorViewModel
    private lateinit var dataSender: DataSender

//    private var bluetoothManager: BluetoothManager? = null
//    private lateinit var mBluetoothAdapter: BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)

//        bluetoothManager = this.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
//        mBluetoothAdapter = bluetoothManager!!.getAdapter()

        sensorViewModel = SensorViewModel(getSystemService(SENSOR_SERVICE) as SensorManager, this)
        // 통신방식 Bluetooth
        dataSender = BluetoothDataSender(this)
        setContentView(binding.root)

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
//            != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(activity,
//                arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
//                REQUEST_BLUETOOTH_CONNECT);
//        }


        // 통신 연결
        val msg1: String = dataSender.connect( this)
        Log.d("connect", msg1)

        //sensor 연결
        val msg: String = sensorViewModel.register()
        Log.d("register", msg)

        binding.stopButton.setOnClickListener {
            sensorViewModel.unRegister()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorViewModel.unRegister()
        dataSender.disconnect()
    }

    //센서 값이 변경되면 호출
    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return
        val msg = sensorViewModel.sensorValue(event)
        val isOk = dataSender.sendData(msg)
        Log.d("", isOk)

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //
    }
}