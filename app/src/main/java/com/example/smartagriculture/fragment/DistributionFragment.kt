package com.example.smartagriculture.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.common.BaseFragment

import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentDistributionBinding
import com.example.smartagriculture.viewmodel.DistributionViewModel

/**
 * A simple [Fragment] subclass.
 */
class DistributionFragment : BaseFragment<DistributionViewModel,FragmentDistributionBinding>() {


    override fun initLayout(): Int {
        return R.layout.fragment_distribution
    }

    override fun initView(view: View) {
    }

    override fun initData() {
    }

}
