package com.example.smartagriculture.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.common.BaseFragment
import com.example.smartagriculture.R
import com.example.smartagriculture.databinding.FragmentDistributionBinding
import com.example.smartagriculture.viewmodel.DistributionViewModel
import com.tianditu.android.maps.GeoPoint
import kotlinx.android.synthetic.main.fragment_distribution.*
import kotlinx.android.synthetic.main.fragment_main.*


/**
 * A simple [Fragment] subclass.
 */
class DistributionFragment : BaseFragment<DistributionViewModel, FragmentDistributionBinding>() {


    override fun initLayout(): Int {
        return R.layout.fragment_distribution
    }

    override fun initView(view: View) {
        viewModel = ViewModelProvider(requireActivity()).get(DistributionViewModel::class.java)
        //启用内置的缩放组件
//        distribution_mapview.setBuiltInZoomControls(true);
//        //得到MapView的控制权,可以用它控制和驱动平移和缩放
//        var mMapController = distribution_mapview.getController();
//        //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
//        var point = GeoPoint((39.915 * 1E6).toInt(), (116.404 * 1E6).toInt())
//        //设置地图中心点
//        mMapController.setCenter(point);
//        //设置地图缩放级别
//        mMapController.setZoom(12);
    }

    override fun initData() {
    }

}
