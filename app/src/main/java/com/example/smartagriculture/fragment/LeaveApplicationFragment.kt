package com.example.smartagriculture.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.common.BaseFragment

import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentLeaveApplicationBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.AttendanceViewModel
import kotlinx.android.synthetic.main.fragment_leave_application.*

/**
 * A simple [Fragment] subclass.
 */
class LeaveApplicationFragment : BaseFragment<AttendanceViewModel,FragmentLeaveApplicationBinding>() {


    override fun initLayout(): Int {
        return R.layout.fragment_leave_application
    }

    override fun initView(view: View) {
    }

    override fun initData() {
    }

    override fun setListener() {
        back.setOnClickListener {
            nav().navigateUp()
        }
    }

}
