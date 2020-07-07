package com.example.smartagriculture.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.amap.api.maps.AMap
import com.amap.api.maps.model.MyLocationStyle
import com.example.common.base.BaseFragment
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentDistributionBinding
import com.example.smartagriculture.viewmodel.DistributionViewModel
import kotlinx.android.synthetic.main.fragment_distribution.*


/**
 * A simple [Fragment] subclass.
 */
class DistributionFragment : BaseFragment<DistributionViewModel, FragmentDistributionBinding>() {

    lateinit var myLocationStyle: MyLocationStyle

    override fun initLayout(): Int {
        return R.layout.fragment_distribution
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity()).get(DistributionViewModel::class.java)
        map.onCreate(savedInstanceState) // 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。

        //初始化地图控制器对象
        val aMap: AMap = map.map

        myLocationStyle =
            MyLocationStyle() //初始化定位蓝点样式类


        // myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。

        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)//定位一次，且将视角移动到地图中心点。
        aMap.myLocationStyle = myLocationStyle //设置定位蓝点的Style

        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.isMyLocationEnabled = true // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。


    }

    override fun lazyLoadData() {
    }

    override fun onDestroy() {
        super.onDestroy()
        map.onDestroy()
    }

}
