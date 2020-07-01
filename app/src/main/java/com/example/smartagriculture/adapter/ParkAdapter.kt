package com.example.smartagriculture.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.View.*
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.common.*
import com.example.common.bean.BeanDataList
import com.example.smartagriculture.R
import com.example.common.myview.ParkView
import com.example.common.myview.TextDrawable
import com.example.common.data.Identification
import java.math.BigDecimal

/**
 * Created by ddy
 */
class ParkAdapter(context: Context?, layoutId: Int) :
    ListBaseAdapter<Any?>(context) {
    private var layoutId: Int = 0

    init {
        this.layoutId = layoutId
    }

    private var onWarningMessageListener: ((view: View, parkId: String, flag: Int) -> Unit)? = null

    fun setOnWMListener(onWarningMessageListener: ((view: View, parkId: String, flag: Int) -> Unit)) {
        this.onWarningMessageListener = onWarningMessageListener

    }

    override fun getLayoutId(): Int {
        return layoutId
    }

    @SuppressLint("SetTextI18n")
    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {
        val data: BeanDataList = mDataList[position] as BeanDataList
        val titleText = holder.getView<TextDrawable>(R.id.textDrawable)
        val textDrawable2 = holder.getView<TextDrawable>(R.id.textDrawable2)
        val textDrawable3 = holder.getView<TextDrawable>(R.id.textDrawable3)
        val textView2 = holder.getView<TextView>(R.id.textView2)
        val textView3 = holder.getView<TextView>(R.id.textView3)
        val textView4 = holder.getView<TextView>(R.id.textView4)
        val textView5 = holder.getView<TextView>(R.id.textView5)
        val textView6 = holder.getView<TextView>(R.id.textView6)
        val textView7 = holder.getView<TextView>(R.id.textView7)
        val textView12 = holder.getView<TextView>(R.id.textView12)
        val textView13 = holder.getView<TextDrawable>(R.id.textView13)
        val textView14 = holder.getView<TextDrawable>(R.id.textView14)
        val textView15 = holder.getView<TextView>(R.id.textView15)
        val textView16 = holder.getView<TextView>(R.id.textView16)
        val textView17 = holder.getView<TextDrawable>(R.id.textView17)
        val textView18 = holder.getView<TextDrawable>(R.id.textView18)
        val textView19 = holder.getView<TextDrawable>(R.id.textView19)
        val textView21 = holder.getView<TextView>(R.id.textView21)
        val textView22 = holder.getView<TextView>(R.id.textView22)
        val park_view = holder.getView<ParkView>(R.id.park_view)
        val relativeLayout = holder.getView<RelativeLayout>(R.id.relativeLayout)
        val relativeLayout2 = holder.getView<RelativeLayout>(R.id.relativeLayout2)

        var strMsg = mutableListOf(
            "${data.useFertilizerNum}\n施肥", "${data.usePesticidesNum}\n施药"
            , "${data.farmingNum}\n农事", "${data.collectionNum}\n采集", "${data.patrolNum}\n巡园"
        )
//        val strMsg = "<font color=\"#1BA17E\"><big>3</big></font><br><font color=\"#696969\">施肥</font>"
        var spList = mutableListOf<SpannableString>()
        strMsg.forEach {
            var sp = SpannableString(it)
            sp.setSpan(
                AbsoluteSizeSpan(28, false),
                0,
                it.length - 2,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            sp.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                it.length - 2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            sp.setSpan(
                ForegroundColorSpan(Color.rgb(27, 161, 126)),
                0,
                it.length - 2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            sp.setSpan(
                AbsoluteSizeSpan(22, false),
                it.length - 2,
                it.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            spList.add(sp)
        }
//        textView3.text = Html.fromHtml(strMsg)
        if (data.traceState == 1) {
            textDrawable2.visibility = VISIBLE
        } else {
            textDrawable2.visibility = GONE
        }
        textView3.text = spList[0]
        textView4.text = spList[1]
        textView5.text = spList[2]
        textView6.text = spList[3]
        textView7.text = spList[4]
        textView2.text = data.planTaskNum.toString() + "\n待做任务"
        titleText.text = data.title
        textView12.text = data.warningCount.toString()
        textView16.text = data.usingHardwareCount.toString()
        textDrawable3.text = data.position
        textView21.text = mContext.resources.getString(R.string.area, data.area)
        textView22.text = mContext.resources.getString(R.string.crop, data.crop)
        when (data.warningVOList.size) {
            0 -> {
                textView13.visibility = INVISIBLE
                textView14.visibility = INVISIBLE
            }
            1 -> {
                textView13.text = data.warningVOList[0].typeName + ":" + data.warningVOList[0].count
                textView13.setDrawableLeft(
                    BitmapDrawable(
                        mContext.resources,
                        stringToBitmap(data.warningVOList[0].typePhoto)
                    )
                )
                textView13.visibility = VISIBLE
                textView14.visibility = INVISIBLE
            }
            else -> {
                textView13.visibility = VISIBLE
                textView14.visibility = VISIBLE
                textView13.text =
                    data.warningVOList[0].typeName + ":" + data.warningVOList[0].count
                textView14.text =
                    data.warningVOList[1].typeName + ":" + data.warningVOList[1].count
                textView13.setDrawableLeft(
                    BitmapDrawable(
                        mContext.resources,
                        stringToBitmap(data.warningVOList[0].typePhoto)
                    )
                )
                textView14.setDrawableLeft(
                    BitmapDrawable(
                        mContext.resources,
                        stringToBitmap(data.warningVOList[1].typePhoto)
                    )
                )
            }
        }
        when (data.usingHardwareList.size) {
            0 -> {
                textView17.visibility = INVISIBLE
                textView18.visibility = INVISIBLE
                textView19.visibility = INVISIBLE
            }
            1 -> {
                textView17.text =
                    data.usingHardwareList[0].typeName + ":" + data.usingHardwareList[0].count
                textView17.setDrawableLeft(
                    BitmapDrawable(
                        mContext.resources,
                        stringToBitmap(data.usingHardwareList[0].typePhoto)
                    )
                )
                textView17.visibility = VISIBLE
                textView18.visibility = INVISIBLE
                textView19.visibility = INVISIBLE
            }
            2 -> {
                textView17.text =
                    data.usingHardwareList[0].typeName + ":" + data.usingHardwareList[0].count
                textView18.text =
                    data.usingHardwareList[1].typeName + ":" + data.usingHardwareList[1].count
                textView17.setDrawableLeft(
                    BitmapDrawable(
                        mContext.resources,
                        stringToBitmap(data.usingHardwareList[0].typePhoto)
                    )
                )
                textView18.setDrawableLeft(
                    BitmapDrawable(
                        mContext.resources,
                        stringToBitmap(data.usingHardwareList[1].typePhoto)
                    )
                )
                textView17.visibility = VISIBLE
                textView18.visibility = VISIBLE
                textView19.visibility = INVISIBLE
            }
            else -> {
                textView17.visibility = VISIBLE
                textView18.visibility = VISIBLE
                textView19.visibility = VISIBLE
                textView17.text =
                    data.usingHardwareList[0].typeName + ":" + data.usingHardwareList[0].count
                textView18.text =
                    data.usingHardwareList[1].typeName + ":" + data.usingHardwareList[1].count
                textView19.text =
                    data.usingHardwareList[2].typeName + ":" + data.usingHardwareList[2].count
                textView17.setDrawableLeft(
                    BitmapDrawable(
                        mContext.resources,
                        stringToBitmap(data.usingHardwareList[0].typePhoto)
                    )
                )
                textView18.setDrawableLeft(
                    BitmapDrawable(
                        mContext.resources,
                        stringToBitmap(data.usingHardwareList[1].typePhoto)
                    )
                )
                textView19.setDrawableLeft(
                    BitmapDrawable(
                        mContext.resources,
                        stringToBitmap(data.usingHardwareList[2].typePhoto)
                    )
                )
            }
        }

        //xy轴倍数比较

        var lat = mutableListOf<String>()
        var lng = mutableListOf<String>()
        var latS = ""
        var lngS = ""
        if (data.attr != null) {
            data.attr!!.split(",").indices.forEach {
                if (it % 2 == 0) {
                    lat.add(data.attr!!.split(",")[it])
                } else {
                    lng.add(data.attr!!.split(",")[it])
                }
            }
            var latBigNum = BigDecimal(data.lat)
            var lngBigNum = BigDecimal(data.lng)
            var latSmalNum = BigDecimal(data.lat)
            var lngSmalNum = BigDecimal(data.lng)
            lat.forEach {
                if (it.toBigDecimal().compareTo(latBigNum) == 1) {
                    latBigNum = it.toBigDecimal()
                }
                if (it.toBigDecimal().compareTo(latSmalNum) == -1) {
                    latSmalNum = it.toBigDecimal()
                }
            }
            lng.forEach {
                if (it.toBigDecimal().compareTo(lngBigNum) == 1) {
                    lngBigNum = it.toBigDecimal()
                }
                if (it.toBigDecimal().compareTo(lngSmalNum) == -1) {
                    lngSmalNum = it.toBigDecimal()
                }
            }
            LogUtil("最大", latBigNum.toString() + lngBigNum)
            LogUtil("最小", latSmalNum.toString() + lngSmalNum)


            park_view.post {
                var height = park_view.height
                var width = park_view.width
                var multiple: BigDecimal
                //xy轴倍数比较
                var xMultiple = width.toBigDecimal() / (latBigNum - latSmalNum)
                var yMultiple = height.toBigDecimal() / (lngBigNum - lngSmalNum)
                multiple = if (xMultiple > yMultiple) {
                    yMultiple
                } else {
                    xMultiple
                }
                LogUtil("比例", multiple)
                for (index in lat.indices) {
                    lat[index] = ((lat[index].toBigDecimal() - latSmalNum) * multiple).toString()
                    lng[index] = ((lng[index].toBigDecimal() - lngSmalNum) * multiple).toString()
                }

                if (data.lat != null && data.lng != null) {
                    latS = ((data.lat!!.toBigDecimal() - latSmalNum) * multiple).toString()
                    lngS = ((data.lng!!.toBigDecimal() - lngSmalNum) * multiple).toString()
                    park_view.setPoint(lat, lng, latS, lngS, data.massifName)
                }
            }
        } else {
            park_view.setPoint(lat, lng, latS, lngS, data.massifName)
        }


        relativeLayout.setOnClickListener {
            onWarningMessageListener?.invoke(relativeLayout, data.parkId, Identification.WARNING)
        }
        relativeLayout2.setOnClickListener {
            onWarningMessageListener?.invoke(relativeLayout, data.parkId, Identification.MONITOR)
        }


    }
}