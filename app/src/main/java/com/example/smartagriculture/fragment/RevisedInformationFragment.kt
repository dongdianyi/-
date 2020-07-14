package com.example.smartagriculture.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.common.GlideLoadUtils
import com.example.common.base.PhotoFragment
import com.example.common.clickNoRepeat
import com.example.common.data.Identification.Companion.ONE
import com.example.common.data.Identification.Companion.TWO
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentRevisedInformationBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.MineViewModel
import com.jph.takephoto.model.TResult
import kotlinx.android.synthetic.main.fragment_revised_information.*
import kotlinx.android.synthetic.main.title_item.*
import java.io.File


/**
 * A simple [Fragment] subclass.
 */
class RevisedInformationFragment() :
    PhotoFragment<MineViewModel, FragmentRevisedInformationBinding>() {


    override fun initLayout(): Int {
        return R.layout.fragment_revised_information
    }

    override fun initView(savedInstanceState: Bundle?) {
        textView.text=getString(R.string.information)
        viewModel=ViewModelProvider(requireActivity()).get(MineViewModel::class.java)
        dataBinding.data=viewModel
        dataBinding.lifecycleOwner=this
    }

    override fun lazyLoadData() {
    }
    override fun setListener() {
        constraintLayout7.setOnClickListener {
            viewModel.showDialogBottom(takePhoto,requireActivity(), ONE,R.layout.photo_dialog)
        }

        constraintLayout8.setOnClickListener {
            viewModel.showDialogBottom(takePhoto,requireActivity(),TWO,R.layout.photo_dialog)
        }

        constraintLayout9.setOnClickListener {

        }

        back.clickNoRepeat {
            nav().navigateUp()
        }
    }

    override fun takeSuccess(result: TResult?) {
        super.takeSuccess(result)
        val tImage = result!!.image
        val file = File(tImage.compressPath)
    }

    override fun takeFail(result: TResult?, msg: String?) {
        super.takeFail(result, msg)

    }
}
