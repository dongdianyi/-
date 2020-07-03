package com.example.common.base

import android.app.Activity
import android.app.Application
import android.content.Context
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
import com.example.common.data.Identification
import com.example.common.model.NoHttpRx
import com.example.common.myview.DropDownView
import com.example.common.myview.TextDrawable
import com.liqi.nohttputils.interfa.OnDialogGetListener


open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var dialogCircle: DialogCircle
    lateinit var parks: MutableList<ParkType>
    var onDialogGetListener: OnDialogGetListener? = null
    var query = "1"
    lateinit var noHttpRx: NoHttpRx
    var standId = 0


    fun showDialogBase(activity: Activity, flag: Int, layout: Int): Unit {
        val rootView =
            View.inflate(activity, layout, null)
        dialogCircle =
            getPop(
                activity,
                rootView,
                2,
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
    var selectedStandId = 0


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
        headerChevronTv: TextDrawable,
        flag: Int
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
                    if (Identification.PARK == flag) {
                        getParks(standId, query, "")
                    }
                    if (Identification.STOCK == flag) {
                        getStock(standId)
                    }
                    return parks[standId].parkName

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

    fun getParks(
        standId: Int,
        query: String,
        massifName: String
    ): Unit {
        this.query = query
        this.noHttpRx = noHttpRx
        this.standId = standId
        var commitParam = CommitParam()
        commitParam.companyId = "1"
        when (standId) {
            0 -> {
                commitParam.parkId = null
                commitParam.parkType = parks[standId].parkType
            }
            -1 -> {
                commitParam.parkId = null
                commitParam.parkType = null
            }
            else -> {
                commitParam.parkId = parks[standId].parkId
                commitParam.parkType = parks[standId].parkType
            }
        }
        commitParam.query = query
        commitParam.massifName = massifName
        var map = hashMapOf<String, String>()
        noHttpRx.postHttpJson(
            map,
            "首页列表",
            BaseUrl.BASE_URL + BaseUrl.PARK_LIST_URL,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

    fun getStock(standId: Int): Unit {
        var commitParam = CommitParam()
        commitParam.materialsTypeId = parks[standId].id
        commitParam.companyId = "1"
        var map = HashMap<String, String>()
        map["Authorization"] = "1239461961037942784"
        noHttpRx.postHttpJson(
            map,
            "农资列表",
            BaseUrl.BASE_URL2 + BaseUrl.STOCK_LIST,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

    fun getProduct(page: String, productName: String): Unit {
        val commitParam = CommitParam()
        commitParam.companyId = "1"
        commitParam.productName = productName
        commitParam.page = page
        commitParam.pageSize = "10"
        var map = HashMap<String, String>()
        map["Authorization"] = "1239461961037942784"
        noHttpRx.postHttpJson(
            map,
            "产品列表",
            BaseUrl.BASE_URL3 + BaseUrl.PRODUCT_LIST,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

    fun getWeather(ip: String?): Unit {
        val commitParam = CommitParam()
        commitParam.ip = ip
        var map = HashMap<String, String>()
        map["Authorization"] = "1239461961037942784"
        noHttpRx.postHttpJson(
            map,
            "天气",
            BaseUrl.BASE_URL2 + BaseUrl.WEATHER,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }
    fun getIp(): Unit {
        noHttpRx.getHttp(
            BaseUrl.GET_IP
        )
    }

    //ip地址
    fun getIp(context: Context): String? {
        when (NetUtil.getNetWorkState(context)) {
            NetUtil.NETWORK_NONE -> {
                ToastUtil(context.getString(R.string.no_net))
                return null
            }
            NetUtil.NETWORK_MOBILE -> {
                return NetWorkUtils.getLocalIpAddress()
            }
            NetUtil.NETWORK_WIFI -> {
                return NetWorkUtils.getLocalIpAddress(context)
            }
            else -> {
            }
        }
        return null
    }
}