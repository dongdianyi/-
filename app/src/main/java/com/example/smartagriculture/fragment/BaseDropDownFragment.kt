package com.example.smartagriculture.fragment

import android.app.Dialog
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.common.BaseFragment
import com.example.common.BaseViewModel
import com.example.common.view.IView
import com.example.smartagriculture.adapter.DropDownAdapter
import com.example.smartagriculture.myview.DropDownView
import com.example.smartagriculture.myview.TextDrawable
import com.liqi.nohttputils.interfa.OnDialogGetListener

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseDropDownFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseFragment<VM,DB>(){
    lateinit var adapter: DropDownAdapter
    lateinit var parks: Array<String>
    private var selectedStandId = 0


    val dropDownListener = object :
        DropDownView.DropDownListener {
        override fun onExpandDropDown() {
            adapter.notifyDataSetChanged()
//            ObjectAnimator.ofFloat(headerChevronIV, View.ROTATION.name, 180f)
//                .start()
        }

        override fun onCollapseDropDown() {
//            ObjectAnimator.ofFloat(headerChevronIV, View.ROTATION.name, -180f, 0f)
//                .start()
        }
    }

    fun setStandStateWithId(waitTime: String?, standId: Int,headerChevronTv:TextDrawable) {
        if (standId in 0..parks.size) {
            if (waitTime != null) {
                parks[standId] = waitTime
            }
            adapter.notifyItemChanged(standId)
        }

        // Should update currently selected stand wait time as well
        if (selectedStandId == standId) {
            headerChevronTv.text = waitTime
        }
    }

    fun viewAction(drop_down_view: DropDownView,headerChevronTv:TextDrawable): DropDownAdapter.ViewActions {
        val viewActions: DropDownAdapter.ViewActions =
            object : DropDownAdapter.ViewActions {

                override fun collapseDropDown() {
                    drop_down_view.collapseDropDown()
                }

                override fun getStandTitle(standId: Int): String? {
                    return parks[standId]
                }

                override fun getStandStatus(standId: Int): String? {
                    return parks[standId]
                }

                override var selectedStand: Int
                    get() = selectedStandId
                    set(standId) {
                        headerChevronTv.text = getStandStatus(standId)
                        selectedStandId = standId
                    }
            }
        return viewActions
    }

    override fun toData(flag: String?, data: String?) {
        super.toData(flag, data)
    }

    override fun fail(isNetWork: Boolean, flag: String?, t: Throwable?) {
        super.fail(isNetWork, flag, t)
    }

}
