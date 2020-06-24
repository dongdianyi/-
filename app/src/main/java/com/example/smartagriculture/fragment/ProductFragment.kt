package com.example.smartagriculture.fragment

import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.HomeAdapter
import com.example.smartagriculture.adapter.DropDownAdapter
import com.example.smartagriculture.databinding.FragmentProductBinding
import com.example.smartagriculture.myview.TextDrawable
import com.example.smartagriculture.util.Identification
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.DataViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.title_item.*

/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : BaseDropDownFragment<DataViewModel, FragmentProductBinding>() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var collapsedView: View
    private lateinit var expandedView: View
    private lateinit var headerChevronTv: TextDrawable

    private lateinit var productAdapter: HomeAdapter

    override fun initLayout(): Int {
        return R.layout.fragment_product
    }

    override fun initView(view: View) {
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        textView.text = getString(R.string.product_manage)
        collapsedView =
            LayoutInflater.from(context)
                .inflate(R.layout.dropview_header_product, null, false)


        v.post {
            var layoutParams = RelativeLayout.LayoutParams(v.width, v.height)
            collapsedView.layoutParams = layoutParams
        }


        headerChevronTv =
            collapsedView.findViewById<TextDrawable>(R.id.textDrawable4)


        parks =
            arrayOf("全部园区", "青岛园区分区", "济南历下区园区", "淄博园区")

        expandedView = LayoutInflater.from(context).inflate(R.layout.dropview_expanded, null, false)

        recyclerView =
            expandedView.findViewById<View>(R.id.recyclerView) as RecyclerView
    }

    override fun initData() {
        viewAction(dropDownView_product, headerChevronTv).selectedStand = 0
        adapter = DropDownAdapter(viewAction(dropDownView_product, headerChevronTv), parks)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        for (i in parks.indices) {
            setStandStateWithId(parks[i], i, headerChevronTv)
        }
        dropDownView_product.setHeaderView(collapsedView)
        dropDownView_product.setExpandedView(expandedView)
        dropDownView_product.dropDownListener = dropDownListener


        productAdapter =
            HomeAdapter(requireContext(), R.layout.product_item, Identification.PRODUCT)
        mLRecycleViewAdapter = LRecyclerViewAdapter(productAdapter)
        val dataList = listOf<String>("wwwwww", "dsds", "章丘大鱼合作社")
        productAdapter.setDataList(dataList)
        product_recycler.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.mm_10)
            .setColorResource(R.color.bg)
            .build()
        product_recycler.addItemDecoration(divider)
        product_recycler.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun setListener() {
        product_recycler.setPullRefreshEnabled(false)
        textView28.setOnClickListener {
            viewModel.toAddProduct(it)
        }
        back.setOnClickListener {
            nav().navigateUp()
        }
    }

}
