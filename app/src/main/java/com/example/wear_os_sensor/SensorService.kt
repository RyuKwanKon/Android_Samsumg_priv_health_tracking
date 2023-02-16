package com.example.wear_os_sensor

import android.app.*
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.wear_os_sensor.model.Constant
import com.example.wear_os_sensor.util.connect.BluetoothDataSender
import com.example.wear_os_sensor.util.connect.DataSender


class SensorService: Service(), SensorEventListener {

    private lateinit var sensorViewModel: SensorViewModel
    private lateinit var dataSender: DataSender

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    fun startForground() {
        setForground()

        sensorViewModel = SensorViewModel(getSystemService(SENSOR_SERVICE) as SensorManager, this)
        // 통신방식 Bluetooth
        dataSender = BluetoothDataSender(this)

        //sensor 연결
        val msg: String = sensorViewModel.register()
        Log.v("register", msg)

        // 통신 연결
        val msg1: String = dataSender.connect(this)
        Log.v("connect", msg1)
    }

    fun stopForground(){
        sensorViewModel.unRegister()
        dataSender.disconnect()
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
        sensorViewModel.unRegister()
        dataSender.disconnect()
        stopSelf()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return
        val msg = sensorViewModel.sensorValue(event)
        dataSender.sendData(msg)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //
    }

    fun setForground(){
        val builder: NotificationCompat.Builder

        val notificationIntent = Intent(this, MainActivity2::class.java)
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