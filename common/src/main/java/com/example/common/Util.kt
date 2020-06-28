package com.example.common

import android.app.Activity
import android.app.Dialog
import android.app.PendingIntent.getActivity
import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter


open class ToastUtil<T>(message: T) {
    lateinit var mToast: Toast

    init {
        when (message) {
            is String -> makeToast(message)
            else -> makeToast(message.toString())
        }
    }

    open fun makeToast(message: String) {
        val view = View.inflate(BaseApplication.context, R.layout.toast, null)
        val textView = view.findViewById<TextView>(R.id.text111)
        //设置控件的宽高
        textView.background.alpha = 200
        textView.maxWidth = BaseApplication.getWidth() - 100
        val toast = Toast(BaseApplication.context)
        toast.view = view
        textView.text = message
        toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 100)
        toast.duration = Toast.LENGTH_SHORT
        toast.show()

    }
}


class LogUtil<T>(TAG: String, content: T) {

    init {
        when (content) {
            is String -> {
                Log.e(TAG, content)
            }
            else -> {
                Log.e(TAG, content.toString())
            }
        }
    }

}

/**
 * 防止重复点击事件 默认0.5秒内不可重复点击
 * @param interval 时间间隔 默认0.5秒
 * @param action 执行方法
 */
var lastClickTime = 0L
fun View.clickNoRepeat(interval: Long = 500, action: (view: View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastClickTime != 0L && (currentTime - lastClickTime < interval)) {
            return@setOnClickListener
        }
        lastClickTime = currentTime
        action(it)
    }
}

/**
 * 给adapter拓展的，防止重复点击item
 */
var adapterlastClickTime = 0L

fun LRecyclerViewAdapter.setNbOnItemClickListener(
    interval: Long = 1000,
    action: (view: View, position: Int) -> Unit
) {
    setOnItemClickListener { view, position ->
        val currentTime = System.currentTimeMillis()
        if (adapterlastClickTime != 0L && (currentTime - adapterlastClickTime < interval)) {
            return@setOnItemClickListener
        }
        adapterlastClickTime = currentTime
        action(view, position)
    }
}
/**
 * 将sp值转换为px值，保证文字大小不变
 *
 * @param spValue
 * @param fontScale
 *            （DisplayMetrics类中属性scaledDensity）
 * @return
 */
fun spToPx(context: Context, spValue: Int): Int {
    var fontScale= context.resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5).toInt()
}


/**
 * 隐藏软键盘
 */
fun hideSoftKeyboard(activity: Activity?) {
    activity?.let { act ->
        val view = act.currentFocus
        view?.let {
            LogUtil("软键盘","软键盘")
            val inputMethodManager =
                act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}


/**
 * 弹窗可使用第三方 gradle直接集成 'com.dou361.dialogui:jjdxm-dialogui:1.0.3'
 *
 * @param context     上下文
 * @param view        视图布局
 * @param widthValue  宽度比例
 * @param heightValue 高度比例
 * @param gravity     弹窗显示位置
 * @param style       动画样式
 * @param isCancel    是否可以点击取消弹窗
 * @return
 */

/**
 * 有圓角的自定义dialog
 */
class DialogCircle : Dialog {
    constructor(
        context: Context?,
        width: Int,
        height: Int,
        layout: View?,
        style: Int,
        gravity: Int
    ) : super(context!!, style) {
        if (layout != null) {
            setContentView(layout)
        }
        val params: WindowManager.LayoutParams = window!!.attributes
        params.width = width
        params.height = height
        params.gravity = gravity
        window!!.attributes = params
    }


}


fun getPop(
    context: Context?,
    view: View?,
    widthValue: Int,
    heightValue: Int,
    gravity: Int,
    style: Int,
    isCancel: Boolean
): DialogCircle {
    val dialogCircle = DialogCircle(
        context,
        BaseApplication.getWidth() / widthValue,
        BaseApplication.getHeight() / heightValue,
        view,
        R.style.dialog,
        gravity
    )
    dialogCircle.setCancelable(isCancel)
    dialogCircle.window!!.setWindowAnimations(style)
    dialogCircle.show()
    return dialogCircle
}