package com.example.smartagriculture.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.base.BaseFragment
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.HomeAdapter
import com.example.smartagriculture.databinding.FragmentNoticeBinding
import com.example.common.data.Identification
import com.example.common.model.NoHttpRx
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.DataViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_notice.*
import kotlinx.android.synthetic.main.fragment_warning_list.*

/**
 * A simple [Fragment] subclass.
 */
class NoticeFragment : BaseFragment<DataViewModel, FragmentNoticeBinding>() {

    var number: String = "7"
    override fun initLayout(): Int {
        return R.layout.fragment_notice
    }

    override fun initView(view: View) {
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
    }

    override fun initData() {
        viewModel.noHttpRx = NoHttpRx(this)


        viewModel.getNotice(number, "1")

        val noticeAdapter =
            HomeAdapter(requireContext(), R.layout.warning_item, Identification.NOTICE)
        mLRecycleViewAdapter = LRecyclerViewAdapter(noticeAdapter)
        val dataList = listOf<String>("通知", "通知2", "通知3")
        noticeAdapter.setDataList(dataList)
        notice_recycler.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.mm_20)
            .setColorResource(R.color.bg)
            .build()
        notice_recycler.addItemDecoration(divider)
        notice_recycler.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun setListener() {
        back.setOnClickListener {
            nav().navigateUp()
        }
    }


}
