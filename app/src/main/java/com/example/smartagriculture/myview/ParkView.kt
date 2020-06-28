package com.example.smartagriculture.myview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.smartagriculture.R

class ParkView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var points = mutableListOf<String>()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measured(widthMeasureSpec), measured(heightMeasureSpec))
    }

    private fun measured(measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            return specSize
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                return 200000.coerceAtMost(specSize)
            }
        }
        return 0
    }

    fun setPoint(points: String): Unit {
        this.points = points.split(",").toMutableList()
        invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //初始化画笔


        //初始化画笔
        var paint = Paint()
        paint.color = ContextCompat.getColor(context, R.color.blue)
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 1f
        paint.isAntiAlias = true
        val path = Path()
        path.moveTo(points[0].toFloat(), points[1].toFloat())

        for (index in 2 until points.size step 2){
            path.lineTo(points[index].toFloat(),points[index+1].toFloat())
        }
        path.close()
        //画线
        canvas!!.drawPath(path, paint)

    }

}