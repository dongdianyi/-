package com.example.smartagriculture.adapter

import android.annotation.SuppressLint
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
import android.view.View.GONE
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.common.LogUtil
import com.example.common.spToPx
import com.example.smartagriculture.R
import com.example.smartagriculture.bean.Data
import com.example.smartagriculture.myview.ParkView
import com.example.smartagriculture.myview.TextDrawable
import com.example.smartagriculture.util.Identification
import java.math.BigDecimal
import java.util.*

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

    @SuppressLint("SetTextI18n")
    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {
        var data:Data= mDataList[position] as Data
        val titleText = holder.getView<TextDrawable>(R.id.textDrawable)
        val textView2 = holder.getView<TextView>(R.id.textView2)
        val textView3 = holder.getView<TextView>(R.id.textView3)
        val textView4 = holder.getView<TextView>(R.id.textView4)
        val textView5 = holder.getView<TextView>(R.id.textView5)
        val textView6 = holder.getView<TextView>(R.id.textView6)
        val textView7 = holder.getView<TextView>(R.id.textView7)
        val textView12 = holder.getView<TextView>(R.id.textView12)
        val textView13 = holder.getView<TextView>(R.id.textView13)
        val textView14 = holder.getView<TextView>(R.id.textView14)
        val textView15= holder.getView<TextView>(R.id.textView15)
        val park_view= holder.getView<ParkView>(R.id.park_view)
        val relativeLayout = holder.getView<RelativeLayout>(R.id.relativeLayout)
        val relativeLayout2 = holder.getView<RelativeLayout>(R.id.relativeLayout2)

        var strMsg= mutableListOf("${data.useFertilizerNum}\n施肥","${data.usePesticidesNum}\n施药"
            ,"${data.farmingNum}\n农事","${data.collectionNum}\n采集","${data.patrolNum}\n巡园")
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
        textView2.text= data.planTaskNum.toString() + "\n待做"
        titleText.text = data.parkName
        if (data.warningCount <= 0) {
            textView12.visibility= GONE
        }else{
            textView12.text=data.warningCount.toString()
        }
        if (data.attr!=null) {
            var lat= mutableListOf<String>()
            var lnt= mutableListOf<String>()
            data.attr!!.split(",").indices.forEach {
                if (it%2==0) {
                    lat.add(data.attr!!.split(",")[it])
                }else{
                    lnt.add(data.attr!!.split(",")[it])
                }
            }
            var latBigNum=BigDecimal(lat[0])
            var lntBigNum=BigDecimal(lnt[0])
            var latSmalNum=BigDecimal(lat[0])
            var lntSmalNum=BigDecimal(lnt[0])
            lat.forEach {
                if (it.toBigDecimal().compareTo(latBigNum)==1) {
                    latBigNum=it.toBigDecimal()
                }
                if (it.toBigDecimal().compareTo(latSmalNum)==-1) {
                    latSmalNum=it.toBigDecimal()
                }
            }
            lnt.forEach {
                if (it.toBigDecimal().compareTo(lntBigNum)==1) {
                    lntBigNum=it.toBigDecimal()
                }
                if (it.toBigDecimal().compareTo(lntSmalNum)==-1) {
                    lntSmalNum=it.toBigDecimal()
                }
            }
            LogUtil("最大",latBigNum.toString()+lntBigNum)
            LogUtil("最小",latSmalNum.toString()+lntSmalNum)
        }

        data.attr?.let { park_view.setPoint(it) }

        park_view.post {
            var height=park_view.height
            var width=park_view.width
        }




        relativeLayout.setOnClickListener {
            onWarningMessageListener?.invoke(relativeLayout, position,Identification.WARNING)
        }
        relativeLayout2.setOnClickListener {
            onWarningMessageListener?.invoke(relativeLayout, position,Identification.MONITOR)
        }


    }
}