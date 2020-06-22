package com.example.smartagriculture.viewmodel

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.BaseApplication.Companion.context
import com.example.common.BaseField
import com.example.common.BaseViewModel
import com.example.common.LogUtil
import com.example.smartagriculture.R
import com.example.smartagriculture.util.DialogCircle
import com.example.smartagriculture.util.getPop
import com.example.smartagriculture.util.nav
import kotlinx.android.synthetic.main.park_dialog.view.*


class MainViewModel(application: Application) : BaseViewModel(application) {
    private lateinit var dialogCircle: DialogCircle
    val STOCK = "stock"
    val SCREEN = "screen"
    var position: Int = 0

    fun showDialog(activity: Activity, flag: String) {
        val rootView =
            View.inflate(activity, R.layout.park_dialog, null)
        when (flag) {
            STOCK -> {
                rootView.radioButton.text = context?.getString(R.string.stock_manage)
                rootView.radioButton2.text = context?.getString(R.string.product_manage)
                rootView.radioButton3.visibility = View.GONE
            }
            else -> {
            }
        }

        rootView.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            LogUtil("走了吗", "setOnCheckedChangeListener")
            when (checkedId) {
                R.id.radioButton -> {
                    LogUtil("走了吗", "zoulema")
                    position = 0
                }
                R.id.radioButton2 -> {
                    position = 1
                }
                R.id.radioButton3 -> {
                    position = 2
                }
                else -> {
                }
            }
        }
        rootView.cancel_button.setOnClickListener {
            dismissDialog()
        }
        rootView.sure_button.setOnClickListener {
            dismissDialog()
            when (flag) {
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
            getPop(activity, rootView, 1, 3, Gravity.BOTTOM, R.style.BottomDialog_Animation, false)
    }

    fun dismissDialog(): Unit {
        if (dialogCircle.isShowing) {
            dialogCircle.dismiss()
        }

    }

    fun toWarningMessage(view: View, position: Int) {
        val bundle = Bundle()
        bundle.putInt("position", position)
        nav(view).navigate(R.id.action_mainFragment_to_warningMessageFragment, bundle)
    }
    fun toMonitor(view: View, position: Int) {
        ARouter.getInstance().build(BaseField.MONITOR_PATH).navigation()
    }

    fun toNotice(view: View) {
        nav(view).navigate(R.id.action_mainFragment_to_noticeFragment)
    }
    fun toSearch(view: View) {
        nav(view).navigate(R.id.action_mainFragment_to_searchFragment)
    }


}