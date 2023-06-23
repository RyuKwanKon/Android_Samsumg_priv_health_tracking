package com.example.wear_os_sensor

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.BatteryManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import com.example.wear_os_sensor.model.Constant
import com.example.wear_os_sensor.model.SensorModel
import com.example.wear_os_sensor.util.PpgUtil
import com.example.wear_os_sensor.util.connect.BluetoothConnect
import com.example.wear_os_sensor.util.connect.DataSender
import java.io.IOException


class SensorService: Service(), SensorEventListener {

    private lateinit var sensorViewModel: SensorViewModel
    val intent = Intent("com.example.ACTION_SERVICE_STOPPED")
    val ppg = PpgUtil(this)
    private var dataSender: BluetoothConnect = BluetoothConnect

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
    fun startForground() {
        setForground()

        sensorViewModel = SensorViewModel(getSystemService(SENSOR_SERVICE) as SensorManager, this)
        // 통신방식 Bluetooth
//        dataSender = BluetoothDataSender(this)

        //sensor 연결
        val msg: String = sensorViewModel.register()

        // 통신 연결
        try{
            val msg1: String = dataSender.connect()
            Log.v("connect", msg1)
        }catch (e: IOException){
            intent.putExtra("state", "mobile Error")
            stopForground()
            return
        }

        ppg.start()
        Thread{
            while(true){
                if(SensorModel.sendData.size >= 60){
                    var sendBinary: String = ""
                    for (i in 0..59){
                        sendBinary += SensorModel.sendData.take()
                    }
                    try {
//                        Log.i("qwe", sendBinary)
                        dataSender.sendData(sendBinary)
                        Thread.sleep(500)
                    }catch (e: Exception){
//                        Log.e("qwe123", e.toString())
                        SensorModel.sendData.clear()
                        intent.putExtra("state", "mobile Error")
                        stopForground()
                    }
                }
            }
        }.start()
    }

    fun stopForground(){
        ppg.destroy()
        sensorViewModel.unRegister()
//        dataSender.disconnect()
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
        stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 서비스가 시작될 때 호출되는 메소드입니다.
        // 서비스 실행 코드 작성
        if (intent != null) {
            val action = intent.action
            if (action != null) {
                if (action == Constant.ACTION_START_LOCATION_SERVICE) {
                    startForground()
                } else if (action == Constant.ACTION_STOP_LOCATION_SERVICE) {
                    stopForground()
                }
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        ppg.destroy()
//        dataSender.disconnect()
        sensorViewModel.unRegister()
        // Service에서 LocalBroadcastManager를 사용하여 종료 메시지를 보냅니다.
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
        stopSelf()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return
        val msg = sensorViewModel.sensorValue(event)
        Thread{
            SensorModel.sendData.offer(msg)
        }.start()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //
    }

    fun setForground(){
        val builder: NotificationCompat.Builder

        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        builder = if (Build.VERSION.SDK_INT >= 26) {
            val CHANNEL_ID = "measuring_service_channel"
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Measuring Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(channel)
            NotificationCompat.Builder(this, CHANNEL_ID)
        } else {
            NotificationCompat.Builder(this)
        }

        builder.setContentTitle("측정시작됨")
            .setContentIntent(pendingIntent)

        startForeground(1, builder.build())
    }

}