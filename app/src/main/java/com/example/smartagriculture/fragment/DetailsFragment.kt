package com.example.smartagriculture.fragment

import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.common.base.BaseFragment
import com.example.common.bean.Bean
import com.example.common.bean.BeanList
import com.example.common.bean.DataX
import com.example.common.data.Identification
import com.example.common.data.Identification.Companion.DEFAULT
import com.example.common.data.Identification.Companion.NOTICE
import com.example.common.data.Identification.Companion.WARNINGLIST
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

    var flag: Int = DEFAULT
    var ids: Int = DEFAULT
    lateinit var data: DataX

    override fun initLayout(): Int {
        return R.layout.fragment_details
    }

    override fun initView(view: View) {
        viewModel = ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        ids = arguments?.getInt("id") ?: ids
        flag = arguments?.getInt("flag") ?: flag
        if (null!=arguments?.getSerializable("data")) {
            data = arguments?.getSerializable("data") as DataX
        }
        viewModel.noHttpRx = NoHttpRx(this)

    }

    override fun initData() {
        when (flag) {
            WARNINGLIST -> {
                if (ids != -1) {
                    viewModel.getWarnListDetails(ids)
                }
                textView.text = getString(R.string.warning_details)
            }
            NOTICE -> {
                textView.text = getString(R.string.notice_description)
                imageView47.setImageResource(R.mipmap.notice_iv)
                textView131.text = data.title
                textView132.text = data.source
                textView133.text = data.creattime
                textView134.text = data.content
            }
            else -> {

            }
        }

    }

    override fun setListener() {
        back.setOnClickListener {
            nav().navigateUp()
        }
    }

    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        when (flag) {
            "预警详情" -> {
                var data = Gson().fromJson(`object`, Bean::class.java)
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
                textView131.text = data.data.title
                textView132.text = data.data.position
                textView133.text = data.data.warntime
                textView134.text = data.data.warnmessage
            }
            else -> {
            }
        }

    }

}
