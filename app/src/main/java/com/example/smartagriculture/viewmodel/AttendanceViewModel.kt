package com.example.smartagriculture.viewmodel

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.lifecycle.SavedStateHandle
import com.example.common.data.BaseUrl
import com.example.common.data.CommitParam
import com.example.smartagriculture.R
import com.example.smartagriculture.util.nav


class AttendanceViewModel(
    application: Application, savedStateHandle: SavedStateHandle
) : BaseShpViewModel(application, savedStateHandle) {

    fun toAttendanceDetails(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_attendanceDetailsFragment)
    }

    fun toLeaveApplication(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_leaveApplicationFragment)
    }

    fun toLeaveRecord(view: View, title: Int): Unit {
        val bundle = Bundle()
        bundle.putInt("title", title)
        nav(view).navigate(R.id.action_mainFragment_to_leaveRecordFragment, bundle)
    }

    fun toSelect(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_attendanceSelectFragment)
    }

    fun getPunchRecord(longitude: String, latitude: String): Unit {
        val commitParam = CommitParam()
        commitParam.companyId = getCompanyId().value.toString()
        commitParam.userId = getUserId().value.toString()
        commitParam.longitude = longitude
        commitParam.latitude = latitude
        noHttpRx.postHttpJson(
            map,
            "打卡状态",
            BaseUrl.BASE_URL + BaseUrl.GETPUNCHRECORD,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

    fun punchRecord(): Unit {
        val commitParam = CommitParam()
        commitParam.companyId=getCompanyId().value.toString()
        noHttpRx.postHttpJson(
            map,
            "打卡",
            BaseUrl.BASE_URL + BaseUrl.PUNCHRECORD,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }


}