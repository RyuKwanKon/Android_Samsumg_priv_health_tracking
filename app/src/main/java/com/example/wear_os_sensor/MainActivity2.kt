//package com.example.wear_os_sensor
//
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.app.ActivityManager
//import android.bluetooth.*
//import android.bluetooth.le.AdvertiseCallback
//import android.bluetooth.le.AdvertiseData
//import android.bluetooth.le.AdvertiseSettings
//import android.bluetooth.le.BluetoothLeAdvertiser
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.os.ParcelUuid
//import android.util.Log
//import android.view.WindowManager
//import android.widget.TextView
//import android.widget.Toast
//import com.example.wear_os_sensor.databinding.ActivityMain2Binding
//import com.example.wear_os_sensor.model.Constant
//import com.example.wear_os_sensor.model.FileModel
//import com.example.wear_os_sensor.util.connect.FileIO
//import com.example.wear_os_sensor.util.connect.TimeProfile
//import java.util.*
//
//
//class MainActivity2 : Activity() {
//    private lateinit var binding: ActivityMain2Binding
//
//    /* Bluetooth API */
////    private lateinit var bluetoothManager: BluetoothManager
////    private var bluetoothGattServer: BluetoothGattServer? = null
////    /* Collection of notification subscribers */
////    private val registeredDevices = mutableSetOf<BluetoothDevice>()
////
////    val TAG: String = "MainActivity2"
////
////    private var bleGatt: BluetoothGatt? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMain2Binding.inflate(layoutInflater)
//
//        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
//
//        setContentView(binding.root)
//        startLocationService()
//
////        val filter = IntentFilter("com.example.wear_os_sensor.BLUETOOTH_DISCONNECTED")
////        registerReceiver(bluetoothDisconnectedReceiver, filter)
//
//        binding.stopButton.setOnClickListener {
//            super.onPause()
////            unregisterReceiver(bluetoothDisconnectedReceiver)
//            //현재 서비스를 멈춰
//            stopLocationService()
//            //intent에서 mainAcitivity를 가져와
//            val intent = Intent(this, MainActivity::class.java)
//            //그걸 실행시켜
//            startActivity(intent)
//        }
//    }
//
//    private fun isLocationServiceRunning(): Boolean {
//        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
//        if (activityManager != null) {
//            for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
//                if (SensorService::class.java.getName() == service.service.className) {
//                    if (service.foreground) {
//                        return true
//                    }
//                }
//            }
//            return false
//        }
//        return false
//    }
//
//    private fun startLocationService() {
//        if (!isLocationServiceRunning()) {
//            val intent = Intent(applicationContext, SensorService::class.java)
//            intent.action = Constant.ACTION_START_LOCATION_SERVICE
//            startService(intent)
//            updateTextStatus()
//            Toast.makeText(this, "service started", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun stopLocationService() {
//        if (isLocationServiceRunning()) {
//            val intent = Intent(applicationContext, SensorService::class.java)
//            intent.action = Constant.ACTION_STOP_LOCATION_SERVICE
//            startService(intent)
//            updateTextStatus()
//            Toast.makeText(this, "service stopped", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun updateTextStatus() {
//        if(!isLocationServiceRunning()){
//            findViewById<TextView>(R.id.status_text)?.text = "Service is Running"
//        }else{
//            findViewById<TextView>(R.id.status_text)?.text = "Service isn't Running"
//        }
//    }
//
//    private val bluetoothDisconnectedReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            if (intent.action == "com.example.wear_os_sensor.BLUETOOTH_DISCONNECTED") {
////            if (intent.action == "com.example.wear_os_sensor.BLUETOOTH_DISCONNECTED") {
//                finish() // Finish the activity
//            }
//        }
//    }
//
//}