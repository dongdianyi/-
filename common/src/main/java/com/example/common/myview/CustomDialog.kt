package com.example.common.myview

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import com.example.common.LogUtil
import com.example.common.R

/**
 * 加载提醒对话框
 * 如果 show之后立即dismiss则不进行展示
 */
class CustomDialog(context: Context?, theme: Int) : Dialog(context!!, theme) {
    private val dismissTime = 0x0
    private var isDismiss = false
    private lateinit var mHandlerTime: Handler

    init {
        mHandlerTime = Handler(Handler.Callback {
            when (it.what) {
                dismissTime -> {
                    if (!isDismiss) {
                        showReallyDialog()
                    } else {
                        isDismiss = false
                    }
                    mHandlerTime.removeCallbacksAndMessages(null)
                }
                else -> {
                }
            }
            false
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(context)
    }

    private fun init(context: Context) {
        //设置不可取消，点击其他区域不能取消，实际中可以抽出去封装供外包设置
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        setContentView(R.layout.progress_layout)
        val params = window!!.attributes
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height =WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params
    }

    private fun fullScreenImmersive(view: View) {
        val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
        view.systemUiVisibility = uiOptions
    }

    override fun show() {
        mHandlerTime.sendEmptyMessageDelayed(dismissTime, 500)
    }

    override fun dismiss() {
        super.dismiss()
        isDismiss = true
        LogUtil("dismiss", "dismiss")
    }


    private fun showReallyDialog() {

        try {
            this.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            )
            super.show()
            LogUtil("show", "show")
            fullScreenImmersive(window!!.decorView)
            this.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil("printStackTrace", e.message)
        }
    }
}