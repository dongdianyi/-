package com.example.smartagriculture.fragment

import android.view.View
import androidx.fragment.app.Fragment
import com.example.common.BaseFragment
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentRevisedInformationBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.title_item.*

/**
 * A simple [Fragment] subclass.
 */
class RevisedInformationFragment :
    BaseFragment<MineViewModel, FragmentRevisedInformationBinding>() {

    override fun initLayout(): Int {
        return R.layout.fragment_revised_information
    }

    override fun initView(view: View) {
        textView.text=getString(R.string.information)
    }

    override fun initData() {
    }
    override fun setListener() {
        back.setOnClickListener {
            nav().navigateUp()
        }
    }
}
