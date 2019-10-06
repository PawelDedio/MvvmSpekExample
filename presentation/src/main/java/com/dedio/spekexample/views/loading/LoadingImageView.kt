package com.dedio.spekexample.views.loading

import android.content.Context
import android.util.AttributeSet
import com.dedio.spekexample.R
import com.dedio.spekexample.util.extensions.getDrawableCompat
import com.dedio.spekexample.views.AnimatedImageView

class LoadingImageView : AnimatedImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs,
            defStyleAttr)

    init {
        setImageDrawable(context.getDrawableCompat(R.drawable.ic_loader_animated))
        startAnimation()
    }
}