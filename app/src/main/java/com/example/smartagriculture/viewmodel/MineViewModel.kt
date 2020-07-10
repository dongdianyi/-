package com.example.smartagriculture.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.common.ToastUtil
import com.example.common.clickNoRepeat
import com.example.common.data.BaseUrl
import com.example.common.data.CommitParam
import com.example.common.getPop
import com.example.smartagriculture.R
import com.example.smartagriculture.activity.LoginActivity
import com.example.smartagriculture.util.ClearCache
import com.example.smartagriculture.util.nav
import java.util.*

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

    fun toSetting(view: View,bundle: Bundle): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_settingFragment,bundle)
    }

    fun toProblem(view: View): Unit {
        nav(view).navigate(R.id.action_mainFragment_to_problemFragment)
    }
    fun toUpdate(view: View): Unit {
        ToastUtil("已是最新版本")
    }

    fun showDialogBase(activity: Activity, title: String, content: String, layout: Int): Unit {
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
                false,
                100,
                0
            )
        dialogCircle.show()
        val sureButton = rootView.findViewById<Button>(com.example.common.R.id.sure_button)
        val closeIv = rootView.findViewById<ImageView>(com.example.common.R.id.close_iv)
        val textView129 = rootView.findViewById<TextView>(com.example.common.R.id.textView129)
        val textView130 = rootView.findViewById<TextView>(com.example.common.R.id.textView130)
        textView129.text = title
        textView130.text = content
        sureButton.clickNoRepeat {
            when (title) {
                "退出登录" -> {
                    ClearCache.cleanApplicationData(Objects.requireNonNull(getApplication()))
                    activity.startActivity(Intent(activity, LoginActivity::class.java))
                    activity.finish()
                }
                "清除缓存" -> {
                    ClearCache.clearAllCache(Objects.requireNonNull(getApplication()))
                    cacheSize.value =
                        "0K"
                }
                else -> {
                }
            }
            dialogCircle.dismiss()
        }
        closeIv.clickNoRepeat {
            dialogCircle.dismiss()
        }
    }

    fun getCache(): MutableLiveData<String> {
        cacheSize = MutableLiveData()
        cacheSize.value = ClearCache.getTotalCacheSize(Objects.requireNonNull(getApplication()))
        return cacheSize
    }

    fun getProblem(page: String): Unit {
        val commitParam = CommitParam()
        commitParam.page = page
        commitParam.pageSize = "10"
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
        noHttpRx.postHttpJson(
            map,
            "个人资料",
            BaseUrl.BASE_URL3 + BaseUrl.INGORMATION,
            commitParam.toJson(commitParam),
            onDialogGetListener
        )
    }

}