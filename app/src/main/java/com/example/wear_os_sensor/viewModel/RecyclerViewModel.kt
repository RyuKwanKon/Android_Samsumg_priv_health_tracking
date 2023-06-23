package com.example.wear_os_sensor

import com.example.wear_os_sensor.model.SensorModel
import com.example.wear_os_sensor.recyclerView.Item
import com.example.wear_os_sensor.recyclerView.SensorAdapter

class RecyclerViewModel {
    private val sensorModel: SensorModel
    private val sensorListUtil: SensorListUtil = SensorListUtil()

    constructor(sensorModel: SensorModel){
        this.sensorModel = sensorModel
    }

    fun getSensorAdapter(): SensorAdapter {
        var items = sensorModel.getCheckedSensorList()

        //이미 센서값을 저장한적이 있다면
        //추후 데이터베이스에서 받아오는 부분
        if (items.size > 0) return SensorAdapter(items)

        for (name in sensorListUtil.getSensorIDList()){
            items.add(Item(name, false))
        }
        return SensorAdapter(items)
    }
}