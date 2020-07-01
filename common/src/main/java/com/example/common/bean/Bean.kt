package com.example.common.bean

import java.io.Serializable


data class Bean(
    val code: String,
    val `data`: BeanData,
    val msg: String,
    val status: String,
    val success: Boolean
)

data class BeanData(
    val number: Int,
    val total: Int,
    val `data`: List<DataX>,
    val equid: Int,
    val massifid: Long,
    val parkid: Int,
    val state: Int,
    val warnmessage: String,
    val position: String,
    val warntime: String,
    val warntype: String,
    val title: String,
    val id: Int
)

data class BeanList(
    val code: String,
    val `data`: List<BeanDataList>,
    val msg: String,
    val status: String,
    val success: Boolean
)

data class BeanDataList(
    val area: String,
    val attr: String,
    val collectionNum: Int,
    val farmingNum: Int,
    val lat: String,
    val lng: String,
    val massifId: String,
    val massifName: String,
    val parkId: String,
    val parkName: String,
    val patrolNum: Int,
    val planTaskNum: Int,
    val title: String,
    val useFertilizerNum: Int,
    val usePesticidesNum: Int,
    val usingHardwareCount: Int,
    val usingHardwareList: List<UsingHardware>,
    val warningCount: Int,
    val warningVOList: List<WarningVO>,
    val data: ParkType,
    val crop: String,
    val position: String,
    val traceState: Int,
    val id: Int,
    val label: String,
    val parentid: String,
    val remarks: String

) : Serializable

data class UsingHardware(
    val count: Int,
    val type: String,
    val typeName: String,
    val typePhoto: String
)

data class WarningVO(
    val count: Int,
    val type: String,
    val typeName: String,
    val typePhoto: String
)

class ParkType {
    lateinit var parkId: String
    lateinit var parkName: String
    lateinit var parkType: String
    var smassifCount: Int = 0
    lateinit var id: String
    lateinit var label: String

    constructor(
        parkId: String,
        parkName: String,
        parkType: String,
        smassifCount: Int
    ) {
        this.parkId = parkId
        this.parkName = parkName
        this.parkType = parkType
        this.smassifCount = smassifCount
    }

    constructor(
        id: String,
        label: String
    ) {
        this.id = id
        this.label = label
    }
}

data class DataX(
    val equid: Int,
    val id: Int,
    val massifid: Long,
    val parkid: Int,
    val state: Int,
    val warnmessage: String,
    val warntime: String,
    val warntype: String,
    val title: String
)


data class a(
    val code: String,
    val `data`: List<Data>,
    val msg: String,
    val status: String,
    val success: Boolean
)

data class Data(
    val equid: Int,
    val id: Int,
    val massifid: Long,
    val parkid: Int,
    val state: Int,
    val title: String,
    val warnmessage: String,
    val warntime: String,
    val warntype: String
)

