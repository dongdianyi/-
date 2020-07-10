package com.example.smartagriculture.fragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.amap.api.mapcore.util.it
import com.example.common.GlideLoadUtils
import com.example.common.base.BaseFragment
import com.example.common.bean.Bean
import com.example.common.clickNoRepeat
import com.example.common.model.NoHttpRx

import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentMineBinding
import com.example.smartagriculture.viewmodel.MineViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * A simple [Fragment] subclass.
 */
class MineFragment : BaseFragment<MineViewModel, FragmentMineBinding>() {

    override fun initLayout(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel=ViewModelProvider(requireActivity()).get(MineViewModel::class.java)
    }

    override fun lazyLoadData() {
        viewModel.noHttpRx= NoHttpRx(this)
        viewModel.getInformation()
    }

    override fun setListener() {
        textView52.clickNoRepeat {
            viewModel.toRevisedInformation(it)
        }
        phone_constraint.clickNoRepeat {
            viewModel.toPhone(it)
        }
        pwd_constraint.clickNoRepeat {
            viewModel.toUpdatePwd(it)
        }
        setting.clickNoRepeat {
            val bundle=Bundle()
            bundle.putString("title",getString(R.string.setting))
            viewModel.toSetting(it,bundle)
        }
        problem.clickNoRepeat {
            viewModel.toProblem(it)
        }
        feedback.clickNoRepeat {
            val bundle=Bundle()
            bundle.putString("title",getString(R.string.feedback))
            viewModel.toSetting(it,bundle)
        }
        about.clickNoRepeat {
            val bundle=Bundle()
            bundle.putString("title",getString(R.string.about))
            viewModel.toSetting(it,bundle)
        }
        update.clickNoRepeat {
            viewModel.toUpdate(it)
        }
    }

    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        when (flag) {
            "个人资料" -> {
                val bean= Gson().fromJson(`object`, Bean::class.java)
                GlideLoadUtils.getInstance().glideLoad(requireContext(),bean.data.photo,imageView22,R.mipmap.default_iv)
                textView53.text=bean.data.userName
                if (TextUtils.isEmpty(bean.data.mobile)) {
                    textView54.text = resources.getString(R.string.phone_num, "--")
                    textView55.text = getString(R.string.bind_phone)
                }else {
                    textView54.text = resources.getString(R.string.phone_num, bean.data.mobile)
                    textView55.text = getString(R.string.revise_phone)
                }
            }
            else -> {
            }
        }
    }
}
