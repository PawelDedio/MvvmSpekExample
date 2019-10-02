package com.dedio.spekexample.util.extensions

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.Animation
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.RotateAnimation
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.Transition
import androidx.transition.Visibility

fun ConstraintSet.connectStartToStart(startId: Int, endId: Int, margin: Int = 0) {
    connect(startId, ConstraintSet.START, endId, ConstraintSet.START, margin)
}

fun ConstraintSet.connectStartToEnd(startId: Int, endId: Int, margin: Int = 0) {
    connect(startId, ConstraintSet.START, endId, ConstraintSet.END, margin)
}

fun ConstraintSet.connectTopToTop(startId: Int, endId: Int, margin: Int = 0) {
    connect(startId, ConstraintSet.TOP, endId, ConstraintSet.TOP, margin)
}

fun ConstraintSet.connectBottomToBottom(startId: Int, endId: Int, margin: Int = 0) {
    connect(startId, ConstraintSet.BOTTOM, endId, ConstraintSet.BOTTOM, margin)
}

fun ConstraintSet.connectTopToBottom(startId: Int, endId: Int, margin: Int = 0) {
    connect(startId, ConstraintSet.TOP, endId, ConstraintSet.BOTTOM, margin)
}

fun ConstraintSet.connectEndToStart(startId: Int, endId: Int, margin: Int = 0) {
    connect(startId, ConstraintSet.END, endId, ConstraintSet.START, margin)
}

fun ConstraintSet.connectEndToEnd(startId: Int, endId: Int, margin: Int = 0) {
    connect(startId, ConstraintSet.END, endId, ConstraintSet.END, margin)
}

inline fun <reified T> ViewGroup.getChildViews(): List<T> {
    val views = mutableListOf<T>()

    for (i in 0 until childCount) {
        val view = getChildAt(i)

        if (view is T) {
            views.add(getChildAt(i) as T)
        }
    }

    return views
}

fun TextView.animateTextColor(
    startColor: Int,
    endColor: Int,
    duration: Long = 300L
): ObjectAnimator {
    val colorAnim = ObjectAnimator.ofInt(this, "textColor", startColor, endColor)
    colorAnim.duration = duration
    colorAnim.setEvaluator(ArgbEvaluator())
    colorAnim.start()
    return colorAnim
}

fun View.startPendulumAnimation(animationDuration: Long = 600L, degrees: Float = 22.5f) {
    lateinit var rightToLeftAnimation: () -> RotateAnimation
    lateinit var leftToRightAnimation: () -> RotateAnimation

    rightToLeftAnimation = {
        rotateAnimation(animationDuration, fromDegrees = -degrees, toDegrees = degrees).apply {
            setEndListener { startAnimation(leftToRightAnimation()) }
        }
    }

    leftToRightAnimation = {
        rotateAnimation(animationDuration, fromDegrees = degrees, toDegrees = -degrees).apply {
            setEndListener { startAnimation(rightToLeftAnimation()) }
        }
    }

    rotateAnimation(animationDuration, fromDegrees = 0f, toDegrees = -degrees).apply {
        setEndListener { startAnimation(rightToLeftAnimation()) }
        startAnimation(this)
    }
}

private inline fun rotateAnimation(animationDuration: Long, fromDegrees: Float, toDegrees: Float) =
    RotateAnimation(
        fromDegrees, toDegrees,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF, 0.5f
    ).apply {
        duration = animationDuration
        interpolator = AnticipateOvershootInterpolator(1.0f)
    }

fun Animation.setEndListener(onEnd: () -> Unit): Animation {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {
        }

        override fun onAnimationEnd(p0: Animation?) {
            onEnd()
        }

        override fun onAnimationStart(p0: Animation?) {
        }

    })

    return this
}

inline fun View.afterMeasured(crossinline f: View.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}

inline fun View.onPreDraw(crossinline f: View.() -> Unit) {
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            f()
            return false
        }
    })
}

fun View.getString(@StringRes resourceId: Int): String = context.getString(resourceId)
fun View.getInteger(@IntegerRes resourceId: Int): Int = context.resources.getInteger(resourceId)
fun View.getLong(@IntegerRes resourceId: Int): Long = context.resources.getInteger(resourceId).toLong()

fun TextView.setTextAppearanceCompat(@StyleRes textStyleId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.setTextAppearance(textStyleId)
    } else {
        this.setTextAppearance(context, textStyleId)
    }
}

inline fun <reified T : View> Context.inflateView(resourceId: Int, root: ViewGroup? = null, attachToRoot: Boolean = true) = LayoutInflater.from(this).inflate(resourceId, root, attachToRoot) as T

fun ViewGroup.setChildrenVisibility(visibility: Int) {
    this.getChildViews<View>().forEach { it.visibility = visibility }
}

fun Visibility.addEndTransitionListener(onEnd: () -> Unit) {
    addListener(object : Transition.TransitionListener {
        override fun onTransitionEnd(transition: Transition) {
            onEnd()
        }

        override fun onTransitionResume(transition: Transition) {
        }

        override fun onTransitionPause(transition: Transition) {
        }

        override fun onTransitionCancel(transition: Transition) {
        }

        override fun onTransitionStart(transition: Transition) {
        }
    })
}

fun TextView.setCompoundDrawablesWithIntrinsicBounds(left: Drawable? = null, top: Drawable? = null, right: Drawable? = null, bottom: Drawable? = null) {
    this.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
}

fun LinearLayout.createSpace(heightInPixels: Int) = Space(context).apply {
    layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heightInPixels)
}
