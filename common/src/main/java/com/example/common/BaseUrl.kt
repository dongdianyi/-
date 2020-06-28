package com.example.common

interface BaseUrl {

companion object{
    const val BASE_URL="http://192.168.2.183:8888/"
    //获取园区类型列表
    const val PARK_TYPE_URL="/bus/v1/api/reportCloud/sweepCode/getMassifPick"

    //首页地块列表
    const val PARK_LIST_URL="/bus/v1/app/api/homePage/appHomePage"

    //系统通知

    const val NOTICE_NUM="/bus/v1/app/api/homePage/earlyWarningApp/"
}
}