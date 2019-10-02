package com.dedio.spekexample.views.loading

import android.content.Context
import android.util.AttributeSet
import com.dedio.spekexample.R
import com.dedio.spekexample.util.extensions.getDrawableCompat
import com.dedio.spekexample.views.AnimatedImageView

class LoadingImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AnimatedImageView(context, attrs, defStyleAttr) {

    init {
        setImageDrawable(context.getDrawableCompat(R.drawable.ic_loader_animated))
        startAnimation()
    }
}