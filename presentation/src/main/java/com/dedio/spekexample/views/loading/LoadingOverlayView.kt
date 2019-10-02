package com.dedio.spekexample.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.widget.FrameLayout
import com.dedio.spekexample.R
import com.dedio.spekexample.util.extensions.getDimenInCalculatedPixels
import com.dedio.spekexample.util.extensions.getStyledAttributes
import com.dedio.spekexample.util.extensions.loadAnimation
import com.dedio.spekexample.views.loading.LoadingImageView

class LoadingOverlayView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                                   defStyleAttr: Int = 0) :
        FrameLayout(context, attrs, defStyleAttr) {

    private val fadeOutAnimation = context.loadAnimation(R.anim.fade_out)

    private var loadingImageWidth = context.getDimenInCalculatedPixels(R.dimen.loading_image_size)
    private var loadingImageHeight = context.getDimenInCalculatedPixels(R.dimen.loading_image_size)

    init {
        setDefaultAttributes(attrs)
        addLoadingImage()
    }

    private fun setDefaultAttributes(attrs: AttributeSet?) {
        isClickable = true
        isFocusable = true
        setBackgroundResource(R.color.loadingOverlayBackground)

        context.getStyledAttributes(attrs, R.styleable.LoadingOverlayView) {
            loadingImageWidth = getDimensionPixelSize(R.styleable.LoadingOverlayView_loadingImageWidth, loadingImageWidth)
            loadingImageWidth = getDimensionPixelSize(R.styleable.LoadingOverlayView_loadingImageHeight, loadingImageHeight)
        }
    }

    private fun addLoadingImage() {
        val width = loadingImageWidth
        val height = loadingImageHeight
        val params = LayoutParams(width, height).apply {
            gravity = Gravity.CENTER
        }

        val view = LoadingImageView(context).apply {
            layoutParams = params
        }

        addView(view)
    }

    fun setVisibilityWithAnimation(newVisibility: Int) {
        if(visibility == View.VISIBLE && newVisibility == View.GONE) {
            setVisibilityOnAnimationEnd(fadeOutAnimation, newVisibility)
            startAnimation(fadeOutAnimation)
        } else {
            super.setVisibility(newVisibility)
        }
    }

    private fun setVisibilityOnAnimationEnd(animation: Animation, newVisibility: Int) {
        animation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationRepeat(animation: Animation) {
            }

            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                visibility = newVisibility
                animation.setAnimationListener(null)
            }
        })
    }
}