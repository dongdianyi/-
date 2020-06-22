package com.example.smartagriculture.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.BaseFragment
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.AttendanceAdapter
import com.example.smartagriculture.databinding.FragmentLeaveRecordBinding
import com.example.smartagriculture.util.Identification
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.AttendanceViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_leave_record.*

/**
 * A simple [Fragment] subclass.
 */
class LeaveRecordFragment : BaseFragment<AttendanceViewModel,FragmentLeaveRecordBinding>() {

    lateinit var attendanceAdapter:AttendanceAdapter

    override fun initLayout(): Int {
        return R.layout.fragment_leave_record
    }

    override fun initView(view: View) {
        viewModel= ViewModelProvider(requireActivity()).get(AttendanceViewModel::class.java)

    }

    override fun initData() {
        attendanceAdapter =
            AttendanceAdapter(requireContext(), R.layout.leave_record_item, Identification.ATTENDANCE_PEASANT_LEAVE)
        mLRecycleViewAdapter = LRecyclerViewAdapter(attendanceAdapter)
        val dataList = listOf<String>("请假一天", "请假五天", "请假10天")
        attendanceAdapter.setDataList(dataList)
        leave_record_recycler.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.dp_10)
            .setColorResource(R.color.bg)
            .build()
        leave_record_recycler.addItemDecoration(divider)
        leave_record_recycler.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun setListener() {
        back.setOnClickListener {
            nav().navigateUp()
        }
    }

}
