package com.example.smartagriculture.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.smartagriculture.R
import com.example.smartagriculture.bean.CreatChat
import com.example.smartagriculture.util.Identification

/**
 * Created by ddy
 */
class AttendanceAdapter(context: Context?, layoutId: Int, id: Int) :
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
            Identification.ATTENDANCE_PEASANT_CLOCK -> {
                val textView74 = holder.getView<TextView>(R.id.textView74)
                textView74.text = mDataList[position].toString()
            }
            Identification.ATTENDANCE_PEASANT_LEAVE -> {
                val textView84 = holder.getView<TextView>(R.id.textView84)
                textView84.text = mDataList[position].toString()
            }
            else -> {

            }
        }


    }

}