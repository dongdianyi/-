package com.example.smartagriculture.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.Html
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import com.example.common.LogUtil
import com.example.common.bean.BeanDataList
import com.example.common.bean.DataX
import com.example.common.bean.Row
import com.example.smartagriculture.R
import com.example.common.myview.TextDrawable
import com.example.common.data.Identification
import com.example.common.stringToBitmap

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

    @SuppressLint("SetTextI18n")
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
                if (data.isread == 1) {
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
                val dataList=mDataList[position]as DataX
                val textView10 = holder.getView<TextView>(R.id.textView10)
                val textView25 = holder.getView<TextView>(R.id.textView25)
                val textView26 = holder.getView<TextView>(R.id.textView26)
                val imageView3 = holder.getView<ImageView>(R.id.imageView3)
                textView10.text =dataList.title
                textView25.text =dataList.content
                textView26.text =dataList.creattime
                when (dataList.statu) {
                    "0" -> {
                        imageView3.visibility = VISIBLE
                    }
                    else -> {
                        imageView3.visibility = GONE
                    }
                }
            }
            Identification.PRODUCT -> {
                val rows=mDataList[position]as Row
                val imageView5 = holder.getView<ImageView>(R.id.imageView5)
                val textView29 = holder.getView<TextView>(R.id.textView29)
                val textView30 = holder.getView<TextView>(R.id.textView30)
                val textView31 = holder.getView<TextView>(R.id.textView31)
                val textDrawable7 = holder.getView<TextDrawable>(R.id.textDrawable7)
                textView29.text = rows.productName
                textView30.text = rows.varieties
                if (rows.productGradeNote!=null) {
                    rows.productGradeNote!!.forEach {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            textView31.append(Html.fromHtml("<font color=\"#009B97\">${it.name}: </font><font color=\"#title_gray\">${it.note}</font>",Html.FROM_HTML_MODE_LEGACY))
                        }else{
                            textView31.append(Html.fromHtml("<font color=\"#009B97\">${it.name}</font><font color=\"#title_gray\">${it.note}</font>"))
                        }
                    }
                }
                textDrawable7.text = rows.createDate

                imageView5.setImageBitmap(stringToBitmap(rows.productPictureUrl))
            }
            Identification.STOCK -> {
                val dataList=mDataList[position]as BeanDataList
                val textDrawable9 = holder.getView<TextView>(R.id.textDrawable9)
                val textView32 = holder.getView<TextView>(R.id.textView32)
                val textView33 = holder.getView<TextView>(R.id.textView33)
                textDrawable9.text = dataList.name
                textView32.text=dataList.stock
                textView33.text=dataList.unitName
            }
            else -> {

            }
        }


    }
}