package com.example.smartagriculture.fragment

import android.view.View
import androidx.fragment.app.Fragment
import com.example.common.BaseFragment
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentNoticeBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.DataViewModel
import kotlinx.android.synthetic.main.fragment_notice.*

/**
 * A simple [Fragment] subclass.
 */
class NoticeFragment : BaseFragment<DataViewModel,FragmentNoticeBinding>() {


    override fun initLayout(): Int {
        return R.layout.fragment_notice
    }

    override fun initView(view: View) {
    }

    override fun initData() {
    }

    override fun setListener() {
        back.setOnClickListener {
            nav().navigateUp()
        }
    }


}
