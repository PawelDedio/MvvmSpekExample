package com.dedio.spekexample.views.decoration

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.DividerItemDecoration
import com.dedio.spekexample.R
import com.dedio.spekexample.util.extensions.getDrawableCompat

class VerticalDividerItemDecoration(context: Context) : DividerItemDecoration(context, VERTICAL) {

    private var divider: Drawable = context.getDrawableCompat(R.drawable.ic_list_item_divider)!!

    init {
        setDrawable(divider)
    }
}