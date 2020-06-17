package com.example.smartagriculture.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.DropDownAdapter
import com.example.smartagriculture.adapter.ParkAdapter
import com.example.smartagriculture.databinding.FragmentHomeBinding
import com.example.smartagriculture.myview.TextDrawable
import com.example.smartagriculture.viewmodel.MainViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
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
    override fun initLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View) {

        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        parkAdapter =
            ParkAdapter(requireContext(), R.layout.home_item)
        mLRecycleViewAdapter = LRecyclerViewAdapter(parkAdapter)
        val dataList = listOf<String>("wwwwww", "dsds", "章丘大鱼合作社")
        parkAdapter.setDataList(dataList)
        home_recycler.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.dp_10)
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

        parks =
            arrayOf("全部园区", "青岛园区分区", "济南历下区园区", "淄博园区")
        viewAction(drop_down_view,headerChevronTv).selectedStand = 0
        adapter = DropDownAdapter(viewAction(drop_down_view,headerChevronTv), parks)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        for (i in parks.indices) {
            setStandStateWithId(parks[i], i,headerChevronTv)
        }
        drop_down_view.setHeaderView(collapsedView)
        drop_down_view.setExpandedView(expandedView)
        drop_down_view.dropDownListener = dropDownListener
    }

    override fun setListener() {
        home_recycler.setPullRefreshEnabled(false)

        screen.setOnClickListener {
            viewModel.showDialog(requireActivity(),viewModel.SCREEN)
        }
        stock_constraint.setOnClickListener {
            viewModel.showDialog(requireActivity(),viewModel.STOCK)
        }
        notice_constraint.setOnClickListener {
            viewModel.toNotice(it)
        }
        parkAdapter.setOnWMListener { view: View, i: Int ->
            //处理接口回掉
            viewModel.toWarningMessage(view, i)
        }
        mLRecycleViewAdapter.setOnItemClickListener { view, position ->
        }
    }


}

