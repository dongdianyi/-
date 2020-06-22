package com.example.smartagriculture.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.HomeAdapter
import com.example.smartagriculture.adapter.DropDownAdapter
import com.example.smartagriculture.databinding.FragmentStockBinding
import com.example.smartagriculture.myview.TextDrawable
import com.example.smartagriculture.util.Identification
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.DataViewModel
import com.github.jdsjlzx.ItemDecoration.GridItemDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
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

    override fun initLayout(): Int {
        return R.layout.fragment_stock
    }

    override fun initView(view: View) {

        textView.text = getString(R.string.stock_manage)

        collapsedView =
            LayoutInflater.from(context)
                .inflate(R.layout.dropview_header_stock, null, false)

        headerChevronTv =
            collapsedView.findViewById<TextDrawable>(R.id.textDrawable8)


        parks =
            arrayOf("全部物资", "青岛物资", "济南历下区物资")



        expandedView = LayoutInflater.from(context).inflate(R.layout.dropview_expanded, null, false)

        recyclerView =
            expandedView.findViewById<View>(R.id.recyclerView) as RecyclerView

    }

    override fun initData() {

        viewAction(dropDownView, headerChevronTv).selectedStand = 0
        adapter = DropDownAdapter(viewAction(dropDownView, headerChevronTv), parks)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        for (i in parks.indices) {
            setStandStateWithId(parks[i], i, headerChevronTv)
        }
        dropDownView.setHeaderView(collapsedView)
        dropDownView.setExpandedView(expandedView)
        dropDownView.dropDownListener = dropDownListener

        var manager=GridLayoutManager(requireContext(), 3)
        stock_recycler.layoutManager = manager

        stockAdapter =
            HomeAdapter(requireContext(), R.layout.stock_item, Identification.STOCK)
        mLRecycleViewAdapter = LRecyclerViewAdapter(stockAdapter)
        val dataList = listOf<String>("镁肥", "钙肥", "有机肥")
        stockAdapter.setDataList(dataList)
        stock_recycler.adapter = mLRecycleViewAdapter

//        val spacing = resources.getDimensionPixelSize(R.dimen.dp_4)
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
            .setHorizontal(R.dimen.dp_20)
            .setVertical(R.dimen.dp_20)
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

}
