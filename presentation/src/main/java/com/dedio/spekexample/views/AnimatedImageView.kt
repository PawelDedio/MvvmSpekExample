package com.dedio.spekexample.views

import android.content.Context
import android.graphics.drawable.Animatable
import android.util.AttributeSet
import com.dedio.spekexample.views.base.BaseImageView

open class AnimatedImageView : BaseImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

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