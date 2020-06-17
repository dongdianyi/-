package com.example.common

import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment

class ToastUtil<T>(message: T) {
    init {
        when (message) {
            is String -> makeToast(message)
            else -> makeToast(message.toString())
        }
    }

    private fun makeToast(message: String) {
        val toast = Toast(BaseApplication.getContext())
        toast.setText(message)
        toast.duration = Toast.LENGTH_SHORT
        toast.show()

    }
}


class LogUtil<T>(TAG: String, content: T) {

    init {
        when (content) {
            is String -> {
                Log.e(TAG, content)
            }
            else -> {
                Log.e(TAG, content.toString())
            }
        }
    }

}


