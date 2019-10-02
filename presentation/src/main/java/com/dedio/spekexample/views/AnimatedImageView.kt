package com.dedio.spekexample.views

import android.content.Context
import android.graphics.drawable.Animatable
import android.util.AttributeSet
import com.dedio.spekexample.views.base.BaseImageView

open class AnimatedImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseImageView(context, attrs, defStyleAttr) {

    init {
        startAnimation()
    }

    fun startAnimation() {
        (drawable as? Animatable)?.start()
    }

    fun stopAnimation() {
        (drawable as? Animatable)?.stop()
    }
}