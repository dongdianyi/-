package com.example.smartagriculture.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.common.BaseFragment
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentSettingBinding
import com.example.smartagriculture.util.Identification.Companion.EXIT
import com.example.smartagriculture.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.fragment_setting.*

/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : BaseFragment<MineViewModel,FragmentSettingBinding>() {


    override fun initLayout(): Int {
        return R.layout.fragment_setting
    }

    override fun initView(view: View) {
        viewModel=ViewModelProvider(requireActivity()).get(MineViewModel::class.java)
    }

    override fun initData() {
    }

    override fun setListener() {
        textView128.setOnClickListener {
            viewModel.showDialogBase(requireActivity(),EXIT,R.layout.dialog_layout)
        }
    }

}
