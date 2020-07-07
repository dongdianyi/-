package com.example.smartagriculture.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common.adapter.DropDownAdapter
import com.example.common.bean.BeanList
import com.example.common.bean.BeanType
import com.example.common.bean.ParkType
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.HomeAdapter
import com.example.smartagriculture.databinding.FragmentStockBinding
import com.example.common.myview.TextDrawable
import com.example.common.data.Identification
import com.example.common.model.NoHttpRx
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.DataViewModel
import com.github.jdsjlzx.ItemDecoration.GridItemDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_stock.*
import kotlinx.android.synthetic.main.title_item.*

/**
 * A simple [Fragment] subclass.
 */
class StockFragment : BaseDropDownFragment<DataViewModel, FragmentStockBinding>() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var collapsedView: View
    private lateinit var expandedView: View
    private lateinit var headerChevronTv: TextDrawable

    private lateinit var stockAdapter: HomeAdapter
    var standId = 0

    override fun initLayout(): Int {
        return R.layout.fragment_stock
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        textView.text = getString(R.string.stock_manage)
        viewModel.parks =
            mutableListOf()
        viewModel.noHttpRx = NoHttpRx(this)
        viewModel.getStockType()

        collapsedView =
            LayoutInflater.from(context)
                .inflate(R.layout.dropview_header_stock, null, false)

        headerChevronTv =
            collapsedView.findViewById<TextDrawable>(R.id.textDrawable8)


        expandedView = LayoutInflater.from(context).inflate(R.layout.dropview_expanded, null, false)

        recyclerView =
            expandedView.findViewById<View>(R.id.recyclerView) as RecyclerView

    }

    override fun lazyLoadData() {

        var manager = GridLayoutManager(requireContext(), 3)
        stock_recycler.layoutManager = manager

        stockAdapter =
            HomeAdapter(requireContext(), R.layout.stock_item, Identification.STOCK)
        mLRecycleViewAdapter = LRecyclerViewAdapter(stockAdapter)
        stock_recycler.adapter = mLRecycleViewAdapter

//        val spacing = resources.getDimensionPixelSize(R.dimen.mm_4)
//        stock_recycler.addItemDecoration(
//            SpacesItemDecoration.newInstance(
//                spacing,
//                spacing,
//                manager.spanCount,
//                Color.GRAY
//            )
//        )
        //根据需要选择使用GridItemDecoration还是SpacesItemDecoration

        //根据需要选择使用GridItemDecoration还是SpacesItemDecoration
        val divider: GridItemDecoration = GridItemDecoration.Builder(requireContext())
            .setHorizontal(R.dimen.mm_20)
            .setVertical(R.dimen.mm_20)
            .setColorResource(R.color.white)
            .build()
        stock_recycler.addItemDecoration(divider)


    }

    override fun setListener() {
        stock_recycler.setPullRefreshEnabled(false)
        back.setOnClickListener {
            nav().navigateUp()
        }
    }

    override fun toData(flag: String?, data: String?) {
        super.toData(flag, data)
        when (flag) {
            "物资类型" -> {
                val data = Gson().fromJson(data, BeanType::class.java)
                data.data.forEach {
                    var parkType = ParkType(
                        it.id,
                        it.label
                    )
                    viewModel.parks.add(parkType)
                }
                setAdapter()
                viewModel.getStock(standId)

            }
            "农资列表" ->{
                val beanList=Gson().fromJson(data,BeanList::class.java)
                stockAdapter.setDataList(beanList.data)
            }
            else -> {
            }
        }

    }

    fun setAdapter(): Unit {
        viewModel.selectedStandId=0
        viewModel.adapter = DropDownAdapter(
            viewModel.viewAction(dropDownView, headerChevronTv,Identification.STOCK),
            viewModel.parks.toMutableList()
        )
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModel. adapter
        for (i in viewModel.parks.indices) {
            viewModel.setStandStateWithId(viewModel.parks[i], i, headerChevronTv)
        }
        dropDownView.setHeaderView(collapsedView)
        dropDownView.setExpandedView(expandedView)
        dropDownView.dropDownListener = viewModel.dropDownListener
    }
}
