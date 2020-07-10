package com.example.smartagriculture.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.base.BaseFragment
import com.example.common.clickNoRepeat
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.AttendanceAdapter
import com.example.smartagriculture.databinding.FragmentAttendanceSelectBinding
import com.example.common.data.Identification
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.AttendanceViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_attendance_select.*
import kotlinx.android.synthetic.main.title_item_two.*

/**
 * A simple [Fragment] subclass.
 */
class AttendanceSelectFragment :
    BaseFragment<AttendanceViewModel, FragmentAttendanceSelectBinding>() {

    lateinit var attendanceAdapter: AttendanceAdapter

    override fun initLayout(): Int {
        return R.layout.fragment_attendance_select
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(AttendanceViewModel::class.java)
        textView.text=getString(R.string.select_details)
    }

    override fun lazyLoadData() {
        attendanceAdapter =
            AttendanceAdapter(
                requireContext(),
                R.layout.attendance_details_item,
                Identification.ATTENDANCE_MANAGER_SELECT
            )
        mLRecycleViewAdapter = LRecyclerViewAdapter(attendanceAdapter)

        var view = LayoutInflater.from(requireContext()).inflate(R.layout.attendance_details_top,
            activity?.findViewById(android.R.id.content),false)
        mLRecycleViewAdapter.addHeaderView(view)
        val dataList = listOf<String>("30", "50", "60")
        attendanceAdapter.setDataList(dataList)
        select_recycler.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.mm_2)
            .setColorResource(R.color.bg)
            .build()
        select_recycler.addItemDecoration(divider)
        select_recycler.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun setListener() {
        back.clickNoRepeat {
            nav().navigateUp()
        }
    }

}

