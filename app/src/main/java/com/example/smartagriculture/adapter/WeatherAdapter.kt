package com.example.smartagriculture.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import cjh.weatherviewlibarary.WeatherView
import com.example.common.stringToBitmap
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
        val date2=holder.getView<TextView>(R.id.date2)
        val wea=holder.getView<TextView>(R.id.wea)
        val wea_iv=holder.getView<ImageView>(R.id.wea_iv)
        val wea_iv2=holder.getView<ImageView>(R.id.wea_iv2)
        val wea2=holder.getView<TextView>(R.id.wea2)
        val win=holder.getView<TextView>(R.id.win)
        val win_speed=holder.getView<TextView>(R.id.win_speed)
        val air=holder.getView<TextView>(R.id.air)
        val weatherView=holder.getView<WeatherView<MyWeatherData>>(R.id.weatherView)
        weatherView.layoutParams=LinearLayout.LayoutParams(
            width,
            height
        )
        when (position) {
            0 -> {
                date1.text=mContext.resources.getString(R.string.today)
            }
            1 ->{
                date1.text=mContext.resources.getString(R.string.tomorrow)
            }
            else -> {
                date1.text=mContext.resources.getString(R.string.after_tomorrow)
            }
        }
        date2.text=myWeatherData.date
        wea.text=myWeatherData.wea
        wea2.text=myWeatherData.wea
        win.text=myWeatherData.win
        win_speed.text=myWeatherData.win_speed
        when (myWeatherData.air_level) {
            "优" -> {
                air.background=mContext.getDrawable(R.drawable.cycle_green)
            }
            "良" -> {
                air.background=mContext.getDrawable(R.drawable.cycle_green)
            }
            else -> {
            }
        }
        air.text=myWeatherData.air_level

        wea_iv.setImageBitmap(stringToBitmap(myWeatherData.weatherUrl))
        wea_iv2.setImageBitmap(stringToBitmap(myWeatherData.weatherUrl))
        weatherView.setDatas(data,highDegree,lowDegree,position)

    }
}