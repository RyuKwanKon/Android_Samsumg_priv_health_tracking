package com.example.wear_os_sensor.util.SDKSensor

import android.util.Log
import com.example.wear_os_sensor.model.SensorModel
import com.samsung.android.service.health.tracking.HealthTracker
import com.samsung.android.service.health.tracking.data.DataPoint
import com.samsung.android.service.health.tracking.data.ValueKey

object PpgGreenTrackerEvent: HealthTracker.TrackerEventListener {
    val TAG: String = "ppgGreen"
    var count: Int = 0
    override fun onDataReceived(list: List<DataPoint?>) {
        if (list.size != 0) {
            Thread{
                for (dataPoint in list) {
                    SensorModel.sendData.offer("1|" + dataPoint!!.getTimestamp().toString() + "|" + dataPoint!!.getValue(ValueKey.PpgGreenSet.PPG_GREEN) + "|");
                }
            }.start()
        } else {
            Log.i(TAG, "onDataReceived List is zero")
        }
    }

    override fun onFlushCompleted() {
        Log.i(TAG, " onFlushCompleted called")
    }

    override fun onError(trackerError: HealthTracker.TrackerError) {
        Log.i(TAG, " onError called")
        if (trackerError === HealthTracker.TrackerError.PERMISSION_ERROR) {
            //
        }
        if (trackerError === HealthTracker.TrackerError.SDK_POLICY_ERROR) {
            //
        }
    }
}