package com.example.smartagriculture.fragment

import android.os.Bundle
import android.view.Gravity.CENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.*
import com.example.common.adapter.DropDownAdapter
import com.example.common.base.BaseApplication
import com.example.common.bean.*
import com.example.common.data.BaseField
import com.example.common.model.NoHttpRx
import com.example.common.myview.MyRadioButton
import com.example.common.myview.TextDrawable
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.ParkAdapter
import com.example.smartagriculture.databinding.FragmentHomeBinding
import com.example.common.data.Identification
import com.example.common.data.Identification.Companion.ONE
import com.example.common.data.Identification.Companion.SCREEN
import com.example.common.data.Identification.Companion.STOCK
import com.example.smartagriculture.viewmodel.MainViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseDropDownFragment<MainViewModel, FragmentHomeBinding>() {

    lateinit var parkAdapter: ParkAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var radioGroup: RadioGroup
    private lateinit var collapsedView: View
    private lateinit var expandedView: View
    private lateinit var headerChevronTv: TextDrawable
    private lateinit var parkList:MutableList<BeanDataList>
    lateinit var jsonObject: JSONObject
    var query = ONE
    var standId = 0
    override fun initLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        parkList= mutableListOf<BeanDataList>()
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
        recyclerView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            BaseApplication.getHeight() / 2
        )
        radioGroup =
            expandedView.findViewById<View>(R.id.radiogroup) as RadioGroup
        headerChevronTv =
            collapsedView.findViewById<TextDrawable>(R.id.textDrawable4)


    }

    override fun lazyLoadData() {
        ARouter.getInstance().inject(this)  // Start auto inject.
        time_tv.text=viewModel.getDate()
        viewModel.noHttpRx = NoHttpRx(this)
        viewModel.onDialogGetListener = this
        viewModel.getParkType()
        viewModel.getIp()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotice()
    }
    override fun setListener() {
        home_recycler.setPullRefreshEnabled(false)
        home_recycler.setLoadMoreEnabled(false)
        search_linear.setOnClickListener {
            viewModel.toSearch(it,Identification.PARK)
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

        parkAdapter.setOnWMListener { view: View, parkId: String, flag: Int ->
            //处理接口回掉
            if (Identification.WARNING == flag) {
                viewModel.toWarningMessage(view, parkId)
            }
            if (Identification.MONITOR == flag) {
                viewModel.toMonitor(view, parkId)
            }
        }
        mLRecycleViewAdapter.setNbOnItemClickListener { view, position ->

        }
    }


    override fun toData(flag: String?, data: String?) {
        super.toData(flag, data)
        when (flag) {
            "系统通知" -> {
                val notice = Gson().fromJson(data, Bean::class.java)
                if (0 < notice.data.number) {
                    notice_num.visibility = View.VISIBLE
                    notice_num.text = notice.data.number.toString()
                } else {
                    notice_num.visibility = View.GONE
                }
            }
            "首页列表" -> {
                val park = Gson().fromJson(data, BeanList::class.java)
                parkList.clear()
                parkList .addAll(park.data)
                parkAdapter.setDataList(park.data)
                home_recycler.adapter=mLRecycleViewAdapter
            }
            "园区类型" -> {
                jsonObject = JSONObject(data)
                val data = jsonObject.getString("data")
                jsonObject = JSONObject(data)
                //通过迭代器获得json当中所有的key值
                val keys: Iterator<*> = jsonObject.keys()
                addRadioButton(keys)
            }
            "天气" -> {
                val bean=Gson().fromJson(data,Bean::class.java)
                weather_tv.text=bean.data.list[0].tem
                weather_name.text=bean.data.list[0].wea
                weather_iv.setImageBitmap(stringToBitmap(bean.data.detail.wea_img1))
            }
            "ip" -> {
                viewModel.getWeather(data?.let { replaceBlank(it) })
            }
            else -> {
            }
        }
    }

    fun addRadioButton(keys: Iterator<*>): Unit {
        var id = 0
        viewModel. parks = mutableListOf()
        var nameList = mutableListOf<String>()
        while (keys.hasNext()) {
            var name = keys.next().toString()
            var radioButton = MyRadioButton(requireContext(), null)
            radioButton.buttonDrawable = null
            radioButton.setBackgroundResource(R.drawable.expanded_bg)
            radioButton.setTextColor(
                ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.text_selector
                )
            )
            radioButton.textSize = resources.getDimension(R.dimen.mm_15)
            radioButton.text = name
            radioButton.id = id
            val lp = RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            radioButton.gravity = CENTER
            lp.setMargins(20, 20, 20, 20)
            radioButton.setPadding(10, 5, 10, 5)
            radioButton.isChecked = id == 0
            radioGroup.addView(radioButton, lp)
            nameList.add(id, jsonObject.getString(name))
            id++
        }
        var jsonArray = JSONArray(nameList[0])
        for (i in 0 until jsonArray.length()) {
            jsonObject = jsonArray.getJSONObject(i)
            var parkType = ParkType(
                jsonObject.getString("parkId"),
                jsonObject.getString("parkName"),
                jsonObject.getString("parkType"),
                jsonObject.getInt("smassifCount")
            )
            viewModel.  parks.add(parkType)
        }
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            for (i in 0 until group.childCount) {
                if (i == checkedId) {
                    viewModel. parks.clear()
                    jsonArray = JSONArray(nameList[i])
                    for (i in 0 until jsonArray.length()) {
                        jsonObject = jsonArray.getJSONObject(i)
                        var parkType = ParkType(
                            jsonObject.getString("parkId"),
                            jsonObject.getString("parkName"),
                            jsonObject.getString("parkType"),
                            jsonObject.getInt("smassifCount")
                        )
                        viewModel.  parks.add(parkType)
                        setAdapter()
                    }
                }
            }
        }
        viewModel.getParks(standId,query,"")
        setAdapter()

    }


    private fun setAdapter(): Unit {
        viewModel.selectedStandId=0
       viewModel. adapter =
            DropDownAdapter(viewModel. viewAction(drop_down_view, headerChevronTv,Identification.PARK), viewModel. parks.toMutableList())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModel. adapter
        for (i in viewModel. parks.indices) {
            viewModel. setStandStateWithId(viewModel. parks[i], i, headerChevronTv)
        }
        drop_down_view.setHeaderView(collapsedView)
        drop_down_view.setExpandedView(expandedView)
        drop_down_view.dropDownListener = viewModel. dropDownListener
    }
}

