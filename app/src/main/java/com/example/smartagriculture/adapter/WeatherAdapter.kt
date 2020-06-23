package com.example.smartagriculture.adapter

import android.content.Context
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import cjh.weatherviewlibarary.WeatherView
import com.example.common.BaseApplication
import com.example.smartagriculture.R
import com.example.smartagriculture.bean.MyWeatherData
import com.example.smartagriculture.myview.TextDrawable
import com.example.smartagriculture.util.Identification

/**
 * Created by ddy
 */
class WeatherAdapter<T>(context: Context?, layoutId: Int, data: MutableList<MyWeatherData>) :
    ListBaseAdapter<Any?>(context) {
    private var layoutId = 0
    var data:MutableList<MyWeatherData>
    init {
        this.layoutId = layoutId
        this.data=data
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
            BaseApplication.getWidth()/4,
            BaseApplication.getWidth()/3
        )
        date1.text=myWeatherData.date
        weatherView.setDatas(data,8,-8,position)


    }
}