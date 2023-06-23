package com.example.wear_os_sensor.model

import java.util.concurrent.ConcurrentLinkedQueue

object SDKSensor {
    private var checkedSensorList: ArrayList<Any> = arrayListOf()
    var ppgRedList: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
    var ppgIrList: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
    var ppgGreenList: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
    var sendList: ConcurrentLinkedQueue<String> = ConcurrentLinkedQueue()
    fun setCheckedSensorList(checkedSensorList: ArrayList<Any>){
        this.checkedSensorList = checkedSensorList
    }

    fun getCheckedSensorList(): ArrayList<Any>{
        return this.checkedSensorList
    }

}