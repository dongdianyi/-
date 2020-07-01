package com.example.smartagriculture.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.base.BaseFragment
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.AttendanceAdapter
import com.example.smartagriculture.databinding.FragmentAttendanceDetailsBinding
import com.example.common.data.Identification
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.AttendanceViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_attendance_details.*

/**
 * A simple [Fragment] subclass.
 */
class AttendanceDetailsFragment : BaseFragment<AttendanceViewModel, FragmentAttendanceDetailsBinding>() {


    lateinit var attendanceAdapter:AttendanceAdapter
    override fun initLayout(): Int {
        return R.layout.fragment_attendance_details
    }

    override fun initView(view: View) {
        viewModel=ViewModelProvider(requireActivity()).get(AttendanceViewModel::class.java)
    }

    override fun initData() {
        attendanceAdapter =
            AttendanceAdapter(requireContext(), R.layout.attendance_item,
                Identification.ATTENDANCE_PEASANT_CLOCK)
        mLRecycleViewAdapter = LRecyclerViewAdapter(attendanceAdapter)
        val dataList = listOf<String>("14.23 山东济南", "13.23 山东济南", "章丘大鱼合作社")
        attendanceAdapter.setDataList(dataList)
        attendance_recycle.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.mm_2)
            .setColorResource(R.color.bg)
            .build()
        attendance_recycle.addItemDecoration(divider)
        attendance_recycle.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun setListener() {
        back.setOnClickListener {
            nav().navigateUp()
        }
    }

}
