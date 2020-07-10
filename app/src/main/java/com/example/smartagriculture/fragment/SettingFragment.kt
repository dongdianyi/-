package com.example.smartagriculture.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.common.AppUtils
import com.example.common.LogUtil
import com.example.common.base.BaseApplication
import com.example.common.base.BaseFragment
import com.example.common.clickNoRepeat
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentSettingBinding
import com.example.common.data.Identification.Companion.EXIT
import com.example.smartagriculture.util.ClearCache
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.setting_layout.*
import kotlinx.android.synthetic.main.title_item.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : BaseFragment<MineViewModel, FragmentSettingBinding>() {
    lateinit var title:String

    override fun initLayout(): Int {
        return R.layout.fragment_setting
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel=ViewModelProvider(requireActivity()).get(MineViewModel::class.java)
        dataBinding.data=viewModel
        dataBinding.lifecycleOwner=this
        title= arguments?.getString("title").toString()
    }

    @SuppressLint("SetTextI18n")
    override fun lazyLoadData() {
        textView.text=title
        when (title) {
            getString(R.string.setting) -> {
                include.visibility=View.VISIBLE
                textView123.text="版本："+AppUtils.getAppName(BaseApplication.context)+"  "+AppUtils.getVersionName(BaseApplication.context)
            }
            getString(R.string.feedback) ->{
                include2.visibility=View.VISIBLE
            }
            getString(R.string.about) ->{
                include3.visibility=View.VISIBLE
            }
            else -> {
            }
        }
    }

    override fun setListener() {
        when (title) {
            getString(R.string.setting) -> {
                textView125.clickNoRepeat {
                    viewModel.showDialogBase(requireActivity(),getString(R.string.clear_cache),getString(R.string.sure_clear_cache),R.layout.dialog_layout)
                }
                textView128.clickNoRepeat {
                    viewModel.showDialogBase(requireActivity(),getString(R.string.exit),getString(R.string.sure_exit),R.layout.dialog_layout)
                }
            }
            else -> {
            }
        }
        back.clickNoRepeat {
            nav().navigateUp()
        }
    }

}
