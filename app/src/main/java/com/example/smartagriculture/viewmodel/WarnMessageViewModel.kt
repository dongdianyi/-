package com.example.smartagriculture.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.common.base.BaseViewModel
import com.example.common.bean.BeanDataList
import com.example.common.data.BaseUrl
import com.example.common.data.CommitParam
import com.example.common.model.NoHttpRx

class WarnMessageViewModel(application: Application,
                           savedStateHandle: SavedStateHandle
) : BaseViewModel(application) {


    var total= MutableLiveData<Int>()

    fun getWarnType(){
        var commitParam= CommitParam()
        commitParam.type = "eralykind"
        var  map=HashMap<String,String>()
        map["Authorization"] = "1239461961037942784"
        noHttpRx.postHttpJson(
            map,
            "预警类型",
            BaseUrl.BASE_URL2+ BaseUrl.WARNING_TYPE_LIST,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

    fun getWarnList( beanDataList: BeanDataList,parkId:String): Unit {
        var commitParam= CommitParam()
        commitParam.parkId = parkId
        commitParam.warntype = beanDataList.id
        commitParam.page = "1"
        commitParam.pageSize = "10"
        var  map=HashMap<String,String>()
        map["Authorization"] = "1239461961037942784"
        noHttpRx.postHttpJson(
            map,
            "预警列表",
            BaseUrl.BASE_URL2+ BaseUrl.WARNING_LIST,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }
}