package com.example.smartagriculture.adapter

import android.content.Context
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.smartagriculture.R
import com.example.smartagriculture.myview.TextDrawable
import com.example.smartagriculture.util.Identification

/**
 * Created by ddy
 */
class DataAdapter(context: Context?, layoutId: Int, id: Int) :
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