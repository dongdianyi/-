package com.example.smartagriculture.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.common.base.BaseFragment

import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentAboutBinding
import com.example.smartagriculture.databinding.FragmentFeedBackBinding
import com.example.smartagriculture.viewmodel.MineViewModel

/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : BaseFragment<MineViewModel, FragmentAboutBinding>() {

    override fun initLayout(): Int {
        return R.layout.fragment_about
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun lazyLoadData() {
    }

}
