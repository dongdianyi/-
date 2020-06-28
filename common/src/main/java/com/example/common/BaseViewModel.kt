package com.example.common

import android.app.Activity
import android.app.Application
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
     lateinit var dialogCircle: DialogCircle

    fun showDialogBase(activity: Activity, flag: Int,layout:Int): Unit {
        val rootView =
            View.inflate(activity, layout, null)
        dialogCircle =
            getPop(activity, rootView, 1, 3, Gravity.CENTER, 0, false)

        var sureButton=rootView.findViewById<Button>(R.id.sure_button)
        var closeIv=rootView.findViewById<ImageView>(R.id.close_iv)
        sureButton.setOnClickListener {
            dialogCircle.dismiss()
        }
        closeIv.setOnClickListener {
            dialogCircle.dismiss()
        }
    }

}