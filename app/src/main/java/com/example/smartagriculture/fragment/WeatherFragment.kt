package com.example.smartagriculture.fragment

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.base.BaseFragment
import com.example.common.bean.Bean
import com.example.common.bean.IP
import com.example.common.clickNoRepeat
import com.example.common.model.NoHttpRx
import com.example.common.replaceBlank
import com.example.smartagriculture.R
import com.example.smartagriculture.adapter.WeatherAdapter
import com.example.smartagriculture.bean.MyWeatherData
import com.example.smartagriculture.databinding.FragmentWeatherBinding
import com.example.smartagriculture.util.nav
import com.example.smartagriculture.viewmodel.DataViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.title_item.*


/**
 * A simple [Fragment] subclass.
 */
class WeatherFragment : BaseFragment<DataViewModel, FragmentWeatherBinding>() {


    var height:Int=0
    var width:Int=0
    lateinit var datas:MutableList<MyWeatherData>
    lateinit var weatherAdapter:WeatherAdapter
        override fun initLayout(): Int {
        return R.layout.fragment_weather
    }

    override fun initView(savedInstanceState: Bundle?) {
        viewModel=ViewModelProvider(requireActivity()).get(DataViewModel::class.java)
        textView.text=getString(R.string.weather_description)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        weather_recycler.layoutManager = linearLayoutManager
        datas = mutableListOf<MyWeatherData>()
        weather_recycler.post {
            height=weather_recycler.height
            width=weather_recycler.width
        }
    }

    override fun lazyLoadData() {
        viewModel.noHttpRx= NoHttpRx(this)
        viewModel.getIp()

    }

    override fun setListener() {
        back.clickNoRepeat {
            nav().navigateUp()
        }
    }

    override fun toData(flag: String?, `object`: String?) {
        super.toData(flag, `object`)
        when (flag) {
            "ip" -> {
                viewModel.getWeather(`object`?.let { replaceBlank(it) })
            }
            "天气" -> {
                var bean= Gson().fromJson(`object`, Bean::class.java)
                textView94.text=bean.data.detail.tem
                textView100.text=getString(R.string.weather_city,bean.data.detail.city)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    textView101.text = Html.fromHtml("<font color=\"#696969\" >风级</font><br/><br/><font color=\"#F2A51C\">${bean.data.detail.win_speed}</font>",
                        Html.FROM_HTML_MODE_LEGACY)
                    textView102.text = Html.fromHtml("<font color=\"#696969\">相对湿度</font><br/><br/><font color=\"#6F81FF\">${bean.data.detail.humidity}</font>",
                        Html.FROM_HTML_MODE_LEGACY)
                    textView103.text = Html.fromHtml("<font color=\"#696969\">空气质量</font><br/><br/><font color=\"#6F81FF\">${bean.data.detail.air_level}</font>",
                        Html.FROM_HTML_MODE_LEGACY)
                    textView104.text = Html.fromHtml("<font color=\"#696969\">大气压力</font><br/><br/><font color=\"#6F81FF\">${bean.data.detail.pressure}</font>",
                        Html.FROM_HTML_MODE_LEGACY)
                }else{
                    textView101.text = Html.fromHtml("<font color=\"#696969\">风级</font><br/><br/><font color=\"#F2A51C\">${bean.data.detail.win_speed}</font>")
                    textView102.text = Html.fromHtml("<font color=\"#696969\">相对湿度</font><br/><br/><font color=\"#6F81FF\">${bean.data.detail.humidity}</font>")
                    textView103.text = Html.fromHtml("<font color=\"#696969\">空气质量</font><br/><br/><font color=\"#6F81FF\">${bean.data.detail.air_level}</font>")
                    textView104.text = Html.fromHtml("<font color=\"#696969\">大气压力</font><br/><br/><font color=\"#6F81FF\">${bean.data.detail.pressure}</font>")
                }
                //高温
                var tem1=bean.data.list[0].tem1
                //低温
                var tem2=bean.data.list[0].tem2
                bean.data.list.forEach {
                    if (tem1<it.tem1) {
                        tem1=it.tem1
                    }
                    if (tem2>it.tem2) {
                        tem2=it.tem2
                    }
                    datas.add(MyWeatherData(it.tem1.toInt(),it.tem2.toInt(),it.date,it.wea_img,it.wea,it.win_speed,it.win[0],it.air_level))
                    weatherAdapter = WeatherAdapter(requireContext(),R.layout.beta_weather,datas,height/2-100,width/3,tem1.toInt(),tem2.toInt())
                    weather_recycler.adapter =weatherAdapter
                }
            }
            else -> {
            }
        }
    }

}
