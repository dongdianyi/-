package com.example.common

import com.google.gson.Gson
import java.io.Serializable


/**
 * Created by ddy on 2018/3/27
 * 传参数设置
 */
class CommitParam : Serializable {
    var companyId:String?=null
    var parkType:String?=null
    var parkId  :String?=null
    var query:String?=null


    fun toJson(commitParam: CommitParam): String {
        return Gson().toJson(commitParam);//传参转json
    }
}
