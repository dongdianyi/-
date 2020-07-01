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
    var type:String?=null
    var parentId:String?=null
    var number:String?=null
    var startPage:String?=null


    fun toJson(commitParam: CommitParam): String {
        return Gson().toJson(commitParam);//传参转json
    }
}
