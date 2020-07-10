package com.example.smartagriculture.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.base.BaseApplication
import com.example.common.base.BaseFragment
import com.example.common.bean.Bean
import com.example.common.bean.BeanList
import com.example.common.clickNoRepeat
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.HomeAdapter
import com.example.smartagriculture.databinding.FragmentNoticeBinding
import com.example.common.data.Identification
import com.example.common.data.Identification.Companion.ONE
import com.example.common.data.Identification.Companion.THREE
import com.example.common.data.Identification.Companion.TWO
import com.example.common.data.Identification.Companion.ZERO
import com.example.common.model.NoHttpRx
import com.example.common.setNbOnItemClickListener
import com.example.smartagriculture.app.MyApplication
import com.example.smartagriculture.util.MyGridLayoutManager
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.DataViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_notice.*
import kotlinx.android.synthetic.main.fragment_warning_list.*

/**
 * A simple [Fragment] subclass.
 */
class NoticeFragment : BaseFragment<DataViewModel, FragmentNoticeBinding>() {

    lateinit var noticeAdapter :HomeAdapter
    lateinit var bean:Bean
    override fun initLayout(): Int {
        return R.layout.fragment_notice
    }

    override fun initView(savedInstanceState:Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        noticeAdapter =
            HomeAdapter(requireContext(), R.layout.warning_item, Identification.NOTICE)
        mLRecycleViewAdapter = LRecyclerViewAdapter(noticeAdapter)

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.mm_20)
            .setColorResource(R.color.bg)
            .build()
        notice_recycler.addItemDecoration(divider)
        notice_recycler.layoutManager = MyGridLayoutManager(BaseApplication.context, 1, LinearLayoutManager.VERTICAL, false)

        notice_recycler.adapter = mLRecycleViewAdapter

    }

    override fun lazyLoadData() {
        viewModel.noHttpRx = NoHttpRx(this)
        viewModel.getNotice(ZERO, "1")
    }


    override fun setListener() {
        back.clickNoRepeat {
            nav().navigateUp()
        }
        mLRecycleViewAdapter.setNbOnItemClickListener { view, position ->
            var bundle= Bundle()
            bundle.putInt("flag",Identification.NOTICE)
            bundle.putSerializable("data",bean.data.data[position])
            nav(view).navigate(R.id.action_noticeFragment_to_detailsFragment,bundle)
            viewModel.getNoticeRead(bean.data.data[position].informationId.toString())
            bean.data.data[position].statu="1"
            mLRecycleViewAdapter.notifyDataSetChanged()
        }
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButton -> {
                    viewModel.getNotice(ZERO, "1")
                }
                R.id.radioButton1 -> {
                    viewModel.getNotice(ONE, "1")
                }
                R.id.radioButton2 -> {
                    viewModel.getNotice(TWO, "1")
                }
                R.id.radioButton3 -> {
                    viewModel.getNotice(THREE, "1")
                }
                else -> {
                }
            }
        }
    }

    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        when (flag) {
            "通知" -> {
                bean= Gson().fromJson(`object`,Bean::class.java)
                noticeAdapter.setDataList(bean.data.data)
                notice_recycler.adapter=mLRecycleViewAdapter
                textView23.text=bean.data.count.toString()
            }
            else -> {
            }
        }

    }

}
