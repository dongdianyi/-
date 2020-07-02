package com.example.smartagriculture.viewmodel

import android.app.Application
import android.view.View
import com.example.common.base.BaseViewModel
import com.example.common.bean.BeanDataList
import com.example.common.data.BaseUrl
import com.example.common.data.CommitParam
import com.example.common.data.Identification
import com.example.common.data.Identification.Companion.STOCK_PARENT
import com.example.common.data.Identification.Companion.STOCK_TYPE
import com.example.common.model.NoHttpRx
import com.example.smartagriculture.R
import com.example.smartagriculture.util.nav

class DataViewModel(application: Application) : BaseViewModel(application) {


    fun toAddProduct(view: View) {
        nav(view).navigate(R.id.action_productFragment_to_addProductFragment)
    }

    fun getWarnListDetails(id:Int): Unit {
        var commitParam= CommitParam()
        commitParam.id = id
        var  map=HashMap<String,String>()
        map["Authorization"] = "1239461961037942784"
        noHttpRx.postHttpJson(
            map,
            "预警详情",
            BaseUrl.BASE_URL2+ BaseUrl.WARNING_DETAILS,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

    fun getNotice(type: String, pageNum:String): Unit {
        var commitParam= CommitParam()
        commitParam.type = type
        commitParam.pageNum = pageNum
        commitParam.pageSize = "10"
        var  map=HashMap<String,String>()
        map["Authorization"] = "1239461961037942784"
        noHttpRx.postHttpJson(
            map,
            "通知",
            BaseUrl.BASE_URL+ BaseUrl.NOTICE_URL,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }
    fun getNoticeRead(informationId: String): Unit {
        var commitParam= CommitParam()
        commitParam.informationId = informationId
        var  map=HashMap<String,String>()
        map["Authorization"] = "1239461961037942784"
        noHttpRx.postHttpJson(
            map,
            "通知已读",
            BaseUrl.BASE_URL+ BaseUrl.NOTICE_STATE,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }
    fun getStockType(): Unit {
        val commitParam= CommitParam()
        commitParam.type = STOCK_TYPE
        commitParam.parentId = STOCK_PARENT
        var  map=HashMap<String,String>()
        map["Authorization"] = "1239461961037942784"
        noHttpRx.postHttpJson(
            map,
            "物资类型",
            BaseUrl.BASE_URL3+ BaseUrl.STOCK_TYPE,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

}