package com.example.common.myview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.RadioButton
import com.example.common.R

@SuppressLint("AppCompatCustomView")
class MyRadioButton(context: Context?, attrs: AttributeSet?) : RadioButton(context, attrs) {

    init {
        val typedArray =
            context?.obtainStyledAttributes(attrs, R.styleable.MyRadioButton);//获取我们定义的属性
        val drawable = typedArray?.getDrawable(R.styleable.MyRadioButton_android_drawableLeft);
        drawable?.setBounds(0, 0, 30, 30);
        setCompoundDrawables(drawable, null, null, null);

    }

}