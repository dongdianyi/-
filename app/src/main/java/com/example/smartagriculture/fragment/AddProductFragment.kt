package com.example.smartagriculture.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentAddProductBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.DataViewModel
import kotlinx.android.synthetic.main.title_item.*

/**
 * A simple [Fragment] subclass.
 */
class AddProductFragment : BaseDropDownFragment<DataViewModel, FragmentAddProductBinding>() {


    override fun initLayout(): Int {
        return R.layout.fragment_add_product
    }

    override fun initView(savedInstanceState: Bundle?) {
        textView.text=getString(R.string.product_manage)
    }

    override fun lazyLoadData() {
    }

    override fun setListener() {
        back.setOnClickListener {
            nav().navigateUp()
        }
    }
}
