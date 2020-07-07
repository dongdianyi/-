/*
 * Apache DropDownView
 *
 * Copyright 2017 The Apache Software Foundation
 * This product includes software developed at
 * The Apache Software Foundation (http://www.apache.org/).
 */
package com.example.common.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.common.R
import com.example.common.base.BaseApplication
import com.example.common.myview.TextDrawable

/**
 * @author Anthony ddy
 */
open class DropDownAdapter(
    private val actionViewDelegate: ViewActions,
    parks: MutableList<Any>
) :
    RecyclerView.Adapter<DropDownAdapter.StandViewHolder>() {

    var count = 0

    init {
        count = parks.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.drop_item, parent, false)
        return StandViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StandViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return count
    }

    inner class StandViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val standTitleTV: TextDrawable =
            itemView.findViewById<View>(R.id.textDrawable6) as TextDrawable
        private val textView27: TextView =
            itemView.findViewById<TextView>(R.id.textView27)

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            standTitleTV.text = actionViewDelegate.getStandTitle(position)
            textView27.text = "地块："+actionViewDelegate.getStandPark(position)
            itemView.isSelected = actionViewDelegate.selectedStand == position
            standTitleTV.isEnabled = actionViewDelegate.selectedStand==position
        }

        private val standViewItemClickListener =
            View.OnClickListener {
                val lastSelectedPosition = actionViewDelegate.selectedStand
                actionViewDelegate.selectedStand = adapterPosition
                notifyItemChanged(lastSelectedPosition)
                notifyItemChanged(adapterPosition)
                actionViewDelegate.collapseDropDown()
            }

        init {
            itemView.setOnClickListener(standViewItemClickListener)
        }
    }

    interface ViewActions {
        fun collapseDropDown()
        fun getStandTitle(standId: Int): String?
        fun getStandPark(standId: Int): String?
        fun getStandStatus(standId: Int): String?
        var selectedStand: Int
    }

}