package com.example.smartagriculture.fragment

import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.common.base.BaseFragment

import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentMineBinding
import com.example.smartagriculture.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * A simple [Fragment] subclass.
 */
class MineFragment : BaseFragment<MineViewModel, FragmentMineBinding>() {

    override fun initLayout(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(view: View) {
        viewModel=ViewModelProvider(requireActivity()).get(MineViewModel::class.java)
    }

    override fun initData() {

    }

    override fun setListener() {
        textView52.setOnClickListener {
            viewModel.toRevisedInformation(it)
        }
        phone_constraint.setOnClickListener {
            viewModel.toPhone(it)
        }
        pwd_constraint.setOnClickListener {
            viewModel.toUpdatePwd(it)
        }
    }

}
