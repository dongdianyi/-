package com.example.common.bean

import java.io.Serializable

data class Bean(
    val code: String,
    val `data`: BeanData,
    val msg: String,
    val status: String,
    val success: Boolean
)

data class ProductGradeNote (
    val name:String,
    val note:String
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
    val groupName: String,
    val groupId: Int,
    val id: Int,
    val count: Int,
    val rows: List<Row>,
    val detail: Detail,
    val list: List<Weather>,
    val userid: String,
    val companyid: String,
    val usermsg: UserMsg,
    val Cookie: String,
    val type: Int,
    val btnContent:String,
    val msg:String,
    val mobile:String,
    val photo:String,
    val userName:String,
    val have:List<Have>



)
data class Have(
    val userName: String,
    val userid: String
)

data class UserMsg (
    val telephone:String?,
    val userName:String?,
    val photo:String?
)

data class BeanList(
    val code: String,
    val `data`: List<BeanDataList>,
    val msg: String,
    val status: String,
    val success: Boolean,
    val count: Int
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
    val id: Long,
    val label: String,
    val parentid: String,
    val name: String,
    val stock: String,
    val unitName: String,
    val creattime: String,
    val content: String,
    val statu: String,
    val question: String,
    val answer: String?,
    val remarks: String?,
    val userName: String,
    val category: String,
    val chatContent: String,
    val chatTime: String,
    val chatUser: String,
    val sendname: String,
    val userId: String,
    var isChoose: Boolean =false



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
        parkName: String
    ) {
        this.id = id
        this.parkName = parkName
    }
}

data class DataX(
    val equid: Int,
    val id: Int,
    val massifid: Long,
    val parkid: Int,
    var isread: Int,
    val warnmessage: String,
    val warntime: String,
    val warntype: String,
    val title: String,
    val content: String,
    val creattime: String,
    val deleteState: String,
    val sendUserName: String,
    var statu: String,
    val userid: String,
    val source: String,
    val informationId: Int
) : Serializable

data class BeanType(
    val code: String,
    val `data`: List<ParkType>,
    val msg: String,
    val status: String,
    val success: Boolean
)

data class Row(
    val createDate: String,
    val deleteState: Int,
    val massifId: String,
    val productGradeNote: List<ProductGradeNote>?,
    val productId: Int,
    val productLeader: String,
    val productName: String,
    val productPictureUrl: String,
    val productQuarter: String,
    val varieties: String
)

data class IP(
    val ip:String,
    val address:String
)

data class Detail(
    val air_level: String,
    val city: String,
    val humidity: String,
    val pressure: String,
    val tem: String,
    val win_speed: String,
    val wea_img1: String
)

data class Weather (
    val air: Int,
    val air_level: String,
    val date: String,
    val day: String,
    val hours: List<Hour>,
    val humidity: Int,
    val tem: String,
    val tem1: String,
    val tem2: String,
    val wea: String,
    val wea_img: String,
    val week: String,
    val win: List<String>,
    val win_speed: String
)

data class Hour(
    val day: String,
    val tem: String,
    val wea: String,
    val win: String,
    val win_speed: String
)