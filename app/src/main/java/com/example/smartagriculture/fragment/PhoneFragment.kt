package com.example.smartagriculture.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.common.BaseFragment

import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentPhoneBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.title_item.*

/**
 * A simple [Fragment] subclass.
 */
class PhoneFragment : BaseFragment<MineViewModel, FragmentPhoneBinding>() {


    override fun initLayout(): Int {
        return R.layout.fragment_phone
    }

    override fun initView(view: View) {
    }

    override fun initData() {
        textView.text=getString(R.string.revise_phone)
    }

    override fun setListener() {
        back.setOnClickListener {
            nav().navigateUp()
        }
    }

}
