package com.example.smartagriculture.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.example.common.LogUtil
import com.example.common.ToastUtil
import com.example.common.base.BaseFragment
import com.example.common.bean.Bean
import com.example.common.clickNoRepeat
import com.example.common.data.Identification
import com.example.common.model.NoHttpRx
import com.example.common.myview.CustomDialog
import com.example.common.myview.TextDrawable
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.AttendanceAdapter
import com.example.smartagriculture.databinding.FragmentAttendanceBinding
import com.example.smartagriculture.util.CustomDatePicker
import com.example.smartagriculture.viewmodel.AttendanceViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.attendance_peasant.*
import kotlinx.android.synthetic.main.fragment_attendance.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AttendanceFragment : BaseFragment<AttendanceViewModel, FragmentAttendanceBinding>(),
    AMapLocationListener {

    lateinit var attendanceAdapter: AttendanceAdapter
    private lateinit var mTimerPicker: CustomDatePicker
    private lateinit var longitude: String
    private lateinit var latitude: String
    lateinit var approval_constraint: View
    lateinit var approved_constraint: View
    lateinit var constraintLayout14: View
    lateinit var textView65: View
    lateinit var textView71: View
    lateinit var textView72: View
    lateinit var textView73: View
    lateinit var textView74: View
    lateinit var textDrawable13: TextDrawable

    //声明mlocationClient对象
    var mlocationClient: AMapLocationClient? = null

    //声明mLocationOption对象
    var mLocationOption: AMapLocationClientOption? = null


    override fun initLayout(): Int {
        return R.layout.fragment_attendance
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(AttendanceViewModel::class.java)
        dataBinding.data = viewModel
        dataBinding.lifecycleOwner = this
        attendanceAdapter =
            AttendanceAdapter(
                requireContext(),
                R.layout.attendance_manager_item,
                Identification.ATTENDANCE_MANAGER
            )
        mLRecycleViewAdapter = LRecyclerViewAdapter(attendanceAdapter)
        attendance_recycler.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.mm_2)
            .setColorResource(R.color.bg)
            .build()
        attendance_recycler.addItemDecoration(divider)
        attendance_recycler.layoutManager = LinearLayoutManager(requireContext())
        if (viewModel.getType().value == 1) {//管理员
            val view = LayoutInflater.from(requireContext()).inflate(
                R.layout.attendance_manager,
                activity?.findViewById(android.R.id.content), false
            )
            mLRecycleViewAdapter.addHeaderView(view)

            approval_constraint = view.findViewById<ConstraintLayout>(R.id.constraintLayout14)
            approved_constraint = view.findViewById<ConstraintLayout>(R.id.constraintLayout14)
            approval_constraint.clickNoRepeat {
                viewModel.toLeaveRecord(it, Identification.ATTENDANCE_MANAGER_APPROVAL)
            }
            approved_constraint.clickNoRepeat {
                viewModel.toLeaveRecord(it, Identification.ATTENDANCE_MANAGER_APPROVED)
            }
            val dataList = listOf<String>("30", "50", "60")
            attendanceAdapter.setDataList(dataList)
        } else {
            val view = LayoutInflater.from(requireContext()).inflate(
                R.layout.attendance_peasant,
                activity?.findViewById(android.R.id.content), false
            )
            mLRecycleViewAdapter.addHeaderView(view)
            constraintLayout14 = view.findViewById<LinearLayout>(R.id.constraintLayout14)
            textView65 = view.findViewById<TextView>(R.id.textView65)
            textView71 = view.findViewById<TextView>(R.id.textView71)
            textView72 = view.findViewById<TextView>(R.id.textView72)
            textView73 = view.findViewById<TextView>(R.id.textView73)
            textView74 = view.findViewById<TextView>(R.id.textView74)
            textDrawable13 = view.findViewById<TextDrawable>(R.id.textDrawable13)
            constraintLayout14.isEnabled = false
            constraintLayout14.clickNoRepeat {
                viewModel.punchRecord()
            }
            textView65.clickNoRepeat {
                viewModel.toAttendanceDetails(it)
            }
            textView71.clickNoRepeat {
                viewModel.toLeaveApplication(it)
            }
            textView72.clickNoRepeat {
                viewModel.toLeaveRecord(it, Identification.ATTENDANCE_PEASANT_LEAVE)
            }
            textView73.clickNoRepeat {
                viewModel.toLeaveApplication(it)
            }
            textView74.clickNoRepeat {
                viewModel.toLeaveRecord(it, Identification.ATTENDANCE_PEASANT_LEAVE)
            }
            textDrawable13.clickNoRepeat {
                mTimerPicker.show("2019-01-01 00:00")
            }
        }


    }

    override fun onResume() {
        super.onResume()
        //启动定位
        if (viewModel.getType().value == 1) {//管理员
        } else {
            mlocationClient!!.startLocation()
        }

    }

    override fun onPause() {
        super.onPause()
        if (viewModel.getType().value == 1) {//管理员
        } else {
            mlocationClient!!.stopLocation()
        }
    }

    override fun lazyLoadData() {
        if (null == mDialog) {
            mDialog = CustomDialog(
                context,
                R.style.CustomDialog
            )
            (mDialog as CustomDialog).show()
        }
        if (viewModel.getType().value == 1) {//管理员
        } else {
            initTimerPicker()

            mlocationClient = AMapLocationClient(requireActivity())
//初始化定位参数
            mLocationOption = AMapLocationClientOption()
//设置定位监听
            mlocationClient!!.setLocationListener(this)
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption!!.locationMode =
                AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
//设置定位间隔,单位毫秒,默认为2000ms
            mLocationOption!!.interval = 5000
//设置定位参数
            mlocationClient!!.setLocationOption(mLocationOption)
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除

        }
    }

    override fun setListener() {
        attendance_recycler.setPullRefreshEnabled(false)
        attendance_recycler.setLoadMoreEnabled(false)
        attendanceAdapter.setOnADListener { view, position, flag ->
            viewModel.toSelect(view)
        }

    }


    fun initTimerPicker() {
        val beginTime = "2020-01-01 00:00"
        val endTime = "2022-12-31 23:59"

        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mTimerPicker = CustomDatePicker(
            requireContext(),
            CustomDatePicker.ResultHandler { time ->
//                startTimeNum = DateUtil.date2TimeStamp(time, "yyyy-MM-dd")
                textDrawable13.text = time.substring(0, 11)
            }, beginTime, endTime
        )

        // 允许点击屏幕或物理返回键关闭
        mTimerPicker.setCancelable(true)
        // 只显示时和分
        mTimerPicker.showSpecificTime4(true)
        // 允许循环滚动
        mTimerPicker.setIsLoop(true)
        // 允许滚动动画
        mTimerPicker.setCanShowAnim(true)
    }

    @SuppressLint("SetTextI18n")
    override fun onLocationChanged(amapLocation: AMapLocation?) {
        LogUtil("amaplocation", amapLocation)
        if (amapLocation != null) {
            if (amapLocation.errorCode == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.locationType //获取当前定位结果来源，如网络定位结果，详见定位类型表
                latitude = amapLocation.latitude.toString() //获取纬度
                longitude = amapLocation.longitude.toString() //获取经度
                amapLocation.accuracy //获取精度信息
                val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val date = Date(amapLocation.time)
                df.format(date) //定位时间
                if (!TextUtils.isEmpty(amapLocation.address)) {
                    textDrawable11.text = amapLocation.address
                    mlocationClient!!.stopLocation()
                    viewModel.noHttpRx = NoHttpRx(this)
                    viewModel.getPunchRecord(
                        longitude,
                        latitude
                    )
                }

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                LogUtil(
                    "AmapError", ("location Error, ErrCode:"
                            + amapLocation.errorCode) + ", errInfo:"
                            + amapLocation.errorInfo
                )
                ToastUtil(amapLocation.errorInfo)
            }
        }
    }

    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        when (flag) {
            "打卡状态" -> {
                mDialog?.dismiss()
                val bean = Gson().fromJson(`object`, Bean::class.java)
                textDrawable10.text = bean.data.title
                textView64.text = bean.data.btnContent
                textView138.text = bean.data.msg
                when (bean.data.state) {
                    1, 6, 7 -> {
                        constraintLayout14.setBackgroundResource(R.mipmap.attendance_bg3)
                        constraintLayout14.isEnabled = false
                    }
                    else -> {
                        constraintLayout14.setBackgroundResource(R.mipmap.attendance_bg1)
                        constraintLayout14.isEnabled = true
                    }
                }
            }
            "打卡" -> {
                viewModel.getPunchRecord(
                    longitude,
                    latitude
                )
            }
            else -> {
            }
        }
    }

    override fun fail(isNetWork: Boolean, flag: String?, t: Throwable?) {
        super.fail(isNetWork, flag, t)
        when (flag) {
            "打卡状态" -> {
                mDialog?.dismiss()
            }
            else -> {
            }
        }
    }
}
