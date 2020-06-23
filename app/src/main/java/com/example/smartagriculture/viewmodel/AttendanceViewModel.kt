package com.example.smartagriculture.viewmodel

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.example.common.BaseViewModel
import com.example.smartagriculture.R
import com.example.smartagriculture.util.nav

class AttendanceViewModel(application: Application) : BaseViewModel(application) {

    fun toAttendanceDetails(view:View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_attendanceDetailsFragment)
    }
    fun toLeaveApplication(view:View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_leaveApplicationFragment)
    }
    fun toLeaveRecord(view:View,title:Int): Unit {
        val bundle = Bundle()
        bundle.putInt("title", title)
        nav(view).navigate(R.id.action_mainFragment_to_leaveRecordFragment,bundle)
    }
    fun toSelect(view:View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_attendanceSelectFragment)
    }
}