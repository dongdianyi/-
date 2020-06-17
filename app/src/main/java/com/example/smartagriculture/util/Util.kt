package com.example.smartagriculture.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.common.BaseApplication
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.DropDownAdapter
import com.example.smartagriculture.myview.DropDownView
import com.example.smartagriculture.myview.TextDrawable
import kotlinx.android.synthetic.main.fragment_home.*


fun ViewPager2.init(
    fragment: Fragment,
    fragments: ArrayList<Fragment>,
    isUserInputEnabled: Boolean = true
): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = isUserInputEnabled
    //设置适配器
    adapter = object : FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int) = fragments[position]
        override fun getItemCount() = fragments.size
    }
    return this
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

/**
 * 弹出框大小控制
 */
object DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }
}
fun Fragment.nav(): NavController {
    return NavHostFragment.findNavController(this)
}

fun nav(view: View): NavController {
    return Navigation.findNavController(view)
}
fun nav(activity: Activity,resId: Int): NavController {
    return Navigation.findNavController(activity,resId)
}

