package com.example.common

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.alibaba.android.arouter.launcher.ARouter
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.onAdaptListener
import me.jessyan.autosize.unit.Subunits
import me.jessyan.autosize.utils.AutoSizeLog
import me.jessyan.autosize.utils.ScreenUtils
import java.util.*
import kotlin.math.sqrt

/**
 * @author ddy
 */
open class BaseApplication : Application() {
    companion object {

        private var width = 0
        private var height = 0
        private var walgreenWidth = 0
        private var screenHeight = 0
        var application: BaseApplication? =null
        var context: Context? = null

        fun getWidth(): Int {
            return width
        }

        fun getHeight(): Int {
            return height
        }
        fun getWalgreenWidth(): Int {
            return walgreenWidth
        }

        fun getScreenHeight(): Int {
            return screenHeight
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        application=this
        screen()
        configUnits()
        //不同模块之间跳转使用
        if (AutoSizeLog.isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()       // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }

    private fun configUnits() {
        //当 App 中出现多进程, 并且您需要适配所有的进程, 就需要在 App 初始化时调用 initCompatMultiProcess()
        AutoSize.initCompatMultiProcess(this)
        //如果在某些特殊情况下出现 InitProvider 未能正常实例化, 导致 AndroidAutoSize 未能完成初始化
//可以主动调用 AutoSize.checkAndInit(this) 方法, 完成 AndroidAutoSize 的初始化后即可正常使用
        //初始化之前要重写 <provider
        //            android:name="me.jessyan.autosize.InitProvider"
        //            android:authorities="${applicationId}.autosize-init-provider"
        //            android:exported="false"
        //            android:multiprocess="true"
        //            tools:node="remove"/>
        //否则会主动初始化
        AutoSize.checkAndInit(this)
//        如何控制 AndroidAutoSize 的初始化，让 AndroidAutoSize 在某些设备上不自动启动？https://github.com/JessYanCoding/AndroidAutoSize/issues/249
        /**
         * 以下是 AndroidAutoSize 可以自定义的参数, [AutoSizeConfig] 的每个方法的注释都写的很详细
         * 使用前请一定记得跳进源码，查看方法的注释, 下面的注释只是简单描述!!!
         */
        AutoSizeConfig.getInstance().unitsManager.isSupportDP = false
        AutoSizeConfig.getInstance().unitsManager.isSupportSP = false
        AutoSizeConfig.getInstance().unitsManager.supportSubunits = Subunits.MM
        //打开对fragment修改尺寸的支持
        AutoSizeConfig.getInstance().isCustomFragment = true;
        AutoSizeConfig.getInstance()
            .setExcludeFontScale(true)
            .onAdaptListener = object : onAdaptListener {
            override fun onAdaptBefore(
                target: Any,
                activity: Activity
            ) { //使用以下代码, 可以解决横竖屏切换时的屏幕适配问题
                //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
//系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
                AutoSizeConfig.getInstance().screenWidth = ScreenUtils.getScreenSize(activity)[0]
                AutoSizeConfig.getInstance().screenHeight = ScreenUtils.getScreenSize(activity)[1]
                AutoSizeLog.d(
                    String.format(
                        Locale.ENGLISH,
                        "%s onAdaptBefore!",
                        target.javaClass.name
                    )
                )
            }

            override fun onAdaptAfter(
                target: Any,
                activity: Activity
            ) {
                AutoSizeLog.d(
                    String.format(
                        Locale.ENGLISH,
                        "%s onAdaptAfter!",
                        target.javaClass.name
                    )
                )
            }
        } //是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
//                .setLog(false)
//是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
//AutoSize 会将屏幕总高度减去状态栏高度来做适配
//设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏高度
//在全面屏或刘海屏幕设备中, 获取到的屏幕高度可能不包含状态栏高度, 所以在全面屏设备中不需要减去状态栏高度，所以可以 setUseDeviceSize(true)
//                .setUseDeviceSize(true)
//是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
//                .setBaseOnWidth(false)
//设置屏幕适配逻辑策略类, 一般不用设置, 使用框架默认的就好
//                .setAutoAdaptStrategy(new AutoAdaptStrategy())
    }

    private fun screen() {
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)
        // 屏幕宽度（像素）
        width = dm.widthPixels
        // 屏幕高度（像素）
        height = dm.heightPixels
        val density = dm.density // 屏幕密度（0.75 / 1.0 / 1.5）
        // 屏幕密度dpi（120 / 160 / 240）
        val densityDpi = dm.densityDpi
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        walgreenWidth = ((width / density).toInt()) // 屏幕宽度(dp)
        // 屏幕高度(dp)
        screenHeight = ((height / density).toInt())
        LogUtil(
            "屏幕尺寸",
            (sqrt((2250 * 2250 + 4002 * 4002).toDouble() )/ 25.4).toString()+
                    "\n"+(sqrt((750 * 750 + 1334 * 1334).toDouble() )/ 25.4).toString()+
            "\n"+(sqrt((1125 * 1125 + 2001 * 2001).toDouble() )/ 25.4).toString()
                 + "\n屏幕宽度（像素）：" + width
                    + "\n屏幕高度（像素）：" + height
                    + "\n屏幕密度（0.75 / 1.0 / 1.5）" + density
                    + "\n屏幕密度dpi（120 / 160 / 240）：" + densityDpi
                    + "\n屏幕宽度（dp）：" + walgreenWidth
                    + "\n屏幕高度（dp）：" + screenHeight
        )
    }

}