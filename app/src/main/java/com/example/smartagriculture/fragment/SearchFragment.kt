package com.example.smartagriculture.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.base.BaseFragment
import com.example.common.ToastUtil
import com.example.common.bean.BeanList
import com.example.common.data.Identification
import com.example.common.data.Identification.Companion.DEFAULT
import com.example.common.data.Identification.Companion.ONE
import com.example.common.data.Identification.Companion.PARK
import com.example.common.hideSoftKeyboard
import com.example.common.model.NoHttpRx
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.ParkAdapter
import com.example.smartagriculture.databinding.FragmentSearchBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.MainViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.search_tv
import kotlinx.android.synthetic.main.title_item_two.*


/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : BaseFragment<MainViewModel, FragmentSearchBinding>() {

    lateinit var parkAdapter: ParkAdapter
    var history = mutableListOf<String>()
    var flag:Int=DEFAULT
    override fun initLayout(): Int {
        return R.layout.fragment_search
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel=ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        textView.text=resources.getText(R.string.search)
        flag= arguments?.getInt("flag") ?: DEFAULT
    }

    override fun lazyLoadData() {
        when (flag) {
            PARK -> {
                search_tv.hint=getString(R.string.search_park)
            }
            else -> {
            }
        }
        parkAdapter =
            ParkAdapter(requireContext(), R.layout.home_item)
        mLRecycleViewAdapter = LRecyclerViewAdapter(parkAdapter)
        search_recycler.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.mm_10)
            .setColorResource(R.color.bg)
            .build()
        search_recycler.addItemDecoration(divider)
        search_recycler.layoutManager = LinearLayoutManager(requireContext())


        for (i in 0..10) {
            history.add("哈哈哈哈哈哈哈哈")
            history.add("嘿嘿嘿")
        }
//往容器内添加TextView数据

        //往容器内添加TextView数据
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(10, 10, 10, 10)
        if (flowlayout != null) {
            flowlayout.removeAllViews()
        }
        for (i in 0 until history.size) {
            val tv = TextView(requireContext())
            tv.setPadding(28, 10, 28, 10)
            tv.textSize=12f
            tv.setTextColor(Color.rgb(34,34,34))
            tv.text = history[i]
            tv.maxEms = 10
            tv.setSingleLine()
            tv.setBackgroundResource(R.drawable.cycle_gray)
            tv.layoutParams = layoutParams
            flowlayout.addView(tv, layoutParams)
            tv.setOnClickListener {
                search_tv.setText(tv.text)
                viewModel.getParks(DEFAULT, ONE,search_tv.text.toString())
            }
        }
    }

    override fun setListener() {
        search_recycler.setPullRefreshEnabled(false)
        search_recycler.setLoadMoreEnabled(false)
        back.setOnClickListener {
            hideSoftKeyboard(requireActivity())
            nav().navigateUp()
        }
        imageView15.setOnClickListener {
            search_tv.setText("")
            search_recycler.visibility= GONE
        }
        textView45.setOnClickListener {
            viewModel.noHttpRx= NoHttpRx(this)
            viewModel.getParks(DEFAULT, ONE,search_tv.text.toString())
        }

        parkAdapter.setOnWMListener { view: View, parkId: String, flag: Int ->
            //处理接口回掉
            if (Identification.WARNING == flag) {
                viewModel.toWarningMessage(view, parkId)
            }
            if (Identification.MONITOR == flag) {
                viewModel.toMonitor(view, parkId)
            }
        }

    }

    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        if ("首页列表" == flag) {
            val park = Gson().fromJson(`object`, BeanList::class.java)
            parkAdapter.setDataList(park.data)
            if (parkAdapter.itemCount!=0) {
                search_recycler.visibility=VISIBLE
            }else{
                search_recycler.visibility= GONE
            }
            search_recycler.adapter=mLRecycleViewAdapter
        }
    }

}
