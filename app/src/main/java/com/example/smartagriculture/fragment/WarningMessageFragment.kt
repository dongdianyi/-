package com.example.smartagriculture.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.androidkun.xtablayout.XTabLayout
import com.androidkun.xtablayout.XTabLayout.OnTabSelectedListener
import com.example.common.base.BaseFragment
import com.example.common.bean.BeanList
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.LabVpAdapter
import com.example.smartagriculture.databinding.FragmentWarningMessageBinding
import com.example.common.data.Identification
import com.example.common.model.NoHttpRx
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.WarnMessageViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_warning_message.*
import kotlinx.android.synthetic.main.title_item.*


/**
 * A simple [Fragment] subclass.
 */
class WarningMessageFragment : BaseFragment<WarnMessageViewModel, FragmentWarningMessageBinding>() {

    var parkId = ""
    override fun initLayout(): Int {
        return R.layout.fragment_warning_message
    }

    override fun initView(view: View) {
        viewModel = ViewModelProvider(requireActivity()).get(WarnMessageViewModel::class.java)
        dataBinding.data = viewModel
        parkId = arguments?.getString("parkId").toString()
        mTitleWarning= mutableListOf()
        viewModel.noHttpRx = NoHttpRx(this)

        viewModel.getWarnType()

    }


    override fun initData() {
    }

    override fun setListener() {

    }

    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        var beanList = Gson().fromJson(`object`, BeanList::class.java)
        beanList.data.forEach {
            mTitleWarning.add(it)
        }
        val mLabVpAdapter =
            LabVpAdapter(childFragmentManager, mTitleWarning, parkId,
                Identification.WARNINGLIST)
        view_pager.adapter = mLabVpAdapter
        task_tab.setupWithViewPager(view_pager)
        for (index in mTitleWarning.indices) {
            task_tab.getTabAt(index)?.customView = getTabView(index)
        }
        task_tab.run {
            setOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: XTabLayout.Tab) {
                    changeTabSelect(tab, view_pager)
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


