package com.example.smartagriculture.adapter

import android.content.Context
import android.text.Html
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.smartagriculture.R
import com.example.smartagriculture.myview.TextDrawable

/**
 * Created by ddy
 */
class ParkAdapter(context: Context?, layoutId: Int) :
    ListBaseAdapter<Any?>(context) {
    private var layoutId: Int = 0

    init {
        this.layoutId = layoutId
    }

    private var onWarningMessageListener: ((view: View, position: Int) -> Unit)? = null

    fun setOnWMListener(onWarningMessageListener: ((view: View, position: Int) -> Unit)) {
        this.onWarningMessageListener = onWarningMessageListener

    }

    override fun getLayoutId(): Int {
        return layoutId
    }

    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {
        val titleText = holder.getView<TextDrawable>(R.id.textDrawable)
        val textView3 = holder.getView<TextView>(R.id.textView3)
        val relativeLayout = holder.getView<RelativeLayout>(R.id.relativeLayout)
        val relativeLayout2 = holder.getView<RelativeLayout>(R.id.relativeLayout2)
        val strMsg =
            "<font color=\"#1BA17E\"><big>3</big></font><br><font color=\"#696969\">施肥</font>"
        textView3.text = Html.fromHtml(strMsg)
        titleText.text = mDataList[position] as CharSequence?



        relativeLayout.setOnClickListener {
            onWarningMessageListener?.invoke(relativeLayout, position)
        }


    }
}