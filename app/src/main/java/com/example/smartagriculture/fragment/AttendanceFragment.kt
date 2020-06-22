package com.example.smartagriculture.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.common.BaseFragment
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentAttendanceBinding
import com.example.smartagriculture.viewmodel.AttendanceViewModel
import kotlinx.android.synthetic.main.attendance_peasant.*

/**
 * A simple [Fragment] subclass.
 */
class AttendanceFragment : BaseFragment<AttendanceViewModel,FragmentAttendanceBinding>() {


    override fun initLayout(): Int {
       return R.layout.fragment_attendance
    }

    override fun initView(view: View) {
        viewModel=ViewModelProvider(requireActivity()).get(AttendanceViewModel::class.java)
    }

    override fun initData() {
    }

    override fun setListener() {
        textView65.setOnClickListener {
            viewModel.toAttendanceDetails(it)
        }
        textView71.setOnClickListener {
            viewModel.toLeaveApplication(it)
        }
        textView72.setOnClickListener {
            viewModel.toLeaveRecord(it)
        }
    }
}
