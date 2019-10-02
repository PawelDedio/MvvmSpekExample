package com.dedio.spekexample.views.base

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.dedio.spekexample.R
import com.dedio.spekexample.util.extensions.getStyledAttributes
import com.dedio.spekexample.views.decoration.VerticalDividerItemDecoration


open class BaseRecyclerView : RecyclerView {

    constructor(context: Context) : super(context) {
        setup(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setup(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs,
            defStyleAttr) {
        setup(context, attrs)
    }

    private fun setup(context: Context, attrs: AttributeSet?) {
        isNestedScrollingEnabled = false

        context.getStyledAttributes(attrs, R.styleable.BaseRecyclerView) {
            val showDivider = getBoolean(R.styleable.BaseRecyclerView_showDivider, false)

            if (showDivider) {
                addItemDecoration(VerticalDividerItemDecoration(context))
            }
        }
    }
}