package com.example.smartagriculture.adapter

import android.content.Context
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import com.example.common.bean.DataX
import com.example.smartagriculture.R
import com.example.common.myview.TextDrawable
import com.example.common.data.Identification

/**
 * Created by ddy
 */
class HomeAdapter(context: Context?, layoutId: Int, id: Int) :
    ListBaseAdapter<Any?>(context) {
    private var layoutId = 0
    private var id = 0

    init {
        this.layoutId = layoutId
        this.id = id
    }

    override fun getLayoutId(): Int {
        return layoutId
    }

    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {

        when (id) {
            Identification.WARNINGLIST -> {
                val data: DataX = mDataList[position] as DataX
                val textView10 = holder.getView<TextView>(R.id.textView10)
                val textView25 = holder.getView<TextView>(R.id.textView25)
                val textView26 = holder.getView<TextView>(R.id.textView26)
                val imageView3 = holder.getView<ImageView>(R.id.imageView3)
                val imageView2 = holder.getView<ImageView>(R.id.imageView2)
                textView10.text = data.title
                textView25.text = data.warnmessage
                textView26.text = data.warntime
                if (data.state == 0) {
                    imageView3.visibility = GONE
                } else {
                    imageView3.visibility = VISIBLE
                }
                when (data.warntype) {
                    Identification.WARNEQUIPMENT -> {
                        imageView2.setImageResource(R.mipmap.equipment_iv)
                    }
                    Identification.WARNEWEATHER -> {
                        imageView2.setImageResource(R.mipmap.weather_iv)
                    }
                    Identification.WARNILLNESS -> {
                        imageView2.setImageResource(R.mipmap.illness_iv)
                    }
                    Identification.WARNFARM -> {
                        imageView2.setImageResource(R.mipmap.farm_iv)
                    }
                    else -> {
                        imageView2.setImageResource(R.mipmap.warn_iv)
                    }
                }


            }
            Identification.NOTICE -> {
                val textView10 = holder.getView<TextView>(R.id.textView10)
                val textView = holder.getView<TextView>(R.id.textView25)
                val textView26 = holder.getView<TextView>(R.id.textView26)
                val imageView3 = holder.getView<ImageView>(R.id.imageView3)
                textView10.text = mDataList[position].toString()
            }
            Identification.PRODUCT -> {
                val textView29 = holder.getView<TextView>(R.id.textView29)
                textView29.text = mDataList[position].toString()
            }
            Identification.STOCK -> {
                val textDrawable9 = holder.getView<TextDrawable>(R.id.textDrawable9)
                textDrawable9.text = mDataList[position].toString()
            }
            else -> {

            }
        }


    }
}