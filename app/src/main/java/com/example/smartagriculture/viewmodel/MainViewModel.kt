package com.example.smartagriculture.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.LogUtil
import com.example.common.ToastUtil
import com.example.common.base.BaseApplication
import com.example.common.clickNoRepeat
import com.example.common.data.BaseField
import com.example.common.data.BaseUrl
import com.example.common.data.CommitParam
import com.example.common.data.Identification.Companion.DEFAULT
import com.example.common.data.Identification.Companion.SCREEN
import com.example.common.data.Identification.Companion.STOCK
import com.example.common.getPop
import com.example.smartagriculture.R
import com.example.smartagriculture.db.Massif
import com.example.smartagriculture.db.MassifRepository
import com.example.smartagriculture.util.nav
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.park_dialog.view.*
import java.text.SimpleDateFormat
import java.util.*


class MainViewModel(
    application: Application, savedStateHandle: SavedStateHandle
) : BaseShpViewModel(application, savedStateHandle) {
    var position: Int = 1
    lateinit var rootView: View
    var flag: Int = DEFAULT
    lateinit var bestLocation: Location

    lateinit var massifRepository: MassifRepository

    fun initMassif(): Unit {
        massifRepository = MassifRepository(getApplication())
    }

    fun getAllMassif(): LiveData<MutableList<Massif>>? {
        return massifRepository.getListLiveData()
    }

    fun insert(vararg massif: Massif): Unit {
        massifRepository.insert(*massif)
    }

    fun update(vararg massif: Massif): Unit {
        massifRepository.update(*massif)
    }

    fun delete(vararg massif: Massif): Unit {
        massifRepository.delete(*massif)
    }

    fun clear(): Unit {
        massifRepository.clear()
    }

    fun showDialog(activity: Activity, flag: Int) {

        rootView =
            LayoutInflater.from(activity).inflate(R.layout.park_dialog, null)
        when (flag) {
            STOCK -> {
                rootView.radioButton.isChecked = true
                rootView.radioButton.text = activity.getString(R.string.stock_manage)
                rootView.radioButton2.text = activity.getString(R.string.product_manage)
                rootView.radioButton3.visibility = View.GONE
            }
            else -> {
                if (position == 1) {
                    rootView.radioButton.isChecked = true
                }
                if (position == 2) {
                    rootView.radioButton2.isChecked = true
                }
                if (position == 3) {
                    rootView.radioButton3.isChecked = true
                }
            }
        }

        rootView.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioButton -> {
                    position = 1
                }
                R.id.radioButton2 -> {
                    position = 2
                }
                R.id.radioButton3 -> {
                    position = 3
                }
                else -> {
                }
            }
        }
        rootView.cancel_button.clickNoRepeat {
            dismissDialog()
        }
        rootView.sure_button.clickNoRepeat {
            dismissDialog()
            sureBtnClick()
            when (flag) {
                SCREEN -> {
                    query = position.toString()
                    getParks(standId, query, "")
                }
                STOCK -> {
                    if (rootView.radioGroup.checkedRadioButtonId == R.id.radioButton) {
                        nav(
                            activity,
                            R.id.stock_constraint
                        ).navigate(R.id.action_mainFragment_to_stockFragment)
                    } else {
                        nav(
                            activity,
                            R.id.stock_constraint
                        ).navigate(R.id.action_mainFragment_to_productFragment)
                    }
                }
                else -> {
                }
            }
        }
        dialogCircle =
            getPop(
                activity,
                rootView,
                1,
                3,
                Gravity.BOTTOM,
                R.style.BottomDialog_Animation,
                false,
                0,
                0
            )
        dialogCircle.show()
    }


    fun sureBtnClick(): Unit {
        when (flag) {
            STOCK -> {
                if (rootView.radioGroup.checkedRadioButtonId == R.id.radioButton) {
                    nav(
                        rootView
                    ).navigate(R.id.action_mainFragment_to_stockFragment)
                } else {
                    nav(
                        rootView
                    ).navigate(R.id.action_mainFragment_to_productFragment)
                }
            }
            SCREEN -> {

            }
            else -> {
            }
        }
    }

    fun dismissDialog(): Unit {
        if (dialogCircle.isShowing) {
            dialogCircle.dismiss()
        }

    }

    fun toWarningMessage(view: View, parkId: String) {
        val bundle = Bundle()
        bundle.putString("parkId", parkId)
        nav(view).navigate(R.id.action_mainFragment_to_warningMessageFragment, bundle)
    }

    fun toMonitor(view: View, parkId: String) {
        ARouter.getInstance().build(BaseField.MONITOR_PATH).navigation()
    }

    fun toNotice(view: View) {
        nav(view).navigate(R.id.action_mainFragment_to_noticeFragment)
    }

    fun toSearch(view: View, flag: Int) {
        var bundle = Bundle()
        bundle.putInt("flag", flag)
        nav(view).navigate(R.id.action_mainFragment_to_searchFragment, bundle)
    }

    fun toWeather(view: View) {
        nav(view).navigate(R.id.action_mainFragment_to_weatherFragment)
    }

    fun getNotice() {
        noHttpRx.getHttp(map, "系统通知", BaseUrl.NOTICE_NUM, onDialogGetListener)
    }

    fun getParkType() {
        var commitParam = CommitParam()
        commitParam.companyId = "1"
        noHttpRx.postHttpJson(
            map,
            "园区类型",
            BaseUrl.BASE_URL + BaseUrl.PARK_TYPE_URL,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

    fun login(username: String, password: String) {
        var commitParam = CommitParam()
        commitParam.username = username
        commitParam.password = password
        noHttpRx.postHttpJson(
            map,
            "登录",
            BaseUrl.BASE_URL + BaseUrl.LOGIN,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

    fun getAppRole() {
        noHttpRx.getHttp(map, "考勤权限", BaseUrl.GETAPPROLE, onDialogGetListener)
    }

    fun getLocation(activity: FragmentActivity): Unit {
        PermissionX.init(activity)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .explainReasonBeforeRequest()
            .onForwardToSettings { deniedList ->
                //监听那些被用户永久拒绝的权限
                showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    getLocationLL()
                } else {
                    ToastUtil("您拒绝了定位权限")
                }


            }

    }

    private fun getLocationLL() {
        var location = getLastKnownLocation()
        //            传递经纬度给网页
        var result =
            "{code: '0',type:'2',data: {longitude: '" + location.longitude + "',latitude: '" + location.latitude + "'}}"
        var webView = WebView(BaseApplication.context)
        webView.loadUrl("javascript:callback($result)")

        //日志
        var locationStr = "维度：" + location.latitude + "经度：" + location.longitude
        LogUtil("*************", "经纬度：$locationStr")
    }

    /**
     * 定位：得到位置对象
     * @return
     */
    @SuppressLint("MissingPermission")
    fun getLastKnownLocation(): Location {
        //获取地理位置管理器
        val mLocationManager =
            BaseApplication.application?.getSystemService(LOCATION_SERVICE) as LocationManager
        val providers = mLocationManager.getProviders(true)
        bestLocation = Location("")
        for (provider in providers) {
            bestLocation = mLocationManager.getLastKnownLocation(provider) ?: continue
        }
        return bestLocation
    }


    @SuppressLint("SimpleDateFormat")
    fun getDate(): String {

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")// HH:mm:ss
//获取当前时间
        val date = Date(System.currentTimeMillis());
        return simpleDateFormat.format(date)
    }


}