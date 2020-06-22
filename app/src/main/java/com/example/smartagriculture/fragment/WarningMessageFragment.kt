package com.example.smartagriculture.fragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.androidkun.xtablayout.XTabLayout
import com.androidkun.xtablayout.XTabLayout.OnTabSelectedListener
import com.example.common.BaseFragment
import com.example.common.LogUtil
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.LabVpAdapter
import com.example.smartagriculture.databinding.FragmentWarningMessageBinding
import com.example.smartagriculture.util.Identification
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.WarnMessageViewModel
import kotlinx.android.synthetic.main.fragment_warning_message.*
import kotlinx.android.synthetic.main.title_item.*


/**
 * A simple [Fragment] subclass.
 */
class WarningMessageFragment : BaseFragment<WarnMessageViewModel, FragmentWarningMessageBinding>() {


    override fun initLayout(): Int {
        return R.layout.fragment_warning_message
    }

    override fun initView(view: View) {
        mTitles = listOf<String>("设备预警", "天气预警", "疾病预警", "农事预警")
        LogUtil("传递过来的参数", arguments?.getInt("position"))
        viewModel = ViewModelProvider(requireActivity()).get(WarnMessageViewModel::class.java)
        dataBinding.data = viewModel
        val mLabVpAdapter = LabVpAdapter(childFragmentManager, mTitles, Identification.WARNINGLIST)
        view_pager.adapter = mLabVpAdapter
        task_tab.setupWithViewPager(view_pager)
        for (index in mTitles.indices) {
            task_tab.getTabAt(index)?.customView = getTabView(index)
        }
    }


    override fun initData() {
    }

    override fun setListener() {
        task_tab.run {
            setOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: XTabLayout.Tab) {
                    changeTabSelect(tab,view_pager)
                }

                override fun onTabUnselected(tab: XTabLayout.Tab) {
                    changeTabNormal(tab)
                }

                override fun onTabReselected(tab: XTabLayout.Tab) {}
            })
            back.setOnClickListener {
                nav().navigateUp()

            }
        }
    }




}


