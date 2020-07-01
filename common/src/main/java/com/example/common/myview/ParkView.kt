package com.example.common.myview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.common.R

class ParkView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var lat = mutableListOf<String>()
    var lng = mutableListOf<String>()
    var latS = ""
    var lngS = ""
    var massifName = ""
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

    fun setPoint(
        lat: MutableList<String>,
        lng: MutableList<String>,
        latS: String,
        lngS: String,
        massifName: String
    ): Unit {
        this.lat = lat
        this.lng = lng
        this.latS = latS
        this.lngS = lngS
        this.massifName = massifName
        invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //初始化画笔


        //初始化画笔
        var paint = Paint()

        paint.color = ContextCompat.getColor(context, R.color.blue)
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 1f
        paint.isAntiAlias = true
        val path = Path()
        if (lat.size <= 0)
            return
        path.moveTo(lat[0].toFloat(), lng[0].toFloat())
//
        for (index in 1 until lat.size) {
            path.lineTo(lat[index].toFloat(), lng[index].toFloat())
        }


        path.close()
        //画线
        canvas.drawPath(path, paint)


        paint.color = ContextCompat.getColor(context, R.color.orange)

        if (!TextUtils.isEmpty(latS)) {
            canvas.drawCircle(latS.toFloat(), lngS.toFloat(), 10f, paint)
            paint.color = ContextCompat.getColor(context, R.color.title_color)
            paint.textSize = 20f
            canvas.drawText(massifName, latS.toFloat()+5, lngS.toFloat(), paint)
        }

    }

}