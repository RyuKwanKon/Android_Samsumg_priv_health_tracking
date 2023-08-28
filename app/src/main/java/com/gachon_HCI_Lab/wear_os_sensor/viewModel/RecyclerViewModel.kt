package com.example.wear_os_sensor

import com.gachon_HCI_Lab.wear_os_sensor.model.SensorModel
import com.gachon_HCI_Lab.wear_os_sensor.recyclerView.Item
import com.gachon_HCI_Lab.wear_os_sensor.recyclerView.SensorAdapter

class RecyclerViewModel {
    private val sensorModel: SensorModel
    private val sensorListUtil: SensorListUtil = SensorListUtil()

    constructor(sensorModel: SensorModel){
        this.sensorModel = sensorModel
    }

    fun getSensorAdapter(): SensorAdapter {
        var items = sensorModel.getCheckedSensorList()
        if (items.size > 0)
            return SensorAdapter(items)
        for (name in sensorListUtil.getSensorIDList()){
            items.add(Item(name, true))
        }
        return SensorAdapter(items)
    }
}