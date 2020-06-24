package com.example.smartagriculture.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.common.BaseFragment

import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentUpdatePwdBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.title_item.*

/**
 * A simple [Fragment] subclass.
 */
class UpdatePwdFragment : BaseFragment<MineViewModel,FragmentUpdatePwdBinding>() {


    override fun initLayout(): Int {
        return R.layout.fragment_update_pwd
    }

    override fun initView(view: View) {
    }

    override fun initData() {
        textView.text=getString(R.string.revise_pwd)
    }
    override fun setListener() {
        back.setOnClickListener {
            nav().navigateUp()
        }
    }
}
