package com.example.common.data

import com.google.gson.Gson
import java.io.Serializable


/**
 * Created by ddy on 2018/3/27
 * 传参数设置
 */
class CommitParam : Serializable {
    lateinit var pageSize: String
    var page: String?=null
    var warntype: Int = 0
    var id: Int = 0
    var companyId:String?=null
    var parkType:String?=null
    var parkId  :String?=null
    var query:String?=null
    var massifName:String?=null
    var type:String?=null
    var parentId:String?=null
    var number:String?=null
    var pageNum:String?=null
    var materialsTypeId:String?=null
    var productName:String?=null
    var informationId:String?=null
    var userId:String?=null
    var name:String?=null
    var ip:String?=null


    fun toJson(commitParam: CommitParam): String {
        return Gson().toJson(commitParam);//传参转json
    }
}
