package com.example.smartagriculture.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.common.spToPx
import com.example.smartagriculture.R
import com.example.smartagriculture.myview.TextDrawable
import com.example.smartagriculture.util.Identification

/**
 * Created by ddy
 */
class ParkAdapter(context: Context?, layoutId: Int) :
    ListBaseAdapter<Any?>(context) {
    private var layoutId: Int = 0

    init {
        this.layoutId = layoutId
    }

    private var onWarningMessageListener: ((view: View, position: Int,flag:Int) -> Unit)? = null

    fun setOnWMListener(onWarningMessageListener: ((view: View, position: Int,flag:Int) -> Unit)) {
        this.onWarningMessageListener = onWarningMessageListener

    }

    override fun getLayoutId(): Int {
        return layoutId
    }

    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {
        val titleText = holder.getView<TextDrawable>(R.id.textDrawable)
        val textView3 = holder.getView<TextView>(R.id.textView3)
        val textView4 = holder.getView<TextView>(R.id.textView4)
        val textView5 = holder.getView<TextView>(R.id.textView5)
        val textView6 = holder.getView<TextView>(R.id.textView6)
        val textView7 = holder.getView<TextView>(R.id.textView7)
        val relativeLayout = holder.getView<RelativeLayout>(R.id.relativeLayout)
        val relativeLayout2 = holder.getView<RelativeLayout>(R.id.relativeLayout2)
        var strMsg= listOf("3\n施肥","3\n施药","3\n农事","3\n采集","3\n巡园")
//        val strMsg = "<font color=\"#1BA17E\"><big>3</big></font><br><font color=\"#696969\">施肥</font>"
        var spList= mutableListOf<SpannableString>()
        strMsg.forEach {
            var sp = SpannableString(it)
            sp.setSpan( AbsoluteSizeSpan(28,false),0,it.length-2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            sp.setSpan( StyleSpan(Typeface.BOLD), 0, it.length-2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp.setSpan( ForegroundColorSpan(Color.rgb(27,161,126)), 0, it.length-2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp.setSpan( AbsoluteSizeSpan(22,false),it.length-2,it.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            spList.add(sp)
        }
//        textView3.text = Html.fromHtml(strMsg)
        textView3.text=spList[0]
        textView4.text=spList[1]
        textView5.text=spList[2]
        textView6.text=spList[3]
        textView7.text=spList[4]
        titleText.text = mDataList[position] as CharSequence?



        relativeLayout.setOnClickListener {
            onWarningMessageListener?.invoke(relativeLayout, position,Identification.WARNING)
        }
        relativeLayout2.setOnClickListener {
            onWarningMessageListener?.invoke(relativeLayout, position,Identification.MONITOR)
        }


    }
}