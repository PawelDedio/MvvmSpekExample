package com.dedio.spekexample.util.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.annotation.AttrRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.use

fun Context.getDrawableCompat(resourceId: Int) = ContextCompat.getDrawable(this, resourceId)
fun Context.getColorCompat(resourceId: Int) = ContextCompat.getColor(this, resourceId)

fun Context.getStyledAttributes(attributeSet: AttributeSet?, styleArray: IntArray): TypedArray =
    this.obtainStyledAttributes(attributeSet, styleArray, 0, 0)

@SuppressLint("Recycle")
fun Context.getStyledAttributes(
    attributeSet: AttributeSet?,
    styleArray: IntArray,
    block: TypedArray.() -> Unit
) =
    this.obtainStyledAttributes(attributeSet, styleArray, 0, 0).use(block)

fun Context.getColorFromAttr(
    @AttrRes attrColor: Int, typedValue: TypedValue = TypedValue(),
    resolveRefs: Boolean = true
): Int {
    theme.resolveAttribute(attrColor, typedValue, resolveRefs)
    return typedValue.data
}

fun Context.getDimenInCalculatedPixels(@DimenRes dimensRes: Int): Int {
    return resources.getDimensionPixelSize(dimensRes)
}

fun Context.loadAnimation(@AnimRes animRes: Int): Animation = AnimationUtils.loadAnimation(this, animRes)
