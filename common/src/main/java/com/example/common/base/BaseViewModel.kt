package com.example.common.base

import android.app.Activity
import android.app.Application
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import com.example.common.*
import com.example.common.adapter.DropDownAdapter
import com.example.common.bean.ParkType
import com.example.common.data.BaseUrl
import com.example.common.data.CommitParam
import com.example.common.model.NoHttpRx
import com.example.common.myview.DropDownView
import com.example.common.myview.TextDrawable
import com.liqi.nohttputils.interfa.OnDialogGetListener


open class BaseViewModel(application: Application) : AndroidViewModel(application){
    lateinit var dialogCircle: DialogCircle
    lateinit var parks: MutableList<ParkType>
    var onDialogGetListener: OnDialogGetListener? = null
    var query="1"
    lateinit var noHttpRx:NoHttpRx
    var standId=0



    fun showDialogBase(activity: Activity, flag: Int, layout: Int): Unit {
        val rootView =
            View.inflate(activity, layout, null)
        dialogCircle =
            getPop(
                activity,
                rootView,
                1,
                3,
                Gravity.CENTER,
                0,
                false
            )

        var sureButton = rootView.findViewById<Button>(R.id.sure_button)
        var closeIv = rootView.findViewById<ImageView>(R.id.close_iv)
        sureButton.setOnClickListener {
            dialogCircle.dismiss()
        }
        closeIv.setOnClickListener {
            dialogCircle.dismiss()
        }
    }

    lateinit var adapter: DropDownAdapter
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

    fun setStandStateWithId(parkType: ParkType, standId: Int, headerChevronTv: TextDrawable) {
        if (standId in 0..parks.size) {
            parks[standId].parkName = parkType.parkName
            adapter.notifyItemChanged(standId)
        }

        // Should update currently selected stand wait time as well
        if (selectedStandId == standId) {
            headerChevronTv.text = parkType.parkName
        }
    }

    fun viewAction(
        drop_down_view: DropDownView,
        headerChevronTv: TextDrawable
    ): DropDownAdapter.ViewActions {
        val viewActions: DropDownAdapter.ViewActions =
            object : DropDownAdapter.ViewActions {

                override fun collapseDropDown() {
                    drop_down_view.collapseDropDown()
                }

                override fun getStandTitle(standId: Int): String? {
                    return parks[standId].parkName
                }

                override fun getStandStatus(standId: Int): String? {
                    return parks[standId].parkName
                }

                override var selectedStand: Int
                    get() = selectedStandId
                    set(standId) {
                        headerChevronTv.text = getStandStatus(standId)
                        selectedStandId = standId
                        getParks(parks, standId,query,noHttpRx)
                    }
            }
        return viewActions
    }

    fun getParks(
        parks: MutableList<ParkType>,
        standId: Int,
        query: String,
        noHttpRx: NoHttpRx
    ): Unit {
        this.query=query
        this.noHttpRx=noHttpRx
        this.standId=standId
        var commitParam = CommitParam()
        commitParam.companyId = "1"
        commitParam.parkType = parks[standId].parkType
        if (standId != 0) {
            commitParam.parkId = parks[standId].parkId
        } else {
            commitParam.parkId = null
        }
        commitParam.query = query
        var map= hashMapOf<String,String>()
        noHttpRx.postHttpJson(
            map,
            "首页列表",
            BaseUrl.BASE_URL + BaseUrl.PARK_LIST_URL,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }


}