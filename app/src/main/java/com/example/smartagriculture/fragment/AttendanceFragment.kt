package com.example.smartagriculture.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.example.common.LogUtil
import com.example.common.base.BaseFragment
import com.example.common.data.Identification
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.AttendanceAdapter
import com.example.smartagriculture.databinding.FragmentAttendanceBinding
import com.example.smartagriculture.util.CustomDatePicker
import com.example.smartagriculture.viewmodel.AttendanceViewModel
import com.github.jdsjlzx.ItemDecoration.DividerDecoration
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import kotlinx.android.synthetic.main.attendance_manager.*
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

    //声明mlocationClient对象
    var mlocationClient: AMapLocationClient? = null

    //声明mLocationOption对象
    var mLocationOption: AMapLocationClientOption? = null


    override fun initLayout(): Int {
        return R.layout.fragment_attendance
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(AttendanceViewModel::class.java)
        include6.visibility = View.VISIBLE
        include7.visibility = View.GONE
        attendanceAdapter =
            AttendanceAdapter(
                requireContext(),
                R.layout.attendance_manager_item,
                Identification.ATTENDANCE_MANAGER
            )
        mLRecycleViewAdapter = LRecyclerViewAdapter(attendanceAdapter)
        val dataList = listOf<String>("30", "50", "60")
        attendanceAdapter.setDataList(dataList)
        attendance_manager_recycler.adapter = mLRecycleViewAdapter

        val divider: DividerDecoration = DividerDecoration.Builder(requireContext())
            .setHeight(R.dimen.mm_2)
            .setColorResource(R.color.bg)
            .build()
        attendance_manager_recycler.addItemDecoration(divider)
        attendance_manager_recycler.layoutManager = LinearLayoutManager(requireContext())
        initTimerPicker()

        mlocationClient = AMapLocationClient(requireActivity())
//初始化定位参数
        mLocationOption = AMapLocationClientOption()
//设置定位监听
        mlocationClient!!.setLocationListener(this)
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption!!.interval = 2000
//设置定位参数
        mlocationClient!!.setLocationOption(mLocationOption)
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除

    }

    override fun onResume() {
        super.onResume()
        //启动定位

        mlocationClient!!.startLocation()

    }

    override fun onPause() {
        super.onPause()
        mlocationClient!!.stopLocation()
    }

    override fun lazyLoadData() {

    }

    override fun setListener() {
        textView65.setOnClickListener {
            viewModel.toAttendanceDetails(it)
        }
        textView71.setOnClickListener {
            viewModel.toLeaveApplication(it)
        }
        textView72.setOnClickListener {
            viewModel.toLeaveRecord(it, Identification.ATTENDANCE_PEASANT_LEAVE)
        }

        approval_constraint.setOnClickListener {
            viewModel.toLeaveRecord(it, Identification.ATTENDANCE_MANAGER_APPROVAL)
        }
        approved_constraint.setOnClickListener {
            viewModel.toLeaveRecord(it, Identification.ATTENDANCE_MANAGER_APPROVED)
        }
        attendanceAdapter.setOnADListener { view, position, flag ->
            viewModel.toSelect(view)
        }
        textDrawable13.setOnClickListener {
            mTimerPicker.show("2019-01-01 00:00")
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

    override fun onLocationChanged(amapLocation: AMapLocation?) {
        if (amapLocation != null) {
            if (amapLocation.errorCode == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.locationType //获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.latitude //获取纬度
                amapLocation.longitude //获取经度
                amapLocation.accuracy //获取精度信息
                val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val date = Date(amapLocation.time)
                df.format(date) //定位时间
                LogUtil("Amap",amapLocation.city+amapLocation.address)
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                LogUtil(
                    "AmapError", ("location Error, ErrCode:"
                            + amapLocation.errorCode) + ", errInfo:"
                            + amapLocation.errorInfo
                )
            }
        }
    }

}
