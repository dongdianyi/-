package com.example.smartagriculture.bean

data class ParkList(
    val code: String,
    val `data`: List<Data>,
    val msg: String,
    val status: String,
    val success: Boolean
)

data class Data(
    val area: String,
    val attr: String?,
    val collectionNum: Int,
    val farmingNum: Int,
    val lat: String?,
    val lng: String?,
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
    val warningVOList: List<Any>
)

data class UsingHardware(
    val count: Int,
    val type: String,
    val typeName: Any
)

data class ParkType(
    val parkId: String,
    var parkName: String,
    val parkType: String,
    val smassifCount: Int
)


