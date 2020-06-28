package com.example.smartagriculture.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.BaseField
import com.example.common.BaseUrl
import com.example.common.CommitParam
import com.example.common.model.NoHttpRx
import com.example.common.setNbOnItemClickListener
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.DropDownAdapter
import com.example.smartagriculture.adapter.ParkAdapter
import com.example.smartagriculture.bean.Data
import com.example.smartagriculture.bean.Notice
import com.example.smartagriculture.bean.ParkList
import com.example.smartagriculture.databinding.FragmentHomeBinding
import com.example.smartagriculture.myview.TextDrawable
import com.example.smartagriculture.util.Identification
import com.example.smartagriculture.util.Identification.Companion.SCREEN
import com.example.smartagriculture.util.Identification.Companion.STOCK
import com.example.smartagriculture.viewmodel.MainViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseDropDownFragment<MainViewModel, FragmentHomeBinding>() {

    lateinit var parkAdapter: ParkAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var collapsedView: View
    private lateinit var expandedView: View
    private lateinit var headerChevronTv: TextDrawable
    private var parkList = mutableListOf<Data>()


    lateinit var map: Map<Any, Any>
    override fun initLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View) {
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        parkAdapter =
            ParkAdapter(requireContext(), R.layout.home_item)
        mLRecycleViewAdapter = LRecyclerViewAdapter(parkAdapter)
        home_recycler.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.mm_10)
            .setColorResource(R.color.bg)
            .build()
        home_recycler.addItemDecoration(divider)
        home_recycler.layoutManager = LinearLayoutManager(requireContext())

        collapsedView =
            LayoutInflater.from(context).inflate(R.layout.dropview_header, null, false)
        expandedView =
            LayoutInflater.from(context).inflate(R.layout.dropview_expanded, null, false)

        recyclerView =
            expandedView.findViewById<View>(R.id.recyclerView) as RecyclerView
        headerChevronTv =
            collapsedView.findViewById<TextDrawable>(R.id.textDrawable4)


    }

    override fun initData() {
        ARouter.getInstance().inject(this)  // Start auto inject.
        parks =
            arrayOf("全部园区", "青岛园区分区", "济南历下区园区", "淄博园区")
        viewAction(drop_down_view, headerChevronTv).selectedStand = 0
        adapter = DropDownAdapter(viewAction(drop_down_view, headerChevronTv), parks)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        for (i in parks.indices) {
            setStandStateWithId(parks[i], i, headerChevronTv)
        }
        drop_down_view.setHeaderView(collapsedView)
        drop_down_view.setExpandedView(expandedView)
        drop_down_view.dropDownListener = dropDownListener

        map = HashMap<Any, Any>()
        map = HashMap<Any, Any>()
        var commitParam = CommitParam()
        commitParam.companyId = "1"
        commitParam.parkType = "212"
        commitParam.parkId = "1"
        commitParam.query = "1"
        val noHttpRx = NoHttpRx(this)
//        noHttpRx.getHttp("token", "预警个数", BaseUrl.NOTICE_NUM, commitParam.toJson(commitParam), this)
        noHttpRx.postHttpJson(
            "token",
            "首页列表",
            BaseUrl.PARK_LIST_URL,
            commitParam.toJson(commitParam),
            this
        )
    }

    override fun setListener() {
        home_recycler.setPullRefreshEnabled(false)
        search_linear.setOnClickListener {
            viewModel.toSearch(it)
        }
        screen.setOnClickListener {
            viewModel.showDialog(requireActivity(), SCREEN)
        }
        stock_constraint.setOnClickListener {
            viewModel.showDialog(requireActivity(), STOCK)
        }
        notice_constraint.setOnClickListener {
            viewModel.toNotice(it)
        }
        monitor_btn.setOnClickListener {
//          1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
            ARouter.getInstance().build(BaseField.MONITOR_PATH).navigation()
//          2. 跳转并携带参数
//            ARouter.getInstance().build("/library/activity")
//                .withLong("key1", 666L)
//                .withString("key3", "888")
//                .navigation()
        }
        housekeeper_btn.setOnClickListener {
            ARouter.getInstance().build(BaseField.HOUSEKEEPER_PATH).navigation()
        }
        report_from_btn.setOnClickListener {
            ARouter.getInstance().build(BaseField.REPORT_FORM_PATH).navigation()
        }
        retrospect_btn.setOnClickListener {
            ARouter.getInstance().build(BaseField.RETROSPECT_PATH).navigation()
        }

        weather_linear.setOnClickListener {
            viewModel.toWeather(it)
        }

        parkAdapter.setOnWMListener { view: View, i: Int, flag: Int ->
            //处理接口回掉
            if (Identification.WARNING == flag) {
                viewModel.toWarningMessage(view, i)
            }
            if (Identification.MONITOR == flag) {
                viewModel.toMonitor(view, i)
            }
        }
        mLRecycleViewAdapter.setNbOnItemClickListener { view, position ->

        }
    }


    override fun toData(flag: String?, data: String?) {
        super.toData(flag, data)
        if ("预警个数" == flag) {
            val notice = Gson().fromJson(data, Notice::class.java)
            if (0 < notice.data) {
                notice_num.visibility = View.VISIBLE
                notice_num.text = notice.data.toString()
            } else {
                notice_num.visibility = View.GONE
            }
        }
        if ("首页列表" == flag) {
            val park=Gson().fromJson(data,ParkList::class.java)
            parkList= park.data.toMutableList()
            parkAdapter.setDataList(parkList as Collection<Any?>?)
        }
    }

}

