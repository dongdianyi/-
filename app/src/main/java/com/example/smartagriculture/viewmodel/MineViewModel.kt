package com.example.smartagriculture.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.common.LogUtil
import com.example.common.base.BaseViewModel
import com.example.common.data.BaseUrl
import com.example.common.data.CommitParam
import com.example.common.getPop
import com.example.smartagriculture.R
import com.example.smartagriculture.activity.LoginActivity
import com.example.smartagriculture.activity.MainActivity
import com.example.smartagriculture.util.ClearCache
import com.example.smartagriculture.util.nav

class MineViewModel(
    application: Application, savedStateHandle: SavedStateHandle
) : BaseShpViewModel(application, savedStateHandle) {


    private lateinit var cacheSize: MutableLiveData<String>

    fun toRevisedInformation(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_revisedInformationFragment)
    }

    fun toPhone(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_phoneFragment)
    }

    fun toUpdatePwd(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_updatePwdFragment)
    }

    fun toSetting(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_settingFragment)
    }

    fun toProblem(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_problemFragment)
    }

    fun showDialogBase(activity: Activity, title: String, content: String, layout: Int): Unit {
        val rootView =
            View.inflate(activity, layout, null)
        dialogCircle =
            getPop(
                activity,
                rootView,
                2,
                2,
                Gravity.CENTER,
                0,
                false
            )
        dialogCircle.show()
        val sureButton = rootView.findViewById<Button>(com.example.common.R.id.sure_button)
        val closeIv = rootView.findViewById<ImageView>(com.example.common.R.id.close_iv)
        val textView129 = rootView.findViewById<TextView>(com.example.common.R.id.textView129)
        val textView130 = rootView.findViewById<TextView>(com.example.common.R.id.textView130)
        textView129.text = title
        textView130.text = content
        sureButton.setOnClickListener {
            when (title) {
                "退出登录" -> {
                    activity.startActivity(Intent(activity, LoginActivity::class.java))
                    activity.finish()
                }
                "清除缓存" -> {
                    ClearCache.clearAllCache(getApplication())
                }
                else -> {
                }
            }
            dialogCircle.dismiss()
        }
        closeIv.setOnClickListener {
            dialogCircle.dismiss()
        }
    }

    fun getCache(): MutableLiveData<String> {
        cacheSize=MutableLiveData()
        cacheSize .value= ClearCache.getTotalCacheSize(getApplication())
        return cacheSize
    }

    fun getProblem(page: String): Unit {
        val commitParam = CommitParam()
        commitParam.page = page
        commitParam.pageSize = "10"
        val map = hashMapOf<String, String>()
        map[getApplication<Application>().resources.getString(R.string.token)] =
            getUserId().value.toString()
        noHttpRx.postHttpJson(
            map,
            "常见问题",
            BaseUrl.BASE_URL3 + BaseUrl.PROBLEM,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

    fun getInformation(): Unit {
        val commitParam = CommitParam()
        val map = hashMapOf<String, String>()
        map[getApplication<Application>().resources.getString(R.string.token)] =
            getUserId().value.toString()
        noHttpRx.postHttpJson(
            map,
            "个人资料",
            BaseUrl.BASE_URL3 + BaseUrl.INGORMATION,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

}