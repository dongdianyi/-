package com.example.common.data

interface BaseUrl {

companion object{

    const val BASE_URL="http://192.168.2.183:8888"

    const val BASE_URL2="http://192.168.2.182:8888"

    const val BASE_URL3="http://192.168.1.17:8888"

    //公网ip
    const val GET_IP = "http://www.3322.org/dyndns/getip"

    //获取园区类型列表
    const val PARK_TYPE_URL="/bus/v1/app/api/homePage/leftSide"

    //首页地块列表
    const val PARK_LIST_URL="/bus/v1/app/api/homePage/appHomePage"

    //系统通知

    const val NOTICE_NUM="/bus/v1/app/api/homePage/earlyWarningApp"

    //预警消息列表
    const val WARNING_LIST="/bus/v1/plan/planEarlyWarningList"

    //农资列表
    const val STOCK_LIST="/bus/v1/materiel/applist"

    //预警类型列表
    const val WARNING_TYPE_LIST="/sys/v1/user/getdict"

    //预警单个详情
    const val WARNING_DETAILS="/bus/v1/plan/planEarlyWarningDetail"

    //通知列表
    const val NOTICE_URL="/bus/v1/app/api/homePage/sysNoticeList"

    //物资类型
    const val STOCK_TYPE="/sys/v1/user/getNewdict"

    //产品列表
    const val PRODUCT_LIST="/sys/v1/sProduct/priductList"

    //通知已读未读
    const val NOTICE_STATE="/bus/v1/app/api/homePage/sysNoticeStatu"

    //常见问题
    const val PROBLEM="/sys/v1/sCommonProblem/applist"

    //天气
    const val WEATHER="/bus/v1/plan/weather"

    //个人可聊天人员 通讯录人员
    const val MAINLIST="/sys/v1/chat/chatpersonlist"

    // 通讯录群聊
    const val MAINLIST_GROUP="/sys/v1/chat/chatgrouplist"

    //个人聊天记录带最后一条记录
    const val CHATRECORD="/sys/v1/chat/chatmessageperson"
}
}