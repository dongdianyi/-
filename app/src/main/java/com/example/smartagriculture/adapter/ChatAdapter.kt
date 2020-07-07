package com.example.smartagriculture.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.common.bean.BeanDataList
import com.example.smartagriculture.R
import com.example.smartagriculture.bean.CreatChat
import com.example.common.data.Identification

/**
 * Created by ddy
 */
class ChatAdapter(context: Context?, layoutId: Int, id: Int) :
    ListBaseAdapter<Any?>(context) {
    private var layoutId = 0
    private var id = 0
    lateinit var beanDataList: BeanDataList

    init {
        this.layoutId = layoutId
        this.id = id
    }

    override fun getLayoutId(): Int {
        return layoutId
    }

    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {

        when (id) {
            Identification.CHAT -> {
                var dataList = mDataList[position] as BeanDataList
                val textView39 = holder.getView<TextView>(R.id.textView39)
                val textView40 = holder.getView<TextView>(R.id.textView40)
                val textView41 = holder.getView<TextView>(R.id.textView41)
                textView39.text = dataList.sendname
                textView40.text = dataList.chatContent
                textView41.text = dataList.chatTime
            }
            Identification.CHATDETAILS -> {
                val textView43 = holder.getView<TextView>(R.id.textView43)
                val textView44 = holder.getView<TextView>(R.id.textView44)
                if (position % 2 == 0) {
                    textView43.text = mDataList[position].toString()
                    textView44.text = "hahahh"
                } else {
                    textView43.text = "hehheh"
                    textView44.text = mDataList[position].toString()
                }
            }
            Identification.CHATMAILLIST -> {
                var beanDataList = mDataList[position] as BeanDataList
                val textView39 = holder.getView<TextView>(R.id.textView39)
                val textView40 = holder.getView<TextView>(R.id.textView40)
                val textView41 = holder.getView<TextView>(R.id.textView41)
                textView40.visibility = View.GONE
                textView41.visibility = View.GONE
                textView39.text = beanDataList.userName
            }
            Identification.ADDCHAT -> {
                val textView39 = holder.getView<TextView>(R.id.textView39)
                val textView40 = holder.getView<TextView>(R.id.textView40)
                val textView41 = holder.getView<TextView>(R.id.textView41)
                val imageView14 = holder.getView<ImageView>(R.id.imageView14)
                textView40.visibility = View.GONE
                textView41.visibility = View.GONE
                imageView14.visibility = View.VISIBLE
                beanDataList = mDataList[position] as BeanDataList
                textView39.text = beanDataList.userName
                imageView14.isEnabled = beanDataList.isChoose
            }
            else -> {

            }
        }


    }

    fun multipleChoose(position: Int): Unit {
        (mDataList[position] as BeanDataList).isChoose =
            (mDataList[position] as BeanDataList).isChoose != true
        notifyDataSetChanged()
    }
}