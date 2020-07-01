package com.example.smartagriculture.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.common.base.BaseFragment
import com.example.common.bean.Bean
import com.example.common.bean.BeanList
import com.example.common.data.Identification
import com.example.common.model.NoHttpRx
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentDetailsBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.DataViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.title_item.*

/**
 * A simple [Fragment] subclass.
 */
class DetailsFragment : BaseFragment<DataViewModel, FragmentDetailsBinding>() {

    override fun initLayout(): Int {
        return R.layout.fragment_details
    }

    override fun initView(view: View) {
        viewModel=ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        val id= arguments?.getInt("id")
        viewModel.noHttpRx = NoHttpRx(this)
        if (id != null) {
            viewModel.getWarnListDetails(id)
        }

    }

    override fun initData() {
        textView.text=getString(R.string.warning_details)
    }

    override fun setListener() {
        back.setOnClickListener {
            nav().navigateUp()
        }
    }
    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        var data= Gson().fromJson(`object`,Bean::class.java)
        when (data.data.warntype) {
            Identification.WARNEQUIPMENT -> {
                imageView47.setImageResource(R.mipmap.equipment_iv)
            }
            Identification.WARNEWEATHER -> {
                imageView47.setImageResource(R.mipmap.weather_iv)
            }
            Identification.WARNILLNESS -> {
                imageView47.setImageResource(R.mipmap.illness_iv)
            }
            Identification.WARNFARM -> {
                imageView47.setImageResource(R.mipmap.farm_iv)
            }
            else -> {
                imageView47.setImageResource(R.mipmap.warn_iv)
            }
        }
        textView131.text=data.data.title
        textView132.text=data.data.position
        textView133.text=data.data.warntime
        textView134.text=data.data.warnmessage

    }

}
