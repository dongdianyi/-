package com.example.common.myview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.telephony.mbms.FileInfo
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import com.example.common.R
import com.example.common.dip2px
import java.io.IOException
import java.net.URL


class TextDrawable : AppCompatTextView {
    private var drawableLeft: Drawable? = null
    private var drawableRight: Drawable? = null
    private var drawableTop: Drawable? = null
    private var leftWidth = 0
    private var rightWidth = 0
    private var topWidth = 0
    private var leftHeight = 0
    private var rightHeight = 0
    private var topHeight = 0
    private var mContext: Context

    constructor(context: Context) : super(context) {
        mContext = context
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mContext = context
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mContext = context
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.TextDrawable)
        drawableLeft = typedArray.getDrawable(R.styleable.TextDrawable_leftDrawable)
        drawableRight = typedArray.getDrawable(R.styleable.TextDrawable_rightDrawable)
        drawableTop = typedArray.getDrawable(R.styleable.TextDrawable_topDrawable)
        if (drawableLeft != null) {
            leftWidth = typedArray.getDimensionPixelOffset(
                R.styleable.TextDrawable_leftDrawableWidth,
                dip2px(context, 20f)
            )
            leftHeight = typedArray.getDimensionPixelOffset(
                R.styleable.TextDrawable_leftDrawableHeight,
                dip2px(context, 20f)
            )

        }
        if (drawableRight != null) {
            rightWidth = typedArray.getDimensionPixelOffset(
                R.styleable.TextDrawable_rightDrawableWidth,
                dip2px(context, 20f)
            )
            rightHeight = typedArray.getDimensionPixelOffset(
                R.styleable.TextDrawable_rightDrawableHeight,
                dip2px(context, 20f)
            )
        }
        if (drawableTop != null) {
            topWidth = typedArray.getDimensionPixelOffset(
                R.styleable.TextDrawable_topDrawableWidth,
                dip2px(context, 20f)
            )
            topHeight = typedArray.getDimensionPixelOffset(
                R.styleable.TextDrawable_topDrawableHeight,
                dip2px(context, 20f)
            )
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (drawableLeft != null) {
            drawableLeft!!.setBounds(0, 0, leftWidth, leftHeight)
        }
        if (drawableRight != null) {
            drawableRight!!.setBounds(0, 0, rightWidth, rightHeight)
        }
        if (drawableTop != null) {
            drawableTop!!.setBounds(0, 0, topWidth, topHeight)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        this.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, null)
    }

    /**
     * 设置左侧图片并重绘
     */
    fun setDrawableLeft(drawableLeft: Drawable?) {
        this.drawableLeft = drawableLeft
        invalidate()
    }

    /**
     * 设置左侧图片并重绘
     */
    fun setDrawableLeft(drawableLeftRes: Int) {
        drawableLeft = mContext.getResources().getDrawable(drawableLeftRes)
        invalidate()
    }

    /**
     * 设置左侧图片并重绘
     */
    fun setDrawableLeft(drawableLeftUrl: String) {
        var downloadImageTask = DownloadImageTask(drawableLeftUrl,object : DownloadListener {
            override fun onFinish(result: Drawable?) {
                drawableLeft = result
                invalidate()
            }
        })
        downloadImageTask.execute()
    }

    /**
     * 设置右侧图片并重绘
     */
    fun setDrawableRight(drawableRight: Drawable?) {
        this.drawableRight = drawableLeft
        invalidate()
    }

    /**
     * 设置右侧图片并重绘
     */
    fun setDrawableRight(drawableRightRes: Int) {
        drawableRight = mContext.getResources().getDrawable(drawableRightRes)
        invalidate()
    }

    /**
     * 设置上部图片并重绘
     */
    fun setDrawable(drawableTop: Drawable?) {
        this.drawableTop = drawableTop
        invalidate()
    }

    /**
     * 设置右侧图片并重绘
     */
    fun setDrawableTop(drawableTopRes: Int) {
        drawableTop = mContext.resources.getDrawable(drawableTopRes)
        invalidate()
    }

    open class DownloadImageTask(url:String,downloadListener: DownloadListener) :
        AsyncTask<String, Void, Drawable>() {
        private var downloadListener: DownloadListener = downloadListener
        private var url = url
        override fun doInBackground(vararg params: String?): Drawable? {
           return loadImageFromNetwork(url)
        }

        override fun onPostExecute(result: Drawable?) {
            downloadListener.onFinish(result)
        }
    }


}

interface DownloadListener {
    fun onFinish(result: Drawable?)

}

fun loadImageFromNetwork(imageUrl: String): Drawable? {
    var drawable: Drawable? = null
    try {
        // 可以在这里通过文件名来判断，是否本地有此图片
        drawable = Drawable.createFromStream(
            URL(imageUrl).openStream(), "image.jpg"
        )
    } catch (e: IOException) {
        Log.d("test", e.message)
    }
    if (drawable == null) {
        Log.d("test", "null drawable")
    } else {
        Log.d("test", "not null drawable")
    }
    return drawable
}
