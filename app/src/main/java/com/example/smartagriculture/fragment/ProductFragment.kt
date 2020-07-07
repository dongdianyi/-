package com.example.smartagriculture.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common.adapter.DropDownAdapter
import com.example.common.bean.Bean
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.HomeAdapter
import com.example.smartagriculture.databinding.FragmentProductBinding
import com.example.common.myview.TextDrawable
import com.example.common.data.Identification
import com.example.common.model.NoHttpRx
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.DataViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.title_item.*

/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : BaseDropDownFragment<DataViewModel, FragmentProductBinding>() {

    private lateinit var productAdapter: HomeAdapter

    override fun initLayout(): Int {
        return R.layout.fragment_product
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        textView.text = getString(R.string.product_manage)
    }

    override fun lazyLoadData() {
        productAdapter =
            HomeAdapter(requireContext(), R.layout.product_item, Identification.PRODUCT)
        mLRecycleViewAdapter = LRecyclerViewAdapter(productAdapter)
        product_recycler.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.mm_10)
            .setColorResource(R.color.bg)
            .build()
        product_recycler.addItemDecoration(divider)
        product_recycler.layoutManager = LinearLayoutManager(requireContext())
        viewModel.noHttpRx= NoHttpRx(this)
        viewModel.getProduct("1","")
    }

    override fun setListener() {
        product_recycler.setPullRefreshEnabled(false)
        back.setOnClickListener {
            nav().navigateUp()
        }
    }

    override fun toData(flag: String?, data: String?) {
        super.toData(flag, data)
        val bean= Gson().fromJson(data,Bean::class.java)
        productAdapter.setDataList(bean.data.rows)
    }

}
