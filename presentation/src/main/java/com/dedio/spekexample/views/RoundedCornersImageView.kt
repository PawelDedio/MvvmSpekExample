package com.dedio.spekexample.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import com.dedio.spekexample.R
import com.dedio.spekexample.util.extensions.getStyledAttributes
import com.dedio.spekexample.views.base.BaseImageView


class RoundedCornersImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseImageView(context, attrs, defStyleAttr) {

    private var radius = context.resources.getDimensionPixelOffset(R.dimen.rounded_corners_image_view_default_radius)
    private var path: Path = Path()
    private lateinit var rect: RectF

    init {
        context.getStyledAttributes(attrs, R.styleable.RoundedCornersImageView) {
            radius = getDimensionPixelSize(R.styleable.RoundedCornersImageView_cornerRadius, radius)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        path.addRoundRect(rect, radius.toFloat(), radius.toFloat(), Path.Direction.CW)
        canvas?.clipPath(path)

        super.onDraw(canvas)
    }
}