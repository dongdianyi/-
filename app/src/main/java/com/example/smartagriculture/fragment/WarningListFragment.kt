package com.example.smartagriculture.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.base.BaseFragment
import com.example.common.bean.Bean
import com.example.common.bean.BeanDataList
import com.example.common.bean.DataX
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.HomeAdapter
import com.example.smartagriculture.databinding.FragmentWarningListBinding
import com.example.common.data.Identification
import com.example.common.model.NoHttpRx
import com.example.common.setNbOnItemClickListener
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.WarnMessageViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_warning_list.*

/**
 * A simple [Fragment] subclass.
 */
class WarningListFragment : BaseFragment<WarnMessageViewModel, FragmentWarningListBinding>() {

    lateinit var warnData:MutableList<DataX>
    lateinit var warnAdapter:HomeAdapter
    lateinit var beanDataList:BeanDataList
    lateinit var bean:Bean
    companion object{
         fun newInstance(title: BeanDataList?, flag: Int,parkId:String): WarningListFragment? {
            val fragment = WarningListFragment()
            val bundle = Bundle()
            bundle.putSerializable("title", title)
            bundle.putString("parkId", parkId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initLayout(): Int {
        return R.layout.fragment_warning_list
    }

    override fun initView(view: View) {
        viewModel = ViewModelProvider(requireActivity()).get(WarnMessageViewModel::class.java)
        dataBinding.data=viewModel
        beanDataList= arguments?.getSerializable("title") as BeanDataList
        var parkId= arguments?.getString("parkId").toString()

        viewModel.noHttpRx = NoHttpRx(this)

        viewModel.getWarnList(beanDataList,parkId)

        warnAdapter =
            HomeAdapter(requireContext(),R.layout.warning_item,
                Identification.WARNINGLIST)
        mLRecycleViewAdapter = LRecyclerViewAdapter(warnAdapter)
        warnData= mutableListOf()
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

    override fun setListener() {
        mLRecycleViewAdapter.setNbOnItemClickListener { view, position ->
            var bundle=Bundle()
            bundle.putInt("flag",Identification.WARNINGLIST)
            bundle.putInt("id",bean.data.data[position].id)
            nav(view).navigate(R.id.action_warningMessageFragment_to_detailsFragment,bundle)
            bean.data.data[position].isread=1
            mLRecycleViewAdapter.notifyDataSetChanged()
        }
    }

    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        bean= Gson().fromJson(`object`,Bean::class.java)
        viewModel.total.value=bean.data.total
        warnAdapter.setDataList(bean.data.data)
    }
}
