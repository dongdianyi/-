package com.example.smartagriculture.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.BaseFragment
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.HomeAdapter
import com.example.smartagriculture.databinding.FragmentWarningListBinding
import com.example.smartagriculture.util.Identification
import com.example.smartagriculture.viewmodel.WarnMessageViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_warning_list.*

/**
 * A simple [Fragment] subclass.
 */
class WarningListFragment : BaseFragment<WarnMessageViewModel,FragmentWarningListBinding>() {


    companion object{
         fun newInstance(title: String?, flag: Int): WarningListFragment? {
            val fragment = WarningListFragment()
            val bundle = Bundle()
            bundle.putString("title", title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initLayout(): Int {
        return R.layout.fragment_warning_list
    }

    override fun initView(view: View) {
        viewModel = ViewModelProvider(requireActivity()).get(WarnMessageViewModel::class.java)
        val warnAdapter =
            HomeAdapter(requireContext(),R.layout.warning_item,Identification.WARNINGLIST)
        mLRecycleViewAdapter = LRecyclerViewAdapter(warnAdapter)
        val dataList = listOf<String>("设备警告", "设备警告2", "设备警告3")
        warnAdapter.setDataList(dataList)
        warning_recycler.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.mm_20)
            .setColorResource(R.color.bg)
            .build()
        warning_recycler.addItemDecoration(divider)
        warning_recycler.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun initData() {
    }

}
