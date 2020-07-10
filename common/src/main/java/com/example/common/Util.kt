package com.example.common

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.example.common.base.BaseApplication
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import java.net.*
import java.util.*
import java.util.regex.Pattern


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
    var fontScale = context.resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5).toInt()
}

fun dip2px(context: Context, dpValue: Float): Int {
    val scale: Float = context.getResources().getDisplayMetrics().density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 隐藏软键盘
 */
fun hideSoftKeyboard(activity: Activity?) {
    activity?.let { act ->
        val view = act.currentFocus
        view?.let {
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
    isCancel: Boolean,
    i: Int,
    i1: Int
): DialogCircle {
    val dialogCircle = DialogCircle(
        context,
        BaseApplication.getWidth() / widthValue+i1,
        BaseApplication.getHeight() / heightValue+i,
        view,
        R.style.dialog,
        gravity
    )
    dialogCircle.setCancelable(isCancel)
    dialogCircle.window!!.setWindowAnimations(style)
    return dialogCircle
}


/**
 * base64转图片
 * @param string  base64串
 * @return
 */
fun stringToBitmap(string: String): Bitmap? {
    var bitmap: Bitmap? = null
    try {
        val bitmapArray = Base64.decode(
            string.split(",").toTypedArray()[1],
            Base64.DEFAULT
        )
        bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.size)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return bitmap
    /**
     *bitmap转为drawable
     */
//    BitmapDrawable(getResources(), bitmap)
}

object NetUtil {
    /**
     * 没有连接网络
     */
    const val NETWORK_NONE = -1

    /**
     * 移动网络
     */
    const val NETWORK_MOBILE = 0

    /**
     * 无线网络
     */
    const val NETWORK_WIFI = 1

    fun getNetWorkState(context: Context): Int {

        // 得到连接管理器对象
        val connectivityManager = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager
            .activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
                return NETWORK_WIFI
            } else if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                return NETWORK_MOBILE
            }
        } else {
            return NETWORK_NONE
        }
        return NETWORK_NONE
    }
}

object NetWorkUtils {
    /**
     * 检查网络是否可用
     *
     * @param paramContext
     * @return
     */
    @SuppressLint("WrongConstant")
    fun checkEnable(paramContext: Context): Boolean {
        val i = false
        val localNetworkInfo = (paramContext
            .getSystemService("connectivity") as ConnectivityManager).activeNetworkInfo
        return localNetworkInfo != null && localNetworkInfo.isAvailable
    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    fun int2ip(ipInt: Int): String {
        val sb = StringBuilder()
        sb.append(ipInt and 0xFF).append(".")
        sb.append(ipInt shr 8 and 0xFF).append(".")
        sb.append(ipInt shr 16 and 0xFF).append(".")
        sb.append(ipInt shr 24 and 0xFF)
        return sb.toString()
    }

    /**
     * 获取当前ip地址
     *
     * @param context
     * @return
     */
    fun getLocalIpAddress(context: Context): String {
        return try {
            val wifiManager = context.applicationContext
                .getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            val i = wifiInfo.ipAddress
            int2ip(i)
        } catch (ex: java.lang.Exception) {
            """ 获取IP出错鸟!!!!请保证是WIFI,或者请重新打开网络!
${ex.message}"""
        }
        // return null;
    }

    //GPRS连接下的ip
    fun getLocalIpAddress(): String? {
        try {
            val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf: NetworkInterface = en.nextElement()
                val enumIpAddr: Enumeration<InetAddress> = intf.inetAddresses
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress: InetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress) {
                        return inetAddress.hostAddress.toString()
                    }
                }
            }
        } catch (ex: SocketException) {
            LogUtil("WifiPreference IpAddress", ex.toString())
        }
        return null
    }
}

fun replaceBlank( str:String):String {
    var dest = "";
    val p = Pattern.compile("\t|\r|\n")
    val m = p.matcher(str)
    dest = m.replaceAll("")
    return dest
}
