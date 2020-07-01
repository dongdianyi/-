package com.example.smartagriculture.adapter

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import cjh.weatherviewlibarary.WeatherView
import com.example.smartagriculture.R
import com.example.smartagriculture.bean.MyWeatherData

/**
 * Created by ddy
 */
class WeatherAdapter(context: Context?, layoutId: Int, data: MutableList<MyWeatherData>,height:Int,width:Int,highDegree:Int,lowDegree:Int) :
    ListBaseAdapter<Any?>(context) {
    private var layoutId = 0
    var data:MutableList<MyWeatherData>
    var height:Int
    var width:Int
    var highDegree:Int
    var lowDegree:Int
    init {
        this.layoutId = layoutId
        this.data=data
        this.height=height
        this.width=width
        this.highDegree=highDegree
        this.lowDegree=lowDegree
        setDataList(data as Collection<Any?>?)
    }

    override fun getLayoutId(): Int {
        return layoutId
    }

    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {

       var myWeatherData=mDataList[position]as MyWeatherData
        val date1=holder.getView<TextView>(R.id.date1)
        val weatherView=holder.getView<WeatherView<MyWeatherData>>(R.id.weatherView)
        weatherView.layoutParams=LinearLayout.LayoutParams(
            width,
            height
        )
        date1.text=myWeatherData.date
        weatherView.setDatas(data,highDegree,lowDegree,position)


    }
}