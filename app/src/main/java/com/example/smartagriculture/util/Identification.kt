package com.example.smartagriculture.util

/**
 * 唯一标识
 */
interface Identification {

    companion object{

        //预警列表
        const val WARNINGLIST=0
        //库存管理
        const val STOCK=1
        const val SCREEN=11
        //产品管理
        const val PRODUCT=2

        //倒计时
        val COUNT = 30
        //定时器
        val WHATNUM = 100

        //交流
        const val CHAT=3
        //交流详情
        const val CHATDETAILS=4
        //通讯录
        const val CHATMAILLIST=5
        //创建群
        const val ADDCHAT=6
        //预警
        const val WARNING=7
        //在线监控设备
        const val MONITOR=8

        //农户打卡详情
        const val ATTENDANCE_PEASANT_CLOCK=9
        //农户请假详情
        const val ATTENDANCE_PEASANT_LEAVE=10
        //考勤管理首页
        const val ATTENDANCE_MANAGER=11
        //考勤管理待审批
        const val ATTENDANCE_MANAGER_APPROVAL=12
        //考勤管理已审批
        const val ATTENDANCE_MANAGER_APPROVED=13
        //考勤管理查询
        const val ATTENDANCE_MANAGER_SELECT=14

        //退出登录
        const val EXIT=15
    }


}