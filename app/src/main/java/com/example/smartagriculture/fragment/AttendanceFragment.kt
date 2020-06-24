package com.example.smartagriculture.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.BaseFragment
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.AttendanceAdapter
import com.example.smartagriculture.databinding.FragmentAttendanceBinding
import com.example.smartagriculture.util.Identification
import com.example.smartagriculture.viewmodel.AttendanceViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import kotlinx.android.synthetic.main.attendance_manager.*
import kotlinx.android.synthetic.main.attendance_peasant.*
import kotlinx.android.synthetic.main.fragment_attendance.*

/**
 * A simple [Fragment] subclass.
 */
class AttendanceFragment : BaseFragment<AttendanceViewModel, FragmentAttendanceBinding>() {

    lateinit var attendanceAdapter: AttendanceAdapter
    override fun initLayout(): Int {
        return R.layout.fragment_attendance
    }

    override fun initView(view: View) {
        viewModel = ViewModelProvider(requireActivity()).get(AttendanceViewModel::class.java)
    }

    override fun initData() {
        include6.visibility = View.GONE
        attendanceAdapter =
            AttendanceAdapter(
                requireContext(),
                R.layout.attendance_manager_item,
                Identification.ATTENDANCE_MANAGER
            )
        mLRecycleViewAdapter = LRecyclerViewAdapter(attendanceAdapter)
        val dataList = listOf<String>("30", "50", "60")
        attendanceAdapter.setDataList(dataList)
        attendance_manager_recycler.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.mm_2)
            .setColorResource(R.color.bg)
            .build()
        attendance_manager_recycler.addItemDecoration(divider)
        attendance_manager_recycler.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun setListener() {
        textView65.setOnClickListener {
            viewModel.toAttendanceDetails(it)
        }
        textView71.setOnClickListener {
            viewModel.toLeaveApplication(it)
        }
        textView72.setOnClickListener {
            viewModel.toLeaveRecord(it, Identification.ATTENDANCE_PEASANT_LEAVE)
        }

        approval_constraint.setOnClickListener {
            viewModel.toLeaveRecord(it, Identification.ATTENDANCE_MANAGER_APPROVAL)
        }
        approved_constraint.setOnClickListener {
            viewModel.toLeaveRecord(it, Identification.ATTENDANCE_MANAGER_APPROVED)
        }
        attendanceAdapter.setOnADListener { view, position, flag ->
            viewModel.toSelect(view)
        }
    }
}
